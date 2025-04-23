package kr.co.pawong.pwbe.adoption.presentation.controller;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.application.service.ApiRequestService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/adoption")
@RequiredArgsConstructor
public class AdoptionUpdateController {

    private final ApiRequestService apiRequestService;
    private final AdoptionUpdateService adoptionUpdateService;

    @GetMapping("/save")
    public ResponseEntity<String> saveAdoption() {
        List<AdoptionCreate> adoptionCreates = apiRequestService.saveAdoption();
        adoptionUpdateService.saveAdoption(adoptionCreates);

        String message = String.format("총 %d개의 입양동물 데이터 중 %d개가 성공적으로 저장되었습니다",
                adoptionCreates.size(), adoptionCreates.size());
        log.info(message);

        return ResponseEntity.ok(message);
    }


}
