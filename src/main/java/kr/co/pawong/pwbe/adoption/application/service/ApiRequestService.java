package kr.co.pawong.pwbe.adoption.application.service;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.ProcessState;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
import kr.co.pawong.pwbe.adoption.infrastructure.external.AdoptionApi;
import kr.co.pawong.pwbe.adoption.infrastructure.external.AdoptionApi.Body;
import kr.co.pawong.pwbe.adoption.infrastructure.external.AdoptionApi.Items;
import kr.co.pawong.pwbe.adoption.infrastructure.external.AdoptionApi.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiRequestService {
    private final RestTemplate restTemplate;

    public List<AdoptionCreate> saveAdoptions() {
        List<AdoptionCreate> allAdoptionCreates = new ArrayList<>();
        int pageNo = 1;
        int numOfRows = 1000;
        boolean hasMoreData = true;

        while (hasMoreData) {
            log.info("데이터 가져오기: 페이지 {}, 페이지당 {} 건", pageNo, numOfRows);

            // API 요청 주소
            URI uri = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/1543061/abandonmentPublicService_v2/abandonmentPublic_v2")
                    .queryParam("serviceKey", "0O1KlLSEEGjpWzJOBa8Q9Mxfc3g%2FA%2BPSVTzNt3XSLdZdVuyaUMWYmsgAPe%2FwolWOrEVyUAYuk9rzp4VNwHNBOg%3D%3D")
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("_type", "json")
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .build(true)
                    .toUri();

            log.info("요청 주소: {}", uri.toString());

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<AdoptionApi> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, AdoptionApi.class);
                AdoptionApi adoptionApi = responseEntity.getBody();
                log.info("응답: {}", adoptionApi);

                if (isValidAdoptionData(adoptionApi)) {
                    List<AdoptionApi.Item> items = adoptionApi.getResponse().getBody().getItems().getItem();
                    List<AdoptionCreate> adoptionCreates = new ArrayList<>();

                    // AdoptionApi -> AdoptionCreate
                    for (AdoptionApi.Item item : items) {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate happenDt = parseLocalDate(item.getHappenDt(), dateFormatter);
                        LocalDate noticeSdt = parseLocalDate(item.getNoticeSdt(), dateFormatter);
                        LocalDate noticeEdt = parseLocalDate(item.getNoticeEdt(), dateFormatter);
                        LocalDateTime updTm = parseLocalDateTime(item.getUpdTm(), dateTimeFormatter);

                        AdoptionCreate adoptionCreate = AdoptionCreate.builder()
                                .desertionNo(item.getDesertionNo())
                                .happenDt(happenDt)
                                .happenPlace(item.getHappenPlace())
                                .upKindCd(UpKindCd.fromValue(item.getUpKindCd()))
                                .upKindNm(UpKindNm.fromValue(item.getUpKindNm()))
                                .kindCd(item.getKindCd())
                                .kindNm(item.getKindNm())
                                .colorCd(item.getColorCd())
                                .age(parseInt(item.getAge()))
                                .weight(item.getWeight())
                                .noticeNo(item.getNoticeNo())
                                .noticeSdt(noticeSdt)
                                .noticeEdt(noticeEdt)
                                .popfile1(item.getPopfile1())
                                .popfile2(item.getPopfile2())
                                .processState(ProcessState.fromValue(item.getProcessState()))
                                .sexCd(convertToEnum(item.getSexCd(), SexCd.class))
                                .neuterYn(convertToEnum(item.getNeuterYn(), NeuterYn.class))
                                .specialMark(item.getSpecialMark())
                                .updTm(updTm)
                                .build();

                        adoptionCreate.updateActiveState();
                        adoptionCreates.add(adoptionCreate);
                    }

                    allAdoptionCreates.addAll(adoptionCreates);

                    if (items.size() < numOfRows) {
                        hasMoreData = false;
                        log.info("마지막 페이지 도달: {} 건의 데이터 검색됨", items.size());
                    } else {
                        pageNo++;

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } else {
                    hasMoreData = false;
                    log.info("더 이상 데이터가 없습니다.");
                }
            } catch (Exception e) {
                log.error("API 호출 중 오류 발생: {}", e.getMessage(), e);
                hasMoreData = false;
            }
        }
        return allAdoptionCreates;
    }

    // 유효성 검사
    private boolean isValidAdoptionData(AdoptionApi adoptionApi) {
        return Optional.ofNullable(adoptionApi)
                .map(AdoptionApi::getResponse)
                .map(Response::getBody)
                .map(Body::getItems)
                .map(Items::getItem)
                .filter(items -> !items.isEmpty())
                .isPresent();
    }

    // String -> LocalDate
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

    // String -> LocalDateTime
    private LocalDateTime parseLocalDateTime(String date, DateTimeFormatter formatter) {
        if (date != null && !date.isEmpty()) {
            try {
                return LocalDateTime.parse(date, formatter)
                        .truncatedTo(ChronoUnit.SECONDS);
            } catch (DateTimeParseException e) {
                log.error("날짜 시간 파싱 오류 ({}): {}", date, e.getMessage());
            }
        }
        return null;
    }

    // String -> int
    private Integer parseInt(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            String age = value.substring(0, 4);
            return Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // String -> Enum
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
}
