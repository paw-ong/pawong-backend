package kr.co.pawong.pwbe.shelter.application.service.port;

import kr.co.pawong.pwbe.shelter.infrastructure.repository.entity.ShelterEntity;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;

public interface ShelterQueryRepository {

    ShelterInfoDto shelterInfo(String careRegNo);

    ShelterEntity findByCareRegNo(String careRegNo);
}
