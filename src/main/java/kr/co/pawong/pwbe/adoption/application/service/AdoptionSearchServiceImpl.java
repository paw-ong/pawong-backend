package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.AdoptionSearchCondition;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionSearchMapper;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.AdoptionEsRepository;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.AdoptionSearchRequest;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionSearchServiceImpl implements AdoptionSearchService {
    private final AdoptionEsRepository adoptionEsRepository;
    private final AdoptionAiService adoptionAiService;

    public AdoptionSearchServiceImpl(AdoptionEsRepository adoptionEsRepository,
                                     AdoptionAiService adoptionAiService) {
        this.adoptionEsRepository = adoptionEsRepository;
        this.adoptionAiService = adoptionAiService;
    }

    /***
     * @param request
     * 주어진 검색 조건을 통해 필터링하고, 정제된 검색어 벡터를 검색에 활용
     * @return adoption list
     */
    @Override
    public List<Adoption> search(AdoptionSearchRequest request) {
        String refinedSearchTerm = refineSearchTerm(request);
        AdoptionSearchCondition condition = AdoptionSearchMapper.fromRequest(request, refinedSearchTerm, embed(refinedSearchTerm));
        return adoptionEsRepository.searchSimilarAdoptionIds(condition);
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
