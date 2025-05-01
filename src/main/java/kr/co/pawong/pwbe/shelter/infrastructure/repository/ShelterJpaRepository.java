package kr.co.pawong.pwbe.shelter.infrastructure.repository;

import kr.co.pawong.pwbe.shelter.infrastructure.repository.entity.ShelterEntity;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShelterJpaRepository extends JpaRepository<ShelterEntity, Long> {

    List<ShelterEntity> findByCareNm(String careNm);
//    List<ShelterEntity> findByCareRegNo(String careRegNo);

    /**
     * 모든 ShelterEntity의 careRegNo만 추출해서 리스트로 반환하는 메서드
     */
    @Query("SELECT s.careRegNo FROM ShelterEntity s")
    List<String> findAllCareRegNos();

    // adoption 에서 careRegNo를 받아 보호소 정보 반환
    @Query("SELECT new kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto(s.careRegNo, s.city, s.district) " +
            "FROM ShelterEntity s WHERE s.careRegNo = :careRegNo")
    ShelterInfoDto shelterInfo(@Param("careRegNo") String careRegNo);

    ShelterEntity findByCareRegNo(String careRegNo);
}
