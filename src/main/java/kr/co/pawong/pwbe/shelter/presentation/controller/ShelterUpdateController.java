package kr.co.pawong.pwbe.shelter.presentation.controller;

import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import kr.co.pawong.pwbe.shelter.application.service.ApiShelterService;
import kr.co.pawong.pwbe.shelter.presentation.controller.dto.ShelterInfoDto;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterQueryService;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shelters")
@RequiredArgsConstructor
public class ShelterUpdateController {

    private final ApiShelterService apiShelterService;
    private final ShelterUpdateService shelterUpdateService;
    private final ShelterQueryService  shelterQueryService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveShelters() {
        List<ShelterCreate> shelterCreates = apiShelterService.saveShelters();
        shelterUpdateService.saveShelters(shelterCreates);

        log.info("총 {}개의 동물보호센터가 성공적으로 저장되었습니다.", shelterCreates.size());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info/{care_Reg_No}")
    public void shelterInfoDto(@PathVariable("care_Reg_No") String careRegNo) {
        ShelterInfoDto shelterInfoDto = shelterQueryService.shelterInfo(careRegNo);

        log.info("보호소 번호 : {}", shelterInfoDto.getCareRegNo());
        log.info("도.시 : {}", shelterInfoDto.getCity());
        log.info("시군구 : {}", shelterInfoDto.getDistrict());
    }
}
