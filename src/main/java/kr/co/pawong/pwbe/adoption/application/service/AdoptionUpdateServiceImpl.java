package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptionUpdateServiceImpl implements AdoptionUpdateService {

    private final AdoptionUpdateRepository adoptionUpdateRepository;

    // AdoptionCreate -> Adoption -> Repo에 전달
    @Override
    public void saveAdoptions(List<AdoptionCreate> adoptionCreates) {
        List<Adoption> adoptions = adoptionCreates.stream()
                .map(Adoption::from)
                .toList();

        adoptionUpdateRepository.saveAdoptions(adoptions);

        log.info("{}개의 입양 정보가 변환 및 저장되었습니다.", adoptions.size());
    }
}
