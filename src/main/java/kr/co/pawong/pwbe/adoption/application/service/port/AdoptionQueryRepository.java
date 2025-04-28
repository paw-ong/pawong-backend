package kr.co.pawong.pwbe.adoption.application.service.port;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import org.springframework.data.domain.Page;

public interface AdoptionQueryRepository {
    Adoption findByIdOrThrow(Long id);
    Page<Adoption> findAllPaged(int page, int size);
}