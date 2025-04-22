package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionSearchService;
import kr.co.pawong.pwbe.adoption.presentation.controller.dto.AdoptionSearchCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionSearchServiceImpl implements AdoptionSearchService {
    @Override
    public List<Adoption> search(AdoptionSearchCondition condition) {
        return List.of();
    }
}
