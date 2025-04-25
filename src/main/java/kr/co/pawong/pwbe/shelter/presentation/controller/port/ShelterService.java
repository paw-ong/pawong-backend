package kr.co.pawong.pwbe.shelter.presentation.controller.port;

import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import java.util.List;


public interface ShelterService {
    // ShelterCreate -> Shelter
    void saveShelters(List<ShelterCreate> shelterCreates);

}
