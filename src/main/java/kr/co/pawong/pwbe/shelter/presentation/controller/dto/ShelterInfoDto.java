package kr.co.pawong.pwbe.shelter.presentation.controller.dto;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterInfoDto {
    private String careRegNo; //동물보호센터번호
    private String city; // 시도
    private String district; // 시군구

    public static ShelterInfoDto from(Shelter shelter) {
        return ShelterInfoDto.builder()
                .careRegNo(shelter.getCareRegNo())
                .city(shelter.getCity())
                .district(shelter.getDistrict())
                .build();
    }
}
