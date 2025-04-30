package kr.co.pawong.pwbe.adoption.presentation.controller;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionEsService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import kr.co.pawong.pwbe.adoption.presentation.port.ApiRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionUpdateController {

    private final ApiRequestService apiRequestService;
    private final AdoptionQueryService adoptionQueryService;
    private final AdoptionUpdateService adoptionUpdateService;
    private final AdoptionEsService adoptionEsService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveAdoptions() {
        apiRequestService.fetchAndSaveAdoptions();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/save-es")
    public ResponseEntity<Void> saveAdoptionsEs() {
        List<Adoption> adoptions = adoptionQueryService.getAllAdoptions();
        adoptionEsService.saveAdoptionToEs(adoptions);
        return ResponseEntity.ok().build();
    }

    @PostMapping("ai-preprocessing")
    public ResponseEntity<Void> aiProcessAdoptions() {
        adoptionUpdateService.aiProcessAdoptions();
        return ResponseEntity.ok().build();
    }

}
