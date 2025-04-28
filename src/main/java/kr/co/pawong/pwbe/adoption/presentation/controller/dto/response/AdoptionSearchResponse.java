package kr.co.pawong.pwbe.adoption.presentation.controller.dto.response;

import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdoptionSearchResponse {
    private Long adoptionId;
    private String popfile1;
    private String kindNm;
    private SexCd sexCd;
    private Integer age;
    private NeuterYn neuterYn;
}
