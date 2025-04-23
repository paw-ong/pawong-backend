package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionSearchMapper;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.document.AdoptionDocument;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionSearchServiceImpl implements AdoptionSearchService {
    private final AdoptionSearchRepository adoptionSearchRepository;
    private final AdoptionAiService adoptionAiService;

    @Override
    public AdoptionSearchResponses search(AdoptionSearchRequest request) {
        String refinedSearchTerm = refineSearchTerm(request);
        AdoptionSearchCondition condition = AdoptionSearchMapper.fromRequest(request, refinedSearchTerm, embed(refinedSearchTerm));

        List<AdoptionDocument> adoptionDocuments = adoptionSearchRepository.searchSimilarAdoptions(condition);

        return new AdoptionSearchResponses(adoptionDocuments.stream()
                .map(adoptionDocument -> AdoptionSearchMapper.toResponse(adoptionDocument))
                .collect(Collectors.toList()));
    }

    // 위임, 정제된 검색어 문장
    private String refineSearchTerm(AdoptionSearchRequest request) {
        String term = request.getSearchTerm();
        return adoptionAiService.refineSearchCondition(term);
    }

    // 위임, 임베딩 값
    private float[] embed(String refinedSearchTerm) {
        return adoptionAiService.embed(refinedSearchTerm);
    }

}
