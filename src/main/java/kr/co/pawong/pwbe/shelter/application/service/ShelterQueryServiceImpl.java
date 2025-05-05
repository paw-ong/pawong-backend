package kr.co.pawong.pwbe.shelter.application.service;

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
        // Repository 통해 Entity 조회
        var entity = shelterQueryRepository.findByCareRegNo(careRegNo);
        // DTO로 변환
        return new ShelterInfoDto(entity.getCareRegNo(),
                entity.getCity(),
                entity.getDistrict());
    }

    @Override
    public ShelterDetailDto shelterDetail(String careRegNo){
        var entity = shelterQueryRepository.findByCareRegNo(careRegNo);

        return new ShelterDetailDto(entity.getCareNm(),
                entity.getCareTel(),
                entity.getCloseDay(),
                entity.getSaveTrgtAnimal(),
                entity.getDivisionNm(),
                entity.getWeekOprStime(),
                entity.getWeekOprEtime(),
                entity.getVetPersonCnt(),
                entity.getSpecsPersonCnt(),
                entity.getCareAddr());
    }
}
