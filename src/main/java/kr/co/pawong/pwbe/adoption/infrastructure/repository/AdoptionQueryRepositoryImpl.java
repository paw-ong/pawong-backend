package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdoptionQueryRepositoryImpl implements AdoptionQueryRepository {

    private final AdoptionJpaRepository adoptionJpaRepository;

    @Override
    public Adoption findByIdOrThrow(Long id) {
        AdoptionEntity entity = adoptionJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adoption not found with id: " + id));

        return entity.toModel();
    }

    @Override
    public Page<Adoption> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "noticeSdt"));
        Page<AdoptionEntity> entityPage = adoptionJpaRepository.findAll(pageable);
        return entityPage.map(AdoptionEntity::toModel);
    }
}