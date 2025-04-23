package kr.co.pawong.pwbe.adoption.application.service.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

public interface AdoptionUpdateRepository {
    // 유기동물정보 RDB에 저장
    void saveAdoption(List<Adoption> adoptions);
}
