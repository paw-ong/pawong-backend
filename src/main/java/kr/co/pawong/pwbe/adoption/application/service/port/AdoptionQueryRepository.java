package kr.co.pawong.pwbe.adoption.application.service.port;

import java.time.LocalDate;
import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdoptionQueryRepository {
    // AdoptionEntity -> Adoption
    List<Adoption> findAll();

    Adoption findByIdOrThrow(Long adoptionId);

    Page<Adoption> findAllPaged(Pageable pageable);

    String findCareRegNoByAdoptionId(Long adoptionId);

    AdoptionEntity findByAdoptionId(Long adoptionId);

    List<Adoption> findTop12ActiveByNoticeEdt(LocalDate today);
}