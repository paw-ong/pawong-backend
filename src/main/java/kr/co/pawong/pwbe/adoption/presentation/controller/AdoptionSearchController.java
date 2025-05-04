package kr.co.pawong.pwbe.adoption.presentation.controller;

import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionAutocompleteResponse;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // adoptionId 리스트만 반환하는 테스트용 API
    @GetMapping("/test/search")
    public ResponseEntity<AdoptionIdSearchResponses> searchDocumentIds(@ModelAttribute AdoptionSearchRequest request) {
        AdoptionIdSearchResponses response = adoptionSearchService.searchDocumentIds(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/autocomplete")
    public ResponseEntity<AdoptionAutocompleteResponse> searchAutoComplete(
            @RequestParam String keyword
    ) {
        List<String> autocomplete = adoptionSearchService.autocomplete(keyword);
        return ResponseEntity.ok(
                new AdoptionAutocompleteResponse(autocomplete)
        );
    }
}