package kr.co.pawong.pwbe.adoption.application.service.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

public interface AdoptionIndexRepository {
    // 유기동물정보 ES에 저장
    void saveBulk(List<Adoption> adoptions);
}
