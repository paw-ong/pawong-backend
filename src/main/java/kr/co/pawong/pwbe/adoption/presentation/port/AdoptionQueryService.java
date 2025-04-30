package kr.co.pawong.pwbe.adoption.presentation.port;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import org.springframework.data.domain.Pageable;


public interface AdoptionQueryService {
    List<Adoption> getAllAdoptions();

    SliceAdoptionSearchResponses fetchSlicedAdoptions(Pageable pageable);
}