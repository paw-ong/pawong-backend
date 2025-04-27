package kr.co.pawong.pwbe.adoption.application.service.port;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

public interface AdoptionQueryRepository {
    Adoption findByIdOrThrow(Long id);
}
