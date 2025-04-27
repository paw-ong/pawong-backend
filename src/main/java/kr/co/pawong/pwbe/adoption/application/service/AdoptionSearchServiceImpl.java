package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionSearchMapper;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponse;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionSearchServiceImpl implements AdoptionSearchService {
    private final AdoptionSearchRepository adoptionSearchRepository;
    private final AdoptionQueryRepository adoptionQueryRepository;
    private final AdoptionAiService adoptionAiService;

    // RDB에서 adoptionId로 최종 adoptions 반환
    @Override
    public AdoptionSearchResponses search(AdoptionSearchRequest request) {
        AdoptionIdSearchResponses adoptionIdSearchResponses = searchDocumentIds(request);
        List<Adoption> adoptions = adoptionIdSearchResponses.getAdoptionResponseList().stream()
                .map(AdoptionIdSearchResponse::getAdoptionId)
                .map(adoptionQueryRepository::findByIdOrThrow)
                .collect(Collectors.toList());

        return new AdoptionSearchResponses(adoptions);
    }

    // ES에서 검색 시 adoptionId를 반환
    @Override
    public AdoptionIdSearchResponses searchDocumentIds(AdoptionSearchRequest request) {
        String refinedSearchTerm = refineSearchTerm(request);
        AdoptionSearchCondition condition = AdoptionSearchMapper.fromRequest(request, refinedSearchTerm, embed(refinedSearchTerm));

        List<Adoption> adoptions = adoptionSearchRepository.searchSimilarAdoptions(condition);
        return new AdoptionIdSearchResponses(adoptions.stream()
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
