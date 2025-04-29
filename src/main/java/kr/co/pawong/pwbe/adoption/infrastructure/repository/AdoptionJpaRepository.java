package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdoptionJpaRepository extends JpaRepository<AdoptionEntity, Long> {

}
