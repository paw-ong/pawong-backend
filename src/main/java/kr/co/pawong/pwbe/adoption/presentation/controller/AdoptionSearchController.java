package kr.co.pawong.pwbe.adoption.presentation.controller;

import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/adoptions")
@RestController
@RequiredArgsConstructor
public class AdoptionSearchController {

    private final AdoptionSearchService adoptionSearchService;

    @GetMapping("/search")
    public ResponseEntity<AdoptionSearchResponses> search(@ModelAttribute AdoptionSearchRequest request) {
        AdoptionSearchResponses response = adoptionSearchService.search(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/document")
    public ResponseEntity<AdoptionIdSearchResponses> searchDocumentIds(@ModelAttribute AdoptionSearchRequest request) {
        AdoptionIdSearchResponses response = adoptionSearchService.searchDocumentIds(request);
        return ResponseEntity.ok(response);
    }
}