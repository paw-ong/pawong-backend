package kr.co.pawong.pwbe.adoption.application.service.support;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionCard;

public class AdoptionCardMapper {

    // 검색 결과 카드에 필요한 mapping
    public static AdoptionCard toAdoptionCard(Adoption adoption) {
        return AdoptionCard.builder()
                .adoptionId(adoption.getAdoptionId())
                .popfile1(adoption.getPopfile1())
                .kindNm(adoption.getKindNm())
                .sexCd(adoption.getSexCd())
                .age(adoption.getAge())
                .neuterYn(adoption.getNeuterYn())
                .build();
    }
}
