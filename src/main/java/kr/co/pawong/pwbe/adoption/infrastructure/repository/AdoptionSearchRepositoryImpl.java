package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class AdoptionSearchRepositoryImpl implements AdoptionEsRepository {


    @Override
    public List<AdoptionDocument> searchSimilarAdoptions(AdoptionSearchCondition condition) {
        return List.of();
    }

    @Override
    public SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(AdoptionSearchCondition condition) {
        return null;
    }
}
