package kr.co.pawong.pwbe.adoption.application.service.port;

import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterDetailDto;

public interface ShelterDetailPort {
    ShelterDetailDto getShelterDetail(String careRegNo);
}
