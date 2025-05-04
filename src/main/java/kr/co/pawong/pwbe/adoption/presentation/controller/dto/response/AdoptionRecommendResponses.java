package kr.co.pawong.pwbe.adoption.presentation.controller.dto.response;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionRecommendResponses {
    private List<AdoptionCard> adoptionCards;
}
