package kr.co.pawong.pwbe.adoption.application.service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionDetailDto {
    private String careRegNo; // 보호소 번호
    private UpKindNm upKindNm; // 축종명
    private SexCd sexCd; // 성별
    private NeuterYn neuterYn; // 중성화여부(타입)
    private String weight; // 체중
    private Integer age; // 나이
    private String colorCd; // 색상
    private String desertionNo; // 구조번호
    private LocalDate noticeEdt; // 공고종료일
    private String tagsField; // 태깅
    private String popfile1; // 이미지1(텍스트)
    private String popfile2; // 이미지2(텍스트)

    public static AdoptionDetailDto from(Adoption adoption) {
        return AdoptionDetailDto.builder()
                .careRegNo(adoption.getCareRegNo())
                .upKindNm(adoption.getUpKindNm())
                .sexCd(adoption.getSexCd())
                .neuterYn(adoption.getNeuterYn())
                .weight(adoption.getWeight())
                .age(adoption.getAge())
                .colorCd(adoption.getColorCd())
                .desertionNo(adoption.getDesertionNo())
                .noticeEdt(adoption.getNoticeEdt())
                .tagsField(adoption.getTagsField())
                .popfile1(adoption.getPopfile1())
                .popfile2(adoption.getPopfile2())
                .build();
    }

}
