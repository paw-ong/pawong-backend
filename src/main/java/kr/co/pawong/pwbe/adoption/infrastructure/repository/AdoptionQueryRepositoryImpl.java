package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdoptionQueryRepositoryImpl implements AdoptionQueryRepository {

    private final AdoptionJpaRepository adoptionJpaRepository;

    /**
     * DB에 저장된 모든 AdoptionEntity를 Adoption 도메인 객체로 변환하여 반환
     */
    @Override
    public List<Adoption> findAll() {
        return adoptionJpaRepository.findAll().stream()
                .map(AdoptionEntity::toModel)
                .toList();
    }

    @Override
    public Adoption findByIdOrThrow(Long id) {
        AdoptionEntity entity = adoptionJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adoption not found with id: " + id));

        return entity.toModel();
    }

    @Override
    public Page<Adoption> findAllPaged(Pageable pageable) {
        Page<AdoptionEntity> entityPage = adoptionJpaRepository.findAll(pageable);
        return entityPage.map(AdoptionEntity::toModel);
    }
}