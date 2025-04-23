package kr.co.pawong.pwbe.adoption.presentation.controller;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.application.service.ApiRequestService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/adoption")
@RequiredArgsConstructor
public class AdoptionUpdateController {

    private final ApiRequestService apiRequestService;
    private final AdoptionUpdateService adoptionUpdateService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveAdoptions() {
        List<AdoptionCreate> adoptionCreates = apiRequestService.saveAdoptions();
        adoptionUpdateService.saveAdoptions(adoptionCreates);

        log.info("총 {}개의 입양동물 데이터가 성공적으로 저장되었습니다.", adoptionCreates.size());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
