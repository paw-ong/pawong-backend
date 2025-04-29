package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.service.port.ShelterQueryRepository;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterQueryServiceImpl implements ShelterQueryService {

    private final ShelterQueryRepository shelterQueryRepository;

    public ShelterInfoDto shelterInfo(String careRegNo) {
        return shelterQueryRepository.shelterInfo(careRegNo);
    }
}
