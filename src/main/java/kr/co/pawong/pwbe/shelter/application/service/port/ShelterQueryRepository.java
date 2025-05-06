package kr.co.pawong.pwbe.shelter.application.service.port;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;


public interface ShelterQueryRepository {

    Shelter findByCareRegNoOrThrow(String careRegNo);
}
