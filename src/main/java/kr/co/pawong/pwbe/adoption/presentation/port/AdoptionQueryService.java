package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;

public interface AdoptionQueryService {

//    String findCarRegNoByAdoptionId(Long id);

    ShelterInfoDto findShelterInfoByAdoptionId(Long adoptionId);
}
