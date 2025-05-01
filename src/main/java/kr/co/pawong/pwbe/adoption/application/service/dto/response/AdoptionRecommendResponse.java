package kr.co.pawong.pwbe.adoption.application.service.dto.response;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionRecommendResponse {
    private String popfile1;
    private UpKindNm upKindNm;
    private SexCd sexCd;
    private Integer age;
    private NeuterYn neuterYn;

    public static AdoptionRecommendResponse from(Adoption adoption) {
        return AdoptionRecommendResponse.builder()
                .popfile1(adoption.getPopfile1())
                .upKindNm(adoption.getUpKindNm())
                .sexCd(adoption.getSexCd())
                .age(adoption.getAge())
                .neuterYn(adoption.getNeuterYn())
                .build();
    }
}
