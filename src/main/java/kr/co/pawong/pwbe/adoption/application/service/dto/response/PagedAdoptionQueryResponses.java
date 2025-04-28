package kr.co.pawong.pwbe.adoption.application.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagedAdoptionQueryResponses {
    private int currentPage;
    private int pageSize;
    private long totalElements; // 전체 데이터 수
    private int totalPages;  // 전체 페이지 수
    private List<AdoptionCard> adoptionCards;
}
