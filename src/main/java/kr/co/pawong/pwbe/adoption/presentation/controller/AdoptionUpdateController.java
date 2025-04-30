package kr.co.pawong.pwbe.adoption.presentation.controller;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.application.service.ApiRequestService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/adoption")
@RequiredArgsConstructor
public class AdoptionUpdateController {

    private final ApiRequestService apiRequestService;
    private final AdoptionUpdateService adoptionUpdateService;
    private final AdoptionQueryService adoptionQueryService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveAdoptions() {
        List<AdoptionCreate> adoptionCreates = apiRequestService.saveAdoptions();
        adoptionUpdateService.saveAdoptions(adoptionCreates);

        log.info("총 {}개의 입양동물 데이터가 성공적으로 저장되었습니다.", adoptionCreates.size());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info/{id}")
    public void carRegNoinfo(@PathVariable("id")Long id){
        String carRegNO = adoptionQueryService.findCarRegNoByAdoptionId(id);
        log.info("carRegNO: {}", carRegNO);
    }
}
