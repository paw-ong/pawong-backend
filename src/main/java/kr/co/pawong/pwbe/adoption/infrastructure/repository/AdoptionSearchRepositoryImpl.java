package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdoptionSearchRepositoryImpl implements AdoptionSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Adoption> searchSimilarAdoptions(AdoptionSearchCondition condition) {
        SearchHits<AdoptionDocument> hits = searchSimilarAdoptionSearchHits(condition);
        return hits.stream()
                .map(SearchHit::getContent)
                .map(AdoptionDocument::toModel)
                .toList();
    }

    @Override
    public SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(AdoptionSearchCondition condition) {
        BoolQuery.Builder filterQueryBuilder = new BoolQuery.Builder();

        if (condition.getUpKindCds() != null && !condition.getUpKindCds().isEmpty()) {
            filterQueryBuilder.filter(f -> f.terms(t -> t
                    .field("upKindCd")
                    .terms(ts -> ts.value(condition.getUpKindCds().stream()
                            .map(upKindCd -> FieldValue.of(upKindCd.toString()))
                            .toList()))
            ));
        }

        if (condition.getSexCd() != null) {
            filterQueryBuilder.filter(f -> f.term(t -> t
                    .field("sexCd")
                    .value(condition.getSexCd().toString())
            ));
        }

        if (condition.getNeuterYn() != null) {
            filterQueryBuilder.filter(f -> f.term(t -> t
                    .field("neuterYn")
                    .value(condition.getNeuterYn().toString())
            ));
        }

        // 하이브리드 검색 쿼리 (텍스트 + 임베딩)
        BoolQuery.Builder semanticQueryBuilder = new BoolQuery.Builder();

        // 정제된 검색 텍스트
        if (condition.getRefinedSearchTerm() != null && !condition.getRefinedSearchTerm().isEmpty()) {
            semanticQueryBuilder.should(s -> s.match(m -> m
                    .field("searchField")
                    .query(condition.getRefinedSearchTerm())
                    .analyzer("korean")
                    .boost(1.0f)    // 가중치를 1배 적용
            ));
        }

        // embedding 검색
        if (condition.getEmbedding() != null && condition.getEmbedding().length == 1536) {

            // embedding 벡터 단순 주소값으로 전달 안하고, 실제 값들 반환
            float[] embedding = condition.getEmbedding();
            List<Float> embeddingList = new ArrayList<>(embedding.length);
            for (float f : embedding) {
                embeddingList.add(f);
            }

            semanticQueryBuilder.should(s -> s.scriptScore(ss -> ss
                    .query(q -> q.matchAll(m -> m)) // 모든 document 대상
                    .script(sc -> sc.inline(i -> i
                            .source("cosineSimilarity(params.query_vector, 'embedding') + 1.0")
                            .params("query_vector", JsonData.of(embeddingList))
                    ))
                    .boost(2.0f)    // 가중치를 2배 적용 (비중을 형태소 분석 대비 높게 적용한다.)
            ));
        }

        // 3. 최종 쿼리 조합
        Query finalQuery = new BoolQuery.Builder()
                .must(filterQueryBuilder.build()._toQuery()) // 필터 적용 (must 쿼리가 키워드와 결합되는 경우 정확한 keyword로 필터링함)
                .must(semanticQueryBuilder.build()._toQuery()) // 하이브리드 검색 (must가 텍스트와 결합되는 경우 형태소분석 + vector 유사도로 검색됨)
                .build()._toQuery();

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .withMaxResults(20)     // 임의로 20개
                .build();

        return elasticsearchOperations.search(searchQuery, AdoptionDocument.class);
    }
}
