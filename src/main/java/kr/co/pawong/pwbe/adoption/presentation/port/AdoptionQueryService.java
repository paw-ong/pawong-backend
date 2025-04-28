package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.PagedAdoptionQueryResponses;


public interface AdoptionQueryService {

    PagedAdoptionQueryResponses fetchPagedAdoptions(int page, int size);
}