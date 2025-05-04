package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionCard;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.ShelterInfoPort;
import kr.co.pawong.pwbe.adoption.application.service.support.AdoptionCardMapper;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.pawong.pwbe.shelter.infrastructure.adapter.ShelterAdapter;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptionQueryServiceImpl implements AdoptionQueryService {
    private final AdoptionQueryRepository adoptionQueryRepository;

    /**
     * DB에 저장된 모든 Adoption 도메인 객체를 조회하여 반환합니다.
     *
     * @return 전체 Adoption 리스트
     */
    @Override
    public List<Adoption> getAllAdoptions() {
        return adoptionQueryRepository.findAll();
    }

    private final ShelterInfoPort shelterInfoPort;

    // infinite scroll을 위한 slice 방식
    @Override
    public SliceAdoptionSearchResponses fetchSlicedAdoptions(Pageable pageable) {
        Page<Adoption> adoptionPage = adoptionQueryRepository.findAllPaged(pageable);
        List<AdoptionCard> adoptionCards = mapToAdoptionCards(adoptionPage);
        boolean hasNext = adoptionPage.hasNext();
        return new SliceAdoptionSearchResponses(hasNext, adoptionCards);
    }

    private List<AdoptionCard> mapToAdoptionCards(Page<Adoption> adoptionPage) {
        return adoptionPage.getContent().stream()
                .map(AdoptionCardMapper::toAdoptionCard)
                .collect(Collectors.toList());
    }

    @Override
    public ShelterInfoDto findShelterInfoByAdoptionId(Long adoptionId) {
        // 1) AdoptionEntity에서 careRegNo 조회
        String careRegNo = adoptionQueryRepository.findCareRegNoByAdoptionId(adoptionId);
        // 2) ShelterAdapter 통해 실제 Shelter 컨텍스트에 질의
        return shelterInfoPort.getShelterInfo(careRegNo);
    }
}