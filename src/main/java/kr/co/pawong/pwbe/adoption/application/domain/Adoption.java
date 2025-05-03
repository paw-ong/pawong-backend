package kr.co.pawong.pwbe.adoption.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.RegionInfoDto;
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
    private String tagsField; // 태깅
    private String refinedSpecialMark; // 정제 데이터
    private float[] embedding; // 임베딩 값
    private boolean isAiProcessed = false; // 정제 여부
    private boolean isEmbedded = false; // 임베딩 여부
    private RegionInfoDto regionInfo;

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

    /**
     * AI 정제 결과로 searchField, tagsField, aiProcessed 값을 갱신하는 메서드
     *
     * @param refinedSpecialMark   정제된 검색 필드 값
     * @param tagsField     정제된 태그 필드 값
     */
    public void updateAiField(String refinedSpecialMark, String tagsField) {
        this.refinedSpecialMark = refinedSpecialMark;
        this.tagsField = tagsField;
        this.isAiProcessed = true;
    }

    /**
     * 임베딩 결과를 Adoption 객체에 저장하는 메서드
     *
     * @param embedding 임베딩 벡터 값
     */
    public void embed(float[] embedding) {
        this.embedding = embedding;
        this.isEmbedded = true;
    }

    /**
     * Adoption 도메인 객체로부터 정제 필드(refinedSpecialMark)용 텍스트를 생성하고
     * AI 서비스로 정제 결과를 반환하는 메서드
     * (kindNm, colorCd, specialMark를 공백으로 연결하여 baseText로 사용)
     *
     * @return 정제 필드에 들어갈 정제된 문자열
     */
    public String extractRefinedSpecialMark() {

        return String.join(" ",
                this.kindNm != null ? this.kindNm : "",
                this.colorCd != null ? this.colorCd : "",
                this.specialMark != null ? this.specialMark : ""
        ).trim();
    }

    /**
     * Adoption 도메인 객체로부터 태그 필드(tagsField)용 텍스트를 생성하고
     * AI 서비스로 태그 추출 결과를 반환하는 메서드
     * (kindNm, colorCd, age, weight, specialMark를 공백으로 연결하여 baseText로 사용)
     *
     * @return 태그 필드에 들어갈 정제된 문자열
     */
    public String extractTagsField() {
        return String.join(" ",
                this.kindNm != null ? this.kindNm : "",
                this.colorCd != null ? this.colorCd : "",
                this.age != null ? String.valueOf(this.age) : "",
                this.weight != null ? this.weight : "",
                this.specialMark != null ? this.specialMark : ""
        ).trim();
    }

    public void regionInfo(RegionInfoDto regionInfoDto) {
        this.regionInfo = regionInfoDto;
    }
}