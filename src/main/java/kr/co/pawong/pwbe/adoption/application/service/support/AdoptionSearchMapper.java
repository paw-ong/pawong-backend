package kr.co.pawong.pwbe.adoption.application.service.support;

import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.AdoptionSearchRequest;

public class AdoptionSearchMapper {

    public static AdoptionSearchCondition fromRequest(AdoptionSearchRequest request, String refinedSearchTerm, float[] embedding) {
        return AdoptionSearchCondition.builder()
                .upKindCds(request.getUpKindCds())
                .sexCd(request.getSexCd())
                .neuterYn(request.getNeuterYn())
                .regions(request.getRegions())
                .refinedSearchTerm(refinedSearchTerm)
                .embedding(embedding)
                .build();
    }
}
