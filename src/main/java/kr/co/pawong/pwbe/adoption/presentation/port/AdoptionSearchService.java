package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import org.springframework.stereotype.Service;

@Service    // testing..
public interface AdoptionSearchService {

    AdoptionSearchResponses search(AdoptionSearchRequest condition);
}
