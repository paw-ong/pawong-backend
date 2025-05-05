package kr.co.pawong.pwbe.shelter.infrastructure.repository;

import kr.co.pawong.pwbe.shelter.application.service.port.ShelterQueryRepository;
import kr.co.pawong.pwbe.shelter.infrastructure.repository.entity.ShelterEntity;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShelterQueryRepositoryImpl implements ShelterQueryRepository {

    private final ShelterJpaRepository shelterJpaRepository;

    @Override
    public ShelterEntity findByCareRegNo(String careRegNo) {
        return shelterJpaRepository.findByCareRegNo(careRegNo);
    }
}
