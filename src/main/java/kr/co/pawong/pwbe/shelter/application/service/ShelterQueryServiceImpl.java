package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import kr.co.pawong.pwbe.shelter.application.service.port.ShelterQueryRepository;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterDetailDto;
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
        Shelter shelter = shelterQueryRepository.findByCareRegNoOrThrow(careRegNo);

        ShelterInfoDto shelterInfoDto = ShelterInfoDto.from(shelter);

        if (shelterInfoDto == null) {
            return new ShelterInfoDto(careRegNo, "", "");
        }

        return shelterInfoDto;
    }

    @Override
    public ShelterDetailDto shelterDetail(String careRegNo){
        Shelter shelter = shelterQueryRepository.findByCareRegNoOrThrow(careRegNo);

        return ShelterDetailDto.from(shelter);
    }
}
