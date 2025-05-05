package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import kr.co.pawong.pwbe.shelter.application.service.port.ShelterQueryRepository;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterDetailDto;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShelterQueryServiceImpl implements ShelterQueryService {

    private final ShelterQueryRepository shelterQueryRepository;

    @Override
    public ShelterInfoDto shelterInfo(String careRegNo) {
        // Repository 통해 Entity 조회
        var entity = shelterQueryRepository.findByCareRegNo(careRegNo);
        if (entity == null) {
            log.warn("ShelterEntity not found for careRegNo: {}", careRegNo);
            return null;
        }
        // DTO로 변환
        return new ShelterInfoDto(entity.getCareRegNo(),
                entity.getCity(),
                entity.getDistrict());
    }

    @Override
    public ShelterDetailDto shelterDetail(String careRegNo){
        Shelter shelter = shelterQueryRepository.findByCareRegNo(careRegNo);

        return ShelterDetailDto.from(shelter);
    }
}
