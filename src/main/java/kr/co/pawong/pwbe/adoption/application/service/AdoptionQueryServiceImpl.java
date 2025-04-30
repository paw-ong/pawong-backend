package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import kr.co.pawong.pwbe.shelter.infrastructure.adapter.ShelterAdapter;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptionQueryServiceImpl implements AdoptionQueryService {

    private final AdoptionQueryRepository adoptionQueryRepository;
    private final ShelterAdapter shelterAdapter;

    @Override
    public ShelterInfoDto findShelterInfoByAdoptionId(Long adoptionId) {
        // 1) AdoptionEntity에서 careRegNo 조회
        String careRegNo = adoptionQueryRepository.findCareRegNoByAdoptionId(adoptionId);
        // 2) ShelterAdapter 통해 실제 Shelter 컨텍스트에 질의
        return shelterAdapter.getShelterInfo(careRegNo);
    }

}
