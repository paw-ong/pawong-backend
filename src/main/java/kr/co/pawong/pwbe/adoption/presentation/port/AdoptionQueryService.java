package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import org.springframework.data.domain.Pageable;


import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;

public interface AdoptionQueryService {
    SliceAdoptionSearchResponses fetchSlicedAdoptions(Pageable pageable);

    ShelterInfoDto findShelterInfoByAdoptionId(Long adoptionId);
}
