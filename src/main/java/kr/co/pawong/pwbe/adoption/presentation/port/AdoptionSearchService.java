package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.AdoptionSearchRequest;

import java.util.List;

public interface AdoptionSearchService {

    List<AdoptionDocument> search(AdoptionSearchRequest condition);
}
