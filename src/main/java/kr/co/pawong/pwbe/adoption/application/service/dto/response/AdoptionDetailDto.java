package kr.co.pawong.pwbe.adoption.application.service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AdoptionDetailDto {
    private String careRegNo; // 보호소 번호
    @Enumerated(EnumType.STRING)
    private UpKindNm upKindNm; // 축종명
    @Enumerated(EnumType.STRING)
    private SexCd sexCd; // 성별
    @Enumerated(EnumType.STRING)
    private NeuterYn neuterYn; // 중성화여부(타입)
    private String weight; // 체중
    private Integer age; // 나이
    private String colorCd; // 색상
    private String desertionNo; // 구조번호
    private LocalDate noticeEdt; // 공고종료일
    private String tagsField; // 태깅
    @Column(columnDefinition = "TEXT")
    private String popfile1; // 이미지1(텍스트)
    @Column(columnDefinition = "TEXT")
    private String popfile2; // 이미지2(텍스트)

    public AdoptionDetailDto(String careRegNo, UpKindNm upKindNm, SexCd sexCd,
                             NeuterYn neuterYn, String weight, Integer age, String colorCd,
                             String desertionNo, LocalDate noticeEdt, String tagsField,
                             String popfile1, String popfile2) {
        this.careRegNo = careRegNo;
        this.upKindNm = upKindNm;
        this.sexCd = sexCd;
        this.neuterYn = neuterYn;
        this.weight = weight;
        this.age = age;
        this.colorCd = colorCd;
        this.desertionNo = desertionNo;
        this.noticeEdt = noticeEdt;
        this.tagsField = tagsField;
        this.popfile1 = popfile1;
        this.popfile2 = popfile2;
    }
}
