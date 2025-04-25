package kr.co.pawong.pwbe.shelter.infrastructure.repository;

import kr.co.pawong.pwbe.shelter.infrastructure.repository.entity.ShelterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelterJpaRepository extends JpaRepository<ShelterEntity, Long> {

    List<ShelterEntity> findByCareNm(String careNm);
    List<ShelterEntity> findByCareRegNo(String careRegNo);
}
