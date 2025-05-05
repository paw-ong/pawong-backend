package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.service.port.ShelterQueryRepository;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShelterQueryServiceImpl implements ShelterQueryService {

    private final ShelterQueryRepository shelterQueryRepository;

    @Override
    public ShelterInfoDto shelterInfo(String careRegNo) {
        ShelterInfoDto shelterInfoDto = shelterQueryRepository.shelterInfo(careRegNo);

        if (shelterInfoDto == null) {
            return new ShelterInfoDto(careRegNo, "", "");
        }

        return shelterInfoDto;
    }
}
