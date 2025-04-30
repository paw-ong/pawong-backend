package kr.co.pawong.pwbe.adoption.presentation.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

public interface AdoptionEsService {

    void saveAdoptionToEs(List<Adoption> adoptions);
}
