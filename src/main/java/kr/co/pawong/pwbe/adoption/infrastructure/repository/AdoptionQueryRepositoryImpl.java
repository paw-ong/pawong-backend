package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdoptionQueryRepositoryImpl implements AdoptionQueryRepository {

    private final AdoptionJpaRepository adoptionJpaRepository;

    @Override
    public Adoption findById(Long desertionNo) {
        AdoptionEntity entity = adoptionJpaRepository.findByDesertionNo(desertionNo)
        .orElseThrow(() -> new EntityNotFoundException("Adoption not found with id: " + desertionNo));

        return entity.toModel();
    }
}
