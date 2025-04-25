package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

public interface AdoptionQueryService {

    Adoption fetchAdoptionById(Long id);
}