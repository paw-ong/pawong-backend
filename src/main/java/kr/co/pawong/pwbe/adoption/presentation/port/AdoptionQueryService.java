package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.PagedAdoptionQueryResponses;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;


public interface AdoptionQueryService {
    PagedAdoptionQueryResponses fetchPagedAdoptions(int page, int size);
    SliceAdoptionSearchResponses fetchSlicedAdoptions(int page, int size);
}