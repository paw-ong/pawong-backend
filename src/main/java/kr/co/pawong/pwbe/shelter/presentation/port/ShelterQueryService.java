package kr.co.pawong.pwbe.shelter.presentation.port;

import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;

public interface ShelterQueryService {

    ShelterInfoDto shelterInfo(String careRegNo);
}
