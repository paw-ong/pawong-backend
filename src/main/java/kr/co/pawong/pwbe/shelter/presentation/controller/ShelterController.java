package kr.co.pawong.pwbe.shelter.presentation.controller;

import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import kr.co.pawong.pwbe.shelter.application.service.ApiShelterService;
import kr.co.pawong.pwbe.shelter.presentation.controller.port.ShelterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ApiShelterService apiShelterService;
    private final ShelterService shelterService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveShelters() {
        List<ShelterCreate> shelterCreates = apiShelterService.saveShelters();
        shelterService.saveShelters(shelterCreates);

        log.info("총 {}개의 동물보호센터가 성공적으로 저장되었습니다.", shelterCreates.size());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
