package kr.co.pawong.pwbe.adoption.application.service.port;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdoptionQueryRepository {
    Adoption findByIdOrThrow(Long adoptionId);
    Page<Adoption> findAllPaged(Pageable pageable);

    String findCareRegNoByAdoptionId(Long id);

}