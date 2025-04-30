package kr.co.pawong.pwbe.adoption.application.service.port;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface AdoptionSearchRepository {
    // Adoption 리스트로 반환
    List<Adoption> searchSimilarAdoptions(AdoptionSearchCondition condition);

    // 검색 결과의 전체 metadata 포함
    SearchHits<AdoptionDocument> searchSimilarAdoptionSearchHits(AdoptionSearchCondition condition);

}
