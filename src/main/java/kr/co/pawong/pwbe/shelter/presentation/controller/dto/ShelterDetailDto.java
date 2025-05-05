package kr.co.pawong.pwbe.shelter.presentation.controller.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.pawong.pwbe.shelter.enums.DivisionNm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterDetailDto {
    private String careNm; // 동물보호센터명
    private String careTel; // 전화번호
    private String closeDay; // 휴무일
    private String saveTrgtAnimal; // 구조대상동물
    private DivisionNm divisionNm; // 동물보호센터 유형
    private String weekOprStime; // 평일 운영시작 시간
    private String weekOprEtime; // 평일 운영종료 시간
    private Integer vetPersonCnt; // 수의사 인원수
    private Integer specsPersonCnt; // 사양관리사 인원수
    private String careAddr; // 소재지 도로명 주소


}
