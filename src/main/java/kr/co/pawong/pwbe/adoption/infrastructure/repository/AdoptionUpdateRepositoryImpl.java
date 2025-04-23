package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdoptionUpdateRepositoryImpl implements AdoptionUpdateRepository {

    private final AdoptionJpaRepository adoptionJpaRepository;

    // Adoption -> AdoptionEntity 데이터 저장
    @Override
    public void saveAdoption(List<Adoption> adoptions) {
        List<AdoptionEntity> adoptionEntities = adoptions.stream()
                .map(AdoptionEntity::from)
                .toList();

        List<AdoptionEntity> savedAdoptionEntity = adoptionJpaRepository.saveAll(adoptionEntities);
        log.info("{}개의 입양 정보가 저장되었습니다.", adoptions.size());

        savedAdoptionEntity.stream()
                .map(AdoptionEntity::toModel)
                .toList();
    }


}
