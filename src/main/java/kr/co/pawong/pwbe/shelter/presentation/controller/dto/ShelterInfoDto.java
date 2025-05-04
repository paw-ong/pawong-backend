package kr.co.pawong.pwbe.shelter.presentation.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShelterInfoDto {
    private String careRegNo; //동물보호센터번호
    private String city; // 시도
    private String district; // 시군구

    public ShelterInfoDto(String careRegNo, String city, String district) {
        this.careRegNo = careRegNo;
        this.city = city;
        this.district = district;
    }
}
