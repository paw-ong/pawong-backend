package kr.co.pawong.pwbe.shelter.presentation.port;

import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import java.util.List;


public interface ShelterUpdateService {
    // ShelterCreate -> Shelter
    void saveShelters(List<ShelterCreate> shelterCreates);

}
