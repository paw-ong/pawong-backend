package kr.co.pawong.pwbe.adoption.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.pawong.pwbe.adoption.enums.ActiveState;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.ProcessState;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
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
    private String careRegNo; // 보호소 번호
    private LocalDateTime updTm; // 수정일
    private String taggingField;
    private String searchField;

    // AdoptionCreate -> Adoption
    public static Adoption from(AdoptionCreate adoptionCreate) {
        return Adoption.builder()
                .desertionNo(adoptionCreate.getDesertionNo())
                .happenDt(adoptionCreate.getHappenDt())
                .happenPlace(adoptionCreate.getHappenPlace())
                .upKindNm(adoptionCreate.getUpKindNm())
                .upKindCd(adoptionCreate.getUpKindCd())
                .kindNm(adoptionCreate.getKindNm())
                .kindCd(adoptionCreate.getKindCd())
                .colorCd(adoptionCreate.getColorCd())
                .age(adoptionCreate.getAge())
                .weight(adoptionCreate.getWeight())
                .noticeNo(adoptionCreate.getNoticeNo())
                .noticeSdt(adoptionCreate.getNoticeSdt())
                .noticeEdt(adoptionCreate.getNoticeEdt())
                .popfile1(adoptionCreate.getPopfile1())
                .popfile2(adoptionCreate.getPopfile2())
                .processState(adoptionCreate.getProcessState())
                .activeState(adoptionCreate.getActiveState())
                .sexCd(adoptionCreate.getSexCd())
                .neuterYn(adoptionCreate.getNeuterYn())
                .specialMark(adoptionCreate.getSpecialMark())
                .careRegNo(adoptionCreate.getCareRegNo())
                .updTm(adoptionCreate.getUpdTm())
                .build();
    }

    // AdoptionUpdate -> Adoption
    public Adoption update(AdoptionUpdate adoptionUpdate) {
        return Adoption.builder()
                .adoptionId(adoptionUpdate.getAdoptionId())
                .desertionNo(adoptionUpdate.getDesertionNo())
                .happenDt(adoptionUpdate.getHappenDt())
                .happenPlace(adoptionUpdate.getHappenPlace())
                .upKindNm(adoptionUpdate.getUpKindNm())
                .upKindCd(adoptionUpdate.getUpKindCd())
                .kindNm(adoptionUpdate.getKindNm())
                .kindCd(adoptionUpdate.getKindCd())
                .colorCd(adoptionUpdate.getColorCd())
                .age(adoptionUpdate.getAge())
                .weight(adoptionUpdate.getWeight())
                .noticeNo(adoptionUpdate.getNoticeNo())
                .noticeSdt(adoptionUpdate.getNoticeSdt())
                .noticeEdt(adoptionUpdate.getNoticeEdt())
                .popfile1(adoptionUpdate.getPopfile1())
                .popfile2(adoptionUpdate.getPopfile2())
                .processState(adoptionUpdate.getProcessState())
                .activeState(adoptionUpdate.getActiveState())
                .sexCd(adoptionUpdate.getSexCd())
                .neuterYn(adoptionUpdate.getNeuterYn())
                .specialMark(adoptionUpdate.getSpecialMark())
                .careRegNo(adoptionUpdate.getCareRegNo())
                .updTm(adoptionUpdate.getUpdTm())
                .build();
    }
}
