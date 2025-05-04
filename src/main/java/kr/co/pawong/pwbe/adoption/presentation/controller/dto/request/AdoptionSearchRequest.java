package kr.co.pawong.pwbe.adoption.presentation.controller.dto.request;

import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdoptionSearchRequest {
    private List<UpKindCd> upKindCds;   // ex: upKindCds=KIND1&upKindCds=KIND2
    private SexCd sexCd;              // ex: sexCd=FEMALE
    private NeuterYn neuterYn;        // ex: neuterYn=Y
//    private List<String> regions;     // ex: regions=서울&regions=부산, 추후에 추가
    private String searchTerm;           // 검색어 문장
}