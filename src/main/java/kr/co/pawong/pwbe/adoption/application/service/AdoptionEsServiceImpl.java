package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.RegionInfoDto;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionEsRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionEsService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionEsServiceImpl implements AdoptionEsService {
    private final AdoptionEsRepository adoptionEsRepository;
    private final AdoptionUpdateRepository adoptionUpdateRepository;
    private final AdoptionAiService adoptionAiService;
    private final AdoptionQueryService adoptionQueryService;

    // 전달받은 Adoption 리스트에 대해 임베딩 및 임베딩 완료 여부 설정,
    // shelter에서 가져온 지역 정보와 같이 ES에 저장 후 RDB에 isEmbedded 업데이트
    // TODO: 이후 ES와 RDB에 원자적 저장 보장하기
    @Override
    public void saveAdoptionToEs(List<Adoption> adoptions) {
        for (Adoption adoption : adoptions) {
            if ((adoption.isAiProcessed() && !adoption.isEmbedded())) {
                String combinedField = Stream.of(adoption.getRefinedSpecialMark(), adoption.getTagsField())
                        .filter(field -> field != null && !field.isBlank())
                        .collect(Collectors.joining(","));

                float[] embedding = adoptionAiService.embed(combinedField);
                adoption.embed(embedding);
            }

            // 지역 정보
            RegionInfoDto regionInfoDto = RegionInfoDto.from(adoptionQueryService.findShelterInfoByAdoptionId(
                    adoption.getAdoptionId()));

            adoption.regionInfo(regionInfoDto);
        }
        // 임베딩이 포함된 Adoption 리스트를 Repo에 전달
        adoptionEsRepository.saveAdoptionToEs(adoptions);

        // 임베딩 완료 상태를 DB에도 반영
        adoptionUpdateRepository.updateIsEmbedded(adoptions);
    }
}
