package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionCard;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.PagedAdoptionQueryResponses;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionCardMapper;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptionQueryServiceImpl implements AdoptionQueryService {

    private final AdoptionQueryRepository adoptionQueryRepository;

    // page 방식
    @Override
    public PagedAdoptionQueryResponses fetchPagedAdoptions(int page, int size) {
        Page<Adoption> adoptionPage = adoptionQueryRepository.findAllPaged(page, size);
        List<AdoptionCard> adoptionCards = mapToAdoptionCards(adoptionPage);
        return new PagedAdoptionQueryResponses(
                page,
                size,
                adoptionPage.getTotalElements(),
                adoptionPage.getTotalPages(),
                adoptionCards
        );
    }

    // infinite scroll을 위한 slice 방식
    @Override
    public SliceAdoptionSearchResponses fetchSlicedAdoptions(int page, int size) {
        Page<Adoption> adoptionPage = adoptionQueryRepository.findAllPaged(page, size);
        List<AdoptionCard> adoptionCards = mapToAdoptionCards(adoptionPage);
        boolean hasNext = adoptionPage.hasNext();
        return new SliceAdoptionSearchResponses(hasNext, adoptionCards);
    }

    private List<AdoptionCard> mapToAdoptionCards(Page<Adoption> adoptionPage) {
        return adoptionPage.getContent().stream()
                .map(AdoptionCardMapper::toAdoptionCard)
                .collect(Collectors.toList());
    }
}