package kr.co.pawong.pwbe.shelter.application.service.port;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface ShelterUpdateRepository {
    // 동물보호센터 RDB저장
    void saveShelters(List<Shelter> shelter);

    List<String> findAllCareRegNos();

}
