package kr.co.pawong.pwbe.shelter.application.service.port;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;


public interface ShelterQueryRepository {

    Shelter findByCareRegNo(String careRegNo);
}
