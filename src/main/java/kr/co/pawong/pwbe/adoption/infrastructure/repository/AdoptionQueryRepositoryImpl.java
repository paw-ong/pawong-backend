package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class AdoptionQueryRepositoryImpl implements AdoptionQueryRepository {

    private final AdoptionJpaRepository adoptionJpaRepository;

    @Override
    public String findCareRegNoByAdoptionId(Long id) {
        return adoptionJpaRepository.findCareRegNoByAdoptionId(id);
    }

}
