package kr.co.pawong.pwbe.adoption.presentation.controller;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionRecommendResponse;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionQueryController {

    private final AdoptionQueryService adoptionQueryService;

    // slice 방식 (무한 스크롤)
    @GetMapping("")
    public ResponseEntity<SliceAdoptionSearchResponses> getSlicedAdoptions(
            @PageableDefault(page = 0, size = 20, sort = "noticeSdt", direction = Sort.Direction.DESC) Pageable pageable) {

        SliceAdoptionSearchResponses response = adoptionQueryService.fetchSlicedAdoptions(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<AdoptionRecommendResponse>> getSlicedAdoptions() {
        List<AdoptionRecommendResponse> adoptionRecommendResponses = adoptionQueryService.getRecommendAdoptions();
        return ResponseEntity.ok(adoptionRecommendResponses);
    }
}