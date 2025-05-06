package kr.co.pawong.pwbe.adoption.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionEsDto;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.RegionInfoDto;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionEsRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionEsService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionEsServiceImpl implements AdoptionEsService {
    private final AdoptionEsRepository adoptionEsRepository;
    private final AdoptionUpdateRepository adoptionUpdateRepository;
    private final AdoptionAiService adoptionAiService;
    private final AdoptionQueryService adoptionQueryService;

    @Value("${adoption.batch-size:50}")
    private int batchSize;

    // 전달받은 Adoption 리스트에 대해 임베딩 및 임베딩 완료 여부 설정,
    // shelter에서 가져온 지역 정보와 같이 ES에 저장 후 RDB에 isEmbedded 업데이트
    // TODO: 이후 ES와 RDB에 원자적 저장 보장하기
    @Override
    public void saveAdoptionToEs(List<Adoption> adoptions) {
        List<Adoption> adoptionsToEmbed = adoptions.stream()
                .filter(adoption -> adoption.isAiProcessed() && !adoption.isEmbedded())
                .toList();

        List<List<Adoption>> batches = splitIntoBatches(adoptionsToEmbed,batchSize);

        for (List<Adoption> batch : batches) {
            processBatch(batch);
        }

        List<AdoptionEsDto> adoptionEsDtos = adoptions.stream()
                .map(adoption -> {
                    RegionInfoDto regionInfoDto = RegionInfoDto.from(adoptionQueryService.findShelterInfoByAdoptionId(
                            adoption.getAdoptionId()));

                    return AdoptionEsDto.from(adoption, regionInfoDto);
                })
                .toList();

        // 임베딩이 포함된 Adoption 리스트를 Repo에 전달
        adoptionEsRepository.saveAdoptionToEs(adoptionEsDtos);

        // 임베딩 완료 상태를 DB에도 반영
        adoptionUpdateRepository.updateIsEmbedded(adoptions);
    }

    private void processBatch(List<Adoption> batch) {
        List<String> combinedFields = new ArrayList<>();
        List<Adoption> adoptionsToEmbed = new ArrayList<>();

        for (Adoption adoption : batch) {
            String combinedField = Stream.of(adoption.getRefinedSpecialMark(),
                            adoption.getTagsField())
                    .filter(field -> field != null && !field.isBlank())
                    .collect(Collectors.joining(","));

            // 임베딩할 필드가 비어있지 않은 경우만 임베딩 대상에 추가
            if (!combinedField.isBlank()) {
                combinedFields.add(combinedField);
                adoptionsToEmbed.add(adoption);
            } else {
                log.warn("Adoption ID {} has no valid fields to embed", adoption.getAdoptionId());
            }
        }

        if (!combinedFields.isEmpty()) {
            try {
                List<Optional<float[]>> embeddings = adoptionAiService.embedBatch(combinedFields);

                for (int i = 0; i < adoptionsToEmbed.size(); i++) {
                    Optional<float[]> embedding = embeddings.get(i);
                    if (embedding.isPresent()) {
                        adoptionsToEmbed.get(i).embed(embedding.get());
                    } else {
                        log.error("Failed to embed adoption ID {}", adoptionsToEmbed.get(i).getAdoptionId());
                    }
                }
            } catch (Exception e) {
                log.error("Error during batch embedding: {}", e.getMessage());
                for (int i = 0; i < adoptionsToEmbed.size(); i++) {
                    try {
                        float[] embedding = adoptionAiService.embed(combinedFields.get(i));
                        adoptionsToEmbed.get(i).embed(embedding);
                    } catch (Exception e1) {
                        log.error("Error embedding adoption ID {}: {}", adoptionsToEmbed.get(i).getAdoptionId(), e1.getMessage());
                    }
                }
            }
        }
    }

    private <T> List<List<T>> splitIntoBatches(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, list.size());
            batches.add(list.subList(i, endIndex));
        }
        return batches;
    }
}
