package kr.co.pawong.pwbe.adoption.presentation.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionDetailDto;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionRecommendResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionDetailResponse;
import org.springframework.data.domain.Pageable;


import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import org.springframework.data.domain.Pageable;

public interface AdoptionQueryService {
    List<Adoption> getAllAdoptions();

    SliceAdoptionSearchResponses fetchSlicedAdoptions(Pageable pageable);

    ShelterInfoDto findShelterInfoByAdoptionId(Long adoptionId);

    AdoptionDetailResponse getAdoptionDetail(Long adoptionId);

    AdoptionRecommendResponses getRecommendAdoptions();
}
