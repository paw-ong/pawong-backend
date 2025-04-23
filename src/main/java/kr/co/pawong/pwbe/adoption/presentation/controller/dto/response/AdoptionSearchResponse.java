package kr.co.pawong.pwbe.adoption.presentation.controller.dto.response;

import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionSearchResponse {

    private Long adoptionId;    // 실제 반환용

    private String specialMark;     // 테스트: 특징 확인용

    private UpKindCd upKindCd;      // 테스트: 축종코드 필터링 확인용

    private NeuterYn neuterYn;      // 테스트: 중성화여부 필터링 확인용

    private SexCd sexCd;        // 테스트: 성별 필터링 확인용
}
