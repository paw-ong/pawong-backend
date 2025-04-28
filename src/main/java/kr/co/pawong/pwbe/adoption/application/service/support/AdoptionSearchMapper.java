package kr.co.pawong.pwbe.adoption.application.service.support;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponse;

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

    // ES에 adoptionId lookup을 위한 mapping
    public static AdoptionIdSearchResponse toIdSearchResponse(Adoption model) {
        return AdoptionIdSearchResponse.builder()
                .adoptionId(model.getAdoptionId())
                .build();
    }
}
