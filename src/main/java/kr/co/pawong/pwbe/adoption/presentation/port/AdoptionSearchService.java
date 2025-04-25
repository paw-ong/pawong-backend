package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionQueryResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import org.springframework.stereotype.Service;

@Service
public interface AdoptionSearchService {

    // RDB에서 adoptionId로 최종 adoptions 반환
    AdoptionQueryResponses search(AdoptionSearchRequest request);

    // ES에서 조회해서 adoptionIds 반환
    AdoptionSearchResponses searchDocumentIds(AdoptionSearchRequest request);
}
