package kr.co.pawong.pwbe.adoption.presentation.controller.dto.response;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionQueryResponses {
    private List<Adoption> adoptions;
}
