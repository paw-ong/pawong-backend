package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AdoptionJpaRepository extends JpaRepository<AdoptionEntity, Long> {

    @Query("SELECT a.careRegNo FROM AdoptionEntity a WHERE a.adoptionId = :id")
    String findCareRegNoByAdoptionId(@Param("id") Long id);


}
