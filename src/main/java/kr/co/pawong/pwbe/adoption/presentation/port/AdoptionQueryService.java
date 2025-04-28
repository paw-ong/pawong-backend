package kr.co.pawong.pwbe.adoption.presentation.port;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.SliceAdoptionSearchResponses;


public interface AdoptionQueryService {
    SliceAdoptionSearchResponses fetchAllAdoptions(int page, int size);
}