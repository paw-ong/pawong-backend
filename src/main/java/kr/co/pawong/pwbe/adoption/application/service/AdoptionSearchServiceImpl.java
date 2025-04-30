package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionSearchRepository;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionCardMapper;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionSearchMapper;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionCard;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.request.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponse;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionIdSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.response.AdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptionSearchServiceImpl implements AdoptionSearchService {
    private final AdoptionSearchRepository adoptionSearchRepository;
    private final AdoptionQueryRepository adoptionQueryRepository;
    private final AdoptionAiService adoptionAiService;

    // RDB에서 adoptionId로 최종 AdoptionSearchResponses 반환
    @Override
    public AdoptionSearchResponses search(AdoptionSearchRequest request) {
        // request에서 ES의 adoptionId 갖고오기
        AdoptionIdSearchResponses adoptionIdSearchResponses = searchDocumentIds(request);
        // adoptionId를 활용해 RDB 조회한 결과 Adoption 객체 반환
        List<Adoption> adoptions = adoptionIdSearchResponses.getAdoptionResponseList().stream()
                .map(AdoptionIdSearchResponse::getAdoptionId)
                .map(adoptionQueryRepository::findByIdOrThrow)
                .collect(Collectors.toList());
        // 최종적으로 검색 결과를 위한 매핑 리스트 반환
        List<AdoptionCard> adoptionCards = adoptions.stream()
                .map(AdoptionCardMapper::toAdoptionCard)
                .collect(Collectors.toList());

        return new AdoptionSearchResponses(adoptionCards);
    }

    // ES에서 검색 시 adoptionId를 반환
    @Override
    public AdoptionIdSearchResponses searchDocumentIds(AdoptionSearchRequest request) {
        String refinedSearchTerm = refineSearchTerm(request);
        AdoptionSearchCondition condition = AdoptionSearchMapper.fromRequest(request, refinedSearchTerm, embed(refinedSearchTerm));

        List<Adoption> adoptions = adoptionSearchRepository.searchSimilarAdoptions(condition);
        return new AdoptionIdSearchResponses(adoptions.stream()
                .map(AdoptionSearchMapper::toIdSearchResponse)
                .collect(Collectors.toList()));
    }

    // 위임, 정제된 검색어 문장
    private String refineSearchTerm(AdoptionSearchRequest request) {
        String term = request.getSearchTerm();
        return adoptionAiService.refineSpecialMark(term);
    }

    // 위임, 임베딩 값
    private float[] embed(String refinedSearchTerm) {
        return adoptionAiService.embed(refinedSearchTerm);
    }

}
