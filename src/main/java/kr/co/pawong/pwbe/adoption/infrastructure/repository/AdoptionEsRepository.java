package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionEsRepository {
    // AdoptionDocument 리스트로 반환
    List<AdoptionDocument> searchSimilarAdoptions(AdoptionSearchCondition condition);

    // 검색 결과의 전체 metadata 포함
    SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(AdoptionSearchCondition condition);

}
