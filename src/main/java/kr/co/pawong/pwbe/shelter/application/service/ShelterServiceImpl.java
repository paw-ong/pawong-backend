package kr.co.pawong.pwbe.shelter.application.service;

import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import kr.co.pawong.pwbe.shelter.application.domain.ShelterCreate;
import kr.co.pawong.pwbe.shelter.application.service.port.ShelterRepository;
import kr.co.pawong.pwbe.shelter.infrastructure.repository.entity.ShelterEntity;
import kr.co.pawong.pwbe.shelter.presentation.controller.port.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;

    @Override
    public void saveShelters(List<ShelterCreate> shelterCreates) {
        List<ShelterEntity> shelterEntities = shelterCreates.stream()
                .map(Shelter::from)  // ShelterEntity.from(ShelterCreate)로 바꿔야 할 수도 있음
                .map(Shelter::toEntity)  // 이 부분이 실제 Entity로 변환되어야 함
                .toList();

        shelterRepository.saveAll(shelterEntities);
    }
}
