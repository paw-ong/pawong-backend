package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import java.util.List;
import java.util.Objects;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionIndexRepository;
import kr.co.pawong.pwbe.adoption.enums.ActiveState;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdoptionEsRepositoryImpl implements AdoptionIndexRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    // Adoption -> AdoptionDocument ES 저장
    @Override
    public void saveAdoptions(List<Adoption> adoptions) {
        if (adoptions.isEmpty()) {
            return;
        }
        String indexName = "adoption";

        try {
            // ActiveState가 ACTIVE인 데이터만 인덱스 쿼리로 변환
            List<IndexQuery> queries = adoptions.stream()
                    .filter(adoption -> ActiveState.ACTIVE.equals(adoption.getActiveState()))
                    .map(adoption -> {
                        try {
                            // Adoption -> AdoptionDocument
                            AdoptionDocument adoptionDocument = AdoptionDocument.from(adoption);
                            return new IndexQueryBuilder()
                                    .withIndex(indexName)
                                    .withObject(adoptionDocument)
                                    .build();
                        } catch (Exception e) {
                            log.error("동물 ID: {}의 문서 변환 중 오류 발생: {}", adoption.getDesertionNo(), e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            log.info("변환된 쿼리 수: {}", queries.size());

            if (queries.isEmpty()) {
                log.warn("저장할 ACTIVE 상태의 동물 데이터가 없습니다.");
                return;
            }

            // ES에 저장
            elasticsearchOperations.bulkIndex(queries, AdoptionDocument.class);
            log.info("{}개의 ACTIVE 상태 동물 데이터가 Elasticsearch에 성공적으로 저장되었습니다.", queries.size());
        } catch (Exception e) {
            log.error("Elasticsearch 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Elasticsearch 저장 실패", e);
        }
    }
}
