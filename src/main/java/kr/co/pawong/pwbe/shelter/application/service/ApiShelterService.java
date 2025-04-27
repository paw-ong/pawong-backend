package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import kr.co.pawong.pwbe.shelter.enums.DivisionNm;
import kr.co.pawong.pwbe.shelter.infrastructure.external.ShelterApi;
import kr.co.pawong.pwbe.shelter.infrastructure.repository.ShelterJpaRepository;
import kr.co.pawong.pwbe.shelter.infrastructure.repository.entity.ShelterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiShelterService {
    private final ShelterJpaRepository shelterJpaRepository;
    private final RestTemplate restTemplate;

    public List<ShelterCreate> saveShelters() {
        List<ShelterCreate> allShelterCreates = new ArrayList<>();
        int pageNo = 1;
        int numOfRows = 1000;
        boolean hasMoreData = true;

        while (hasMoreData) {
            log.info("데이터 가져오기: 페이지 {}, 페이지당 {} 건", pageNo, numOfRows);

            URI uri = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/1543061/animalShelterSrvc_v2/shelterInfo_v2").queryParam("serviceKey", "mIh16wSgE8R9SjJMMwvxYwP%2BInJxEi0M5ZLimKlsKz6nIjuGNb6aEPbGyEU2bT4s1ty83mIWB4fW8h5N3u9LCA%3D%3D").queryParam("numOfRows", numOfRows).queryParam("pageNo", pageNo).queryParam("_type", "json").queryParam("MobileOS", "ETC").queryParam("MobileApp", "AppTest").build(true).toUri();

            log.info("요청 주소: {}", uri.toString());

            org.springframework.http.HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<ShelterApi> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, ShelterApi.class);
                ShelterApi shelterApi = responseEntity.getBody();
                log.info("응답: {}", shelterApi);

                if (isValidShelterData(shelterApi)) {

                    List<ShelterApi.Item> items = shelterApi.getResponse().getBody().getItems().getItem();
                    List<ShelterCreate> shelterCreates = new ArrayList<>();

                    for (ShelterApi.Item item : items) {

                        // 날짜 변환
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate dsignationDate = parseLocalDate(item.getDsignationDate(), formatter);
                        LocalDate dataStdDt = parseLocalDate(item.getDataStdDt(), formatter);
                        String[] addr = parseAddress(item.getCareAddr());

                        ShelterCreate shelterCreate = ShelterCreate.builder()
                                .careNm(item.getCareNm())
                                .careRegNo(item.getCareRegNo())
                                .orgNm(item.getOrgNm())
                                .divisionNm(convertToEnum(item.getDivisionNm(), DivisionNm.class))
                                .saveTrgtAnimal(item.getSaveTrgtAnimal())
                                .careAddr(item.getCareAddr())
                                .jibunAddr(item.getJibunAddr())
                                .city(addr[0])
                                .district(addr[1])
                                .lat(parseDouble(item.getLat(), null))
                                .lng(parseDouble(item.getLng(), null))
                                .dsignationDate(dsignationDate)
                                .weekOprStime(item.getWeekOprStime())
                                .weekOprEtime(item.getWeekOprEtime())
                                .weekCellStime(item.getWeekCellStime())
                                .weekCellEtime(item.getWeekCellEtime())
                                .weekendOprStime(item.getWeekendOprStime())
                                .weekendOprEtime(item.getWeekendOprEtime())
                                .weekendCellStime(item.getWeekendCellStime())
                                .weekendCellEtime(item.getWeekendCellEtime())
                                .closeDay(item.getCloseDay())
                                .vetPersonCnt(parseInt(item.getVetPersonCnt(), null))
                                .specsPersonCnt(parseInt(item.getSpecsPersonCnt(), null))
                                .medicalCnt(parseInt(item.getMedicalCnt(), null))
                                .breedCnt(parseInt(item.getBreedCnt(), null))
                                .quarabtineCnt(parseInt(item.getQuarabtineCnt(), null))
                                .feedCnt(parseInt(item.getFeedCnt(), null))
                                .transCarCnt(parseInt(item.getTransCarCnt(), null))
                                .careTel(item.getCareTel())
                                .dataStdDt(dataStdDt)
                                .build();
                        shelterCreates.add(shelterCreate);
                    }

                    allShelterCreates.addAll(shelterCreates);

                    // 현재 페이지의 데이터 수가 numOfRows보다 적으면 더 이상 데이터가 없는 것으로 판단
                    if (items.size() < numOfRows) {
                        hasMoreData = false;
                        log.info("마지막 페이지 도달: {} 건의 데이터 검색됨", items.size());
                    } else {
                        // 다음 페이지로 이동
                        pageNo++;

                        // API 호출 간격을 두어 서버 부하 방지 (선택사항)
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } else {
                    // 데이터가 없는 경우
                    hasMoreData = false;
                    log.info("더 이상 데이터가 없습니다.");
                }
            } catch (Exception e) {
                log.error("API 호출 중 오류 발생: {}", e.getMessage(), e);
                hasMoreData = false;
            }
        }

        if (!allShelterCreates.isEmpty()) {
            // 중복 제거를 위한 Map 사용 (careNm을 키로 사용)
            Map<String, ShelterEntity> uniqueSheltersMap = new HashMap<>();
            int duplicateCount = 0;

            for (ShelterCreate shelterCreate : allShelterCreates) {
                String careNm = shelterCreate.getCareNm();

                // 이미 해당 이름의 보호소가 맵에 있는지 확인
                if (!uniqueSheltersMap.containsKey(careNm)) {
                    ShelterEntity shelterEntity = shelterCreate.toEntityShelter();
                    uniqueSheltersMap.put(careNm, shelterEntity);
                } else {
                    duplicateCount++;
                }
            }

            // 중복 제거된 보호소 목록 생성
            List<ShelterEntity> uniqueShelterEntitys = new ArrayList<>(uniqueSheltersMap.values());

            // 기존 데이터베이스에 있는 보호소와 중복 제거
            List<ShelterEntity> sheltersToSave = new ArrayList<>();
            for (ShelterEntity shelterEntity : uniqueShelterEntitys) {
                String careNm = shelterEntity.getCareNm();
                // 데이터베이스에서 해당 이름의 보호소가 있는지 확인
                List<ShelterEntity> existingShelters = shelterJpaRepository.findByCareNm(careNm);

                if (existingShelters.isEmpty()) {
                    // 데이터베이스에 없는 경우에만 저장 목록에 추가
                    sheltersToSave.add(shelterEntity);
                } else {
                    log.info("데이터베이스에 이미 존재하는 보호소: {}", careNm);
                }
            }

            // 새로운 보호소만 저장
            if (!sheltersToSave.isEmpty()) {
                shelterJpaRepository.saveAll(sheltersToSave);
                log.info("총 {} 건의 새로운 보호소 데이터를 데이터베이스에 저장했습니다.", sheltersToSave.size());
            } else {
                log.info("저장할 새로운 보호소 데이터가 없습니다.");
            }

            log.info("API에서 가져온 총 보호소 수: {}, 중복 제거 후: {}, 중복 수: {}", allShelterCreates.size(), uniqueShelterEntitys.size(), duplicateCount);
        }

        return allShelterCreates;
    }

    private boolean isValidShelterData(ShelterApi shelterApi) {
        return Optional.ofNullable(shelterApi).map(ShelterApi::getResponse).map(ShelterApi.Response::getBody).map(ShelterApi.Body::getItems).map(ShelterApi.Items::getItem).filter(items -> !items.isEmpty()).isPresent();
    }

    private LocalDate parseLocalDate(String date, DateTimeFormatter formatter) {
        if (date != null && !date.isEmpty()) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                log.error("날짜 파싱 오류 ({}): {}", date, e.getMessage());
            }
        }
        return null;
    }

    private Integer parseInt(String value, Integer defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Double parseDouble(String value, Double defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private <T extends Enum<T>> T convertToEnum(String data, Class<T> enumClass) {
        if (data == null) {
            return null;
        }

        try {
            return Enum.valueOf(enumClass, data);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String[] parseAddress(String careAddr) {
        String[] result = new String[2]; // [0] = city, [1] = district

        if (careAddr != null && !careAddr.isEmpty()) {
            String[] parts = careAddr.split(" ");
            if (parts.length >= 2) {
                result[0] = parts[0]; // city
                result[1] = parts[1]; // town
            }
        }

        return result;
    }
}
