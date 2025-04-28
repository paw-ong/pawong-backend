package kr.co.pawong.pwbe.adoption.presentation.controller;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.PagedAdoptionQueryResponses;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionQueryController {

    private final AdoptionQueryService adoptionQueryService;

    // page 방식
    @GetMapping("")
    public ResponseEntity<PagedAdoptionQueryResponses> getPagedAdoptions(Pageable pageable) {
        PagedAdoptionQueryResponses response = adoptionQueryService.fetchPagedAdoptions(pageable);
        return ResponseEntity.ok(response);
    }

    // slice 방식 (무한 스크롤)
    @GetMapping("/slice")
    public ResponseEntity<SliceAdoptionSearchResponses> getSlicedAdoptions(Pageable pageable) {
        SliceAdoptionSearchResponses response = adoptionQueryService.fetchSlicedAdoptions(pageable);
        return ResponseEntity.ok(response);
    }
}