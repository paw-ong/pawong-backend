package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import org.springframework.data.domain.Pageable;


public interface AdoptionQueryService {
    SliceAdoptionSearchResponses fetchSlicedAdoptions(Pageable pageable);
}