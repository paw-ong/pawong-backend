package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionEsRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionEsServiceImpl implements AdoptionEsService {
    private final AdoptionEsRepository adoptionEsRepository;

    @Override
    public List<Adoption> getAllAdoptions() {
        return adoptionEsRepository.convertToAdoptions();
    }

    @Override
    public void saveAdoptionToEs(List<Adoption> adoptions) {
        adoptionEsRepository.saveAdoptionToEs(adoptions);
    }


}
