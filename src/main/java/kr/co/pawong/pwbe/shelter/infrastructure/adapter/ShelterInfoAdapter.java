package kr.co.pawong.pwbe.shelter.infrastructure.adapter;

import kr.co.pawong.pwbe.adoption.application.service.port.ShelterInfoPort;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShelterInfoAdapter implements ShelterInfoPort {

    private final ShelterQueryService shelterQueryService;

    @Override
    public ShelterInfoDto getShelterInfo(String careRegNo) {
        return shelterQueryService.shelterInfo(careRegNo);
    }


}
