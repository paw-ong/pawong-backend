package kr.co.pawong.pwbe.adoption.application.service.port;

import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;

public interface ShelterInfoPort {
    ShelterInfoDto getShelterInfo(String careRegNo);
}
