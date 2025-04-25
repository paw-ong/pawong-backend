package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionSearchMapper;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionQueryResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponse;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
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
    private final AdoptionQueryService adoptionQueryService;

    // RDB에서 adoptionId로 최종 adoptions 반환
    @Override
    public AdoptionQueryResponses search(AdoptionSearchRequest request) {
        AdoptionSearchResponses adoptionSearchResponses = searchDocumentIds(request);
        List<Adoption> adoptions = adoptionSearchResponses.getAdoptionResponseList().stream()
                .map(AdoptionSearchResponse::getAdoptionId)
                .map(adoptionQueryService::fetchAdoptionById)
                .collect(Collectors.toList());

        return new AdoptionQueryResponses(adoptions);
    }

    // ES에서 검색 시 adoptionId를 반환
    @Override
    public AdoptionSearchResponses searchDocumentIds(AdoptionSearchRequest request) {
        String refinedSearchTerm = refineSearchTerm(request);
        AdoptionSearchCondition condition = AdoptionSearchMapper.fromRequest(request, refinedSearchTerm, embed(refinedSearchTerm));

        List<Adoption> adoptions = adoptionSearchRepository.searchSimilarAdoptions(condition);
        return new AdoptionSearchResponses(adoptions.stream()
                .map(AdoptionSearchMapper::toResponse)
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
