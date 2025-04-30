package kr.co.pawong.pwbe.adoption.application.service.port;

public interface AdoptionQueryRepository {

    String findCareRegNoByAdoptionId(Long id);

}
