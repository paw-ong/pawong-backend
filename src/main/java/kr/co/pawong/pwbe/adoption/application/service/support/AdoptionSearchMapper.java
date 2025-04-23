package kr.co.pawong.pwbe.adoption.application.service.support;

import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponse;

public class AdoptionSearchMapper {

    public static AdoptionSearchCondition fromRequest(AdoptionSearchRequest request, String refinedSearchTerm, float[] embedding) {
        return AdoptionSearchCondition.builder()
                .upKindCds(request.getUpKindCds())
                .sexCd(request.getSexCd())
                .neuterYn(request.getNeuterYn())
//                .regions(request.getRegions())    // 추후에 추가
                .refinedSearchTerm(refinedSearchTerm)
                .embedding(embedding)
                .build();
    }

    public static AdoptionSearchResponse toResponse(AdoptionDocument document) {
        return AdoptionSearchResponse.builder()
                .adoptionId(document.getAdoptionId())   // 실제 반환용
                .specialMark(document.getSpecialMark()) // 테스트 확인용
                .upKindCd(document.getUpKindCd())   // 테스트 확인용
                .neuterYn(document.getNeuterYn())   // 테스트 확인용
                .sexCd(document.getSexCd()) // 테스트 확인용
                .build();
    }
}
