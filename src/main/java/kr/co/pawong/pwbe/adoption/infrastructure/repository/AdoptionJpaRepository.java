package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdoptionJpaRepository extends JpaRepository<AdoptionEntity, Long> {

    Optional<AdoptionEntity> findByDesertionNo(Long desertionNo);
}
