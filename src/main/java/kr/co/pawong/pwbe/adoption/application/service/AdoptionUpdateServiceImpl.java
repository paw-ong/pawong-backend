package kr.co.pawong.pwbe.adoption.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.application.domain.AdoptionCreate;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionUpdateRepository;
import kr.co.pawong.pwbe.adoption.enums.ActiveState;
import kr.co.pawong.pwbe.adoption.presentation.port.AdoptionUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptionUpdateServiceImpl implements AdoptionUpdateService {

    private final AdoptionUpdateRepository adoptionUpdateRepository;
    private final AdoptionQueryRepository adoptionQueryRepository;
    private final AdoptionAiService adoptionAiService;

    // AdoptionCreate -> Adoption -> Repo에 전달
    @Override
    public void saveAdoptions(List<AdoptionCreate> adoptionCreates) {
        List<Adoption> adoptions = adoptionCreates.stream()
                .map(Adoption::from)
                .toList();

        adoptionUpdateRepository.saveAdoptions(adoptions);

        log.info("{}개의 입양 정보가 변환 및 저장되었습니다.", adoptions.size());
    }

    /**
     * DB에서 Adoption 도메인 객체를 모두 조회하여
     * ACTIVE 상태만 AI 전처리(정제) 후
     * refinedSpecialMark, tagsField, aiProcessed가 변경된 경우만
     * 10개씩 정제하고 10개씩 업데이트하는 메서드
     */
    @Override
    public void aiProcessAdoptions() {
        List<Adoption> adoptions = adoptionQueryRepository.findAll();

        List<Adoption> toUpdate = new ArrayList<>();
        for (Adoption adoption : adoptions) {
            if (adoption.getActiveState() == ActiveState.ACTIVE && !adoption.isAiProcessed()) {
                log.info("AdoptionId = {}", adoption.getAdoptionId());
                String refinedSpecialMark = adoptionAiService.refineSearchCondition(adoption.getRefinedSpecialMark());
                List<String> tags = adoptionAiService.tag(adoption.getTagsField());
                String tagsField = String.join(" ", tags);

                boolean aiProcessed =
                        (refinedSpecialMark != null && !refinedSpecialMark.isBlank()) || (tagsField != null
                                && !tagsField.isBlank());

                if (!Objects.equals(adoption.getRefinedSpecialMark(), refinedSpecialMark)
                        || !Objects.equals(adoption.getTagsField(), tagsField)
                        || adoption.isAiProcessed() != aiProcessed) {


                    adoption.updateAiField(refinedSpecialMark, tagsField);
                    toUpdate.add(adoption);
                }
            }
            // 10개씩 저장
            if (toUpdate.size() == 10) {
                adoptionUpdateRepository.updateAiFields(toUpdate);
                toUpdate.clear();
            }
        }

        // 남은 데이터 저장
        if (!toUpdate.isEmpty()) {
            adoptionUpdateRepository.updateAiFields(toUpdate);
        }
    }


}
