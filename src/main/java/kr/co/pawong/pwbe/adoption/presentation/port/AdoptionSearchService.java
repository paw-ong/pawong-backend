package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.AdoptionSearchRequest;

import java.util.List;

public interface AdoptionSearchService {

    List<Adoption> search(AdoptionSearchRequest condition);
}
