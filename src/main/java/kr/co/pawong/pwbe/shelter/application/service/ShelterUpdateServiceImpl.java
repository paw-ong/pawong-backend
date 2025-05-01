package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import kr.co.pawong.pwbe.shelter.application.service.port.ShelterUpdateRepository;
import kr.co.pawong.pwbe.shelter.presentation.port.ShelterUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterUpdateServiceImpl implements ShelterUpdateService {

    private final ShelterUpdateRepository shelterUpdateRepository;

    // ShelterCreate -> Shelter -> Repo에 전달
    @Transactional
    @Override
    public void saveShelters(List<ShelterCreate> shelterCreates) {
        List<Shelter> shelters = shelterCreates.stream()
                .map(Shelter::from)
                .toList();

        shelterUpdateRepository.saveShelters(shelters);
    }
}
