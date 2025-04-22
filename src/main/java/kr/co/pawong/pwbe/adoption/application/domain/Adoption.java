package kr.co.pawong.pwbe.adoption.application.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.pawong.pwbe.adoption.enums.ActiveState;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.ProcessState;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Adoption {
    private Long adoptionId; // 입양id
    private String desertionNo; // 구조번호
    private LocalDate happenDt; // 접수일
    private String happenPlace; // 발견장소
    private UpKindNm upKindNm; // 축종명
    private UpKindCd upKindCd; // 축종코드
    private String kindNm; // 품종명
    private String kindCd; // 품종코드
    private String colorCd; // 색상
    private Integer age; // 나이 - Long
    private String weight; // 체중
    private String noticeNo; // 공고번호
    private LocalDate noticeSdt; // 공고시작일
    private LocalDate noticeEdt; // 공고종료일
    private String popfile1; // 이미지1(텍스트)
    private String popfile2; // 이미지2(텍스트)
    private ProcessState processState; // 상태
    private ActiveState activeState;
    private SexCd sexCd; // 성별
    private NeuterYn neuterYn; // 중성화여부(타입)
    private String specialMark; // 특징
    private LocalDateTime updTm; // 수정일
    private Shelter shelter; // 보호소id(외래키)

}
