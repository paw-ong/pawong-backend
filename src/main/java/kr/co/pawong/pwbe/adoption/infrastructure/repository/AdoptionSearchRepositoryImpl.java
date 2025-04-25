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
        return searchSimilarAdoptionSearchHits(condition).stream()
                .map(SearchHit::getContent)
                .map(AdoptionDocument::toModel)
                .toList();
    }

    @Override
    public SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(AdoptionSearchCondition condition) {
        Query finalQuery = buildFinalQuery(condition);

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .withMaxResults(20)
                .build();

        return elasticsearchOperations.search(searchQuery, AdoptionDocument.class);
    }

    private Query buildFinalQuery(AdoptionSearchCondition condition) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        boolQuery.must(buildFilterQuery(condition));
        boolQuery.must(buildSemanticQuery(condition));
        return boolQuery.build()._toQuery();
    }

    private Query buildFilterQuery(AdoptionSearchCondition condition) {
        BoolQuery.Builder filter = new BoolQuery.Builder();

        if (condition.getUpKindCds() != null && !condition.getUpKindCds().isEmpty()) {
            filter.filter(f -> f.terms(t -> t
                    .field("upKindCd")
                    .terms(ts -> ts.value(condition.getUpKindCds().stream()
                            .map(cd -> FieldValue.of(cd.toString()))
                            .toList()))
            ));
        }

        if (condition.getSexCd() != null) {
            filter.filter(f -> f.term(t -> t
                    .field("sexCd")
                    .value(condition.getSexCd().toString())
            ));
        }

        if (condition.getNeuterYn() != null) {
            filter.filter(f -> f.term(t -> t
                    .field("neuterYn")
                    .value(condition.getNeuterYn().toString())
            ));
        }

        return filter.build()._toQuery();
    }

    private Query buildSemanticQuery(AdoptionSearchCondition condition) {
        BoolQuery.Builder semantic = new BoolQuery.Builder();

        if (condition.getRefinedSearchTerm() != null && !condition.getRefinedSearchTerm().isEmpty()) {
            semantic.should(s -> s.match(m -> m
                    .field("searchField")
                    .query(condition.getRefinedSearchTerm())
                    .analyzer("korean")
                    .boost(1.0f)    // 형태소는 1배의 가중치
            ));
        }

        if (condition.getEmbedding() != null && condition.getEmbedding().length == 1536) {
            List<Float> embeddingList = new ArrayList<>(condition.getEmbedding().length);
            for (float f : condition.getEmbedding()) {
                embeddingList.add(f);
            }

            semantic.should(s -> s.scriptScore(ss -> ss
                    .query(q -> q.matchAll(m -> m))
                    .script(sc -> sc.inline(i -> i
                            .source("cosineSimilarity(params.query_vector, 'embedding') + 1.0") // cosine similarity 활용
                            .params("query_vector", JsonData.of(embeddingList))
                    ))
                    .boost(2.0f)    // 벡터는 2배의 가중치
            ));
        }

        return semantic.build()._toQuery();
    }
}