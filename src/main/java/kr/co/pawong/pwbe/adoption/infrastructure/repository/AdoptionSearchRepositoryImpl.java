package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdoptionSearchRepositoryImpl implements AdoptionSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<AdoptionDocument> searchSimilarAdoptions(AdoptionSearchCondition condition) {
        SearchHits<AdoptionDocument> hits = searchSimilarAdoptionSearchHits(condition);
        return hits.stream()
                .map(hit -> hit.getContent())
                .toList();
    }

    @Override
    public SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(AdoptionSearchCondition condition) {
        // 1. 필터링 쿼리 (New API)
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

        // 2. 하이브리드 검색 쿼리 (텍스트 + 임베딩)
        BoolQuery.Builder semanticQueryBuilder = new BoolQuery.Builder();

        // 텍스트 검색 (searchField)
        if (condition.getRefinedSearchTerm() != null && !condition.getRefinedSearchTerm().isEmpty()) {
            semanticQueryBuilder.should(s -> s.match(m -> m
                    .field("searchField")
                    .query(condition.getRefinedSearchTerm())
                    .analyzer("korean")
                    .boost(1.0f)
            ));
        }

        // 임베딩 벡터 검색 (cosine 유사도)
        if (condition.getEmbedding() != null && condition.getEmbedding().length == 1536) {
            semanticQueryBuilder.should(s -> s.scriptScore(ss -> ss
                    .query(q -> q.matchAll(m -> m)) // 모든 문서 대상
                    .script(sc -> sc.inline(i -> i
                            .source("cosineSimilarity(params.query_vector, 'embedding') + 1.0")
                            .params("query_vector", JsonData.of(condition.getEmbedding()))
                    ))
                    .boost(2.0f)
            ));
        }

        // 3. 최종 쿼리 조합
        Query finalQuery = new BoolQuery.Builder()
                .must(filterQueryBuilder.build()._toQuery()) // 필터 적용
                .must(semanticQueryBuilder.build()._toQuery()) // 하이브리드 검색
                .build()._toQuery();

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .withMaxResults(20)
                .build();

        return elasticsearchOperations.search(searchQuery, AdoptionDocument.class);
    }
}
