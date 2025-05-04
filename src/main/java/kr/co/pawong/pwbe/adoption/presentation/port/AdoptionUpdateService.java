package kr.co.pawong.pwbe.adoption.presentation.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;

public interface AdoptionUpdateService {
    // AdoptionCreate -> Adoption
    void saveAdoptions(List<AdoptionCreate> adoptionCreates);

    void aiProcessAdoptions();

}
