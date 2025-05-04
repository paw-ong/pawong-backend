package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdoptionSearchService {

    // RDB에서 adoptionId로 최종 adoptions 반환
    AdoptionSearchResponses search(AdoptionSearchRequest request);

    // ES에서 조회해서 adoptionIds 반환
    AdoptionIdSearchResponses searchDocumentIds(AdoptionSearchRequest request);

    // ES에서 조회해서 자동완성 리스트 반환
    List<String> autocomplete(String keyword);
}
