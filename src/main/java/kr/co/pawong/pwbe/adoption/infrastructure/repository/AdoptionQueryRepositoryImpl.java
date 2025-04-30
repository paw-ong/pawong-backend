package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdoptionQueryRepositoryImpl implements AdoptionQueryRepository {

    private final AdoptionJpaRepository adoptionJpaRepository;

    /**
     * DB에 저장된 모든 AdoptionEntity를 Adoption 도메인 객체로 변환하여 반환
     */
    @Override
    public List<Adoption> convertToAdoptions() {
        return adoptionJpaRepository.findAll().stream()
                .map(AdoptionEntity::toModel)
                .toList();
    }
}
