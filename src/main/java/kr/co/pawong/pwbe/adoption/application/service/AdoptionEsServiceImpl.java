package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionEsRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.enums.ActiveState;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionEsServiceImpl implements AdoptionEsService {
    private final AdoptionEsRepository adoptionEsRepository;
    private final AdoptionUpdateRepository adoptionUpdateRepository;
    private final AdoptionAiService adoptionAiService;

    /**
     * 전달받은 Adoption 리스트에 대해 임베딩 및 임베딩 완료 여부(embeddingDone) 설정 후
     * ES(Elasticsearch)에 저장하고, DB의 embeddingDone 상태를 일괄 업데이트합니다.
     *
     * <p>
     * - refinedSpecialMark 또는 tagsField가 있고 ACTIVE 상태인 경우 임베딩 수행 및 embeddingDone(true)<br>
     * - 아니면 embeddingDone(false)로 설정<br>
     * - 임베딩이 포함된 Adoption 리스트를 ES에 저장<br>
     * - embeddingDone 필드를 DB에도 반영
     * </p>
     *
     * @param adoptions 임베딩 및 저장할 Adoption 리스트
     */
    @Override
    public void saveAdoptionToEs(List<Adoption> adoptions) {
        adoptions.forEach(adoption -> {
            boolean hasSearch = adoption.getRefinedSpecialMark() != null && !adoption.getRefinedSpecialMark().isBlank();
            boolean hasTag = adoption.getTagsField() != null && !adoption.getTagsField().isBlank();

            if ((hasSearch || hasTag) && adoption.getActiveState() == ActiveState.ACTIVE) {
                String combinedField = Stream.of(adoption.getRefinedSpecialMark(), adoption.getTagsField())
                        .filter(field -> field != null && !field.isBlank())
                        .collect(Collectors.joining(","));

                float[] embedding = adoptionAiService.embed(combinedField);
                adoption.embed(embedding);
                adoption.isEmbedded(true);
            } else {
                adoption.isEmbedded(false);
            }
        });
        // 임베딩이 포함된 Adoption 리스트를 Repo에 전달
        adoptionEsRepository.saveAdoptionToEs(adoptions);

        // 임베딩 완료 상태를 DB에도 반영
        adoptionUpdateRepository.updateIsEmbedded(adoptions);
    }
}
