package kr.co.pawong.pwbe.adoption.presentation.controller;

import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/adoption")
@RestController
@RequiredArgsConstructor
public class AdoptionSearchController {

    private final AdoptionSearchService adoptionSearchService;

    // 일단은 검색 결과부터 확인, responseentity<> 변환은 추후에 추가하자
    @GetMapping("/search/document")
    public AdoptionSearchResponses search(@ModelAttribute AdoptionSearchRequest request) {
        return adoptionSearchService.search(request);
    }

}
