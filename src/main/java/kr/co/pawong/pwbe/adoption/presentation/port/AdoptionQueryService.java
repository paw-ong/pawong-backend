package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.PagedAdoptionQueryResponses;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;
import org.springframework.data.domain.Pageable;


public interface AdoptionQueryService {
    PagedAdoptionQueryResponses fetchPagedAdoptions(Pageable pageable);
    SliceAdoptionSearchResponses fetchSlicedAdoptions(Pageable pageable);
}