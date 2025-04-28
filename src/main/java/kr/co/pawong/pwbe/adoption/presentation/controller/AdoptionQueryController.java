package kr.co.pawong.pwbe.adoption.presentation.controller;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionQueryController {

    private final AdoptionQueryService adoptionQueryService;

    @GetMapping("")
    public ResponseEntity<SliceAdoptionSearchResponses> getAdoptions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        SliceAdoptionSearchResponses response = adoptionQueryService.fetchSlicedAdoptions(page, size);
        return ResponseEntity.ok(response);
    }
}
