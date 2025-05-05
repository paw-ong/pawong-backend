package kr.co.pawong.pwbe.shelter.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
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
    public Shelter findByCareRegNo(String careRegNo) {
        return shelterJpaRepository.findByCareRegNo(careRegNo)
                .map(ShelterEntity::toModel)
                .orElseThrow(() ->
                        new EntityNotFoundException("보호소 정보가 없습니다. 번호=" + careRegNo)
                );
    }
}
