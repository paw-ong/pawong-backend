package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionQueryBuilder;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdoptionSearchRepositoryImpl implements AdoptionSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    private final AdoptionQueryBuilder queryBuilder;

    @Override
    public List<Adoption> searchSimilarAdoptions(AdoptionSearchCondition condition) {
        return searchSimilarAdoptionSearchHits(condition).stream()
            .map(SearchHit::getContent)
            .map(AdoptionDocument::toModel)
            .toList();
    }

    @Override
    public SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(
        AdoptionSearchCondition condition) {
        Query finalQuery = queryBuilder.buildFinalQuery(condition);

        NativeQuery searchQuery = NativeQuery.builder()
            .withQuery(finalQuery)
            .withMaxResults(20)
            .build();

        return elasticsearchOperations.search(searchQuery, AdoptionDocument.class);
    }
}
