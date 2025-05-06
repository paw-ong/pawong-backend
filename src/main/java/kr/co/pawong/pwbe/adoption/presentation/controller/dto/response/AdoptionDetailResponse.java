package kr.co.pawong.pwbe.adoption.presentation.controller.dto.response;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionDetailDto;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionDetailResponse {
    private AdoptionDetailDto adoptionDetailDto;
    private ShelterDetailDto shelterDetailDto;
}
