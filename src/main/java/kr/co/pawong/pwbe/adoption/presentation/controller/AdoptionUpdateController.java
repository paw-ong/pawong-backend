package kr.co.pawong.pwbe.adoption.presentation.controller;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.ApiRequestServiceImpl;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionEsService;
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

    private final ApiRequestServiceImpl apiRequestServiceImpl;
    private final AdoptionEsService adoptionEsService;
    private final AdoptionUpdateService adoptionUpdateService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveAdoptions() {
        apiRequestServiceImpl.fetchAndSaveAdoptions();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/saveEs")
    public ResponseEntity<Void> saveAdoptionsEs() {
        List<Adoption> adoptions = adoptionEsService.getAllAdoptions();
        adoptionEsService.saveAdoptionToEs(adoptions);
        return ResponseEntity.ok().build();
    }

}
