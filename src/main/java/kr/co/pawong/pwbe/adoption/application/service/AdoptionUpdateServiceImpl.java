package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionIndexRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionUpdateServiceImpl implements AdoptionUpdateService {

    private final AdoptionUpdateRepository adoptionUpdateRepository;
    private final AdoptionIndexRepository adoptionIndexRepository;

    // AdoptionCreate -> Adoption -> Repo에 전달
    @Override
    public void saveAdoptions(List<AdoptionCreate> adoptionCreates) {
        List<Adoption> adoptions = adoptionCreates.stream()
                .map(Adoption::from)
                .toList();

        adoptionUpdateRepository.saveAdoptions(adoptions);
        adoptionIndexRepository.saveAdoptions(adoptions);
    }
}
