package kr.co.pawong.pwbe.shelter.infrastructure.adapter;

import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShelterAdapter {

    private final ShelterQueryService shelterQueryService;

    public ShelterInfoDto getShelterInfo(String careRegNo) {
        return shelterQueryService.shelterInfo(careRegNo);
    }


}
