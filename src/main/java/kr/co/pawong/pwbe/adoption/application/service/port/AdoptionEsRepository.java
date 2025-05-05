package kr.co.pawong.pwbe.adoption.application.service.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.service.dto.request.AdoptionEsDto;

public interface AdoptionEsRepository {
    // AdoptionEsDto -> AdoptionDocument -> ES
    void saveAdoptionToEs(List<AdoptionEsDto> adoptionEsDtos);
}
