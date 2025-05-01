package kr.co.pawong.pwbe.adoption.application.service.support;

import java.util.Collections;
import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition.Region;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponse;

public class AdoptionSearchMapper {

    public static AdoptionSearchCondition fromRequest(AdoptionSearchRequest request, String refinedSearchTerm, float[] embedding) {
        return AdoptionSearchCondition.builder()
                .upKindCds(request.getUpKindCds())
                .sexCd(request.getSexCd())
                .neuterYn(request.getNeuterYn())
                .regions(toRegionList(request.getRegions()))
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

    /**
     * regions 문자열을 city/district 로 파싱한 리스트를 반환합니다.
     * - "서울특별시"        → city="서울특별시", district=null
     * - "인천광역시 부평구" → city="인천광역시", district="부평구"
     */
    public static List<Region> toRegionList(List<String> regions) {
        if (regions == null) {
            return Collections.emptyList();
        }
        return regions.stream()
            .map(AdoptionSearchMapper::parseRegion)
            .toList();
    }
    public static Region parseRegion(String cityAndDistrict) {
        String trimmed = cityAndDistrict.trim();
        int idx = trimmed.indexOf(' ');
        return (idx < 0)
            ? new Region(trimmed, null)
            : new Region(trimmed.substring(0, idx), trimmed.substring(idx + 1));
    }


}
