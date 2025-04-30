package kr.co.pawong.pwbe.adoption.application.service;

import java.util.List;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionQueryServiceImpl implements AdoptionQueryService {
    private final AdoptionQueryRepository adoptionQueryRepository;

    /**
     * DB에 저장된 모든 Adoption 도메인 객체를 조회하여 반환합니다.
     *
     * @return 전체 Adoption 리스트
     */
    @Override
    public List<Adoption> getAllAdoptions() {
        return adoptionQueryRepository.convertToAdoptions();
    }
}
