package kr.co.pawong.pwbe.adoption.application.service.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

public interface AdoptionIndexRepository {
    // AdoptionEntity -> Adoption
    List<Adoption> convertToAdoptions();

    // Adoption -> AdoptionDocument -> ES
    void saveAdoptionToEs(List<Adoption> adoptions);

}
