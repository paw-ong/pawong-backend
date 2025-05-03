package kr.co.pawong.pwbe.adoption.application.service.dto.request;

import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionInfoDto {
    private String city;
    private String district;

    public static RegionInfoDto from(ShelterInfoDto shelterInfoDto) {
        return RegionInfoDto.builder()
                .city(shelterInfoDto.getCity())
                .district(shelterInfoDto.getDistrict())
                .build();
    }

}
