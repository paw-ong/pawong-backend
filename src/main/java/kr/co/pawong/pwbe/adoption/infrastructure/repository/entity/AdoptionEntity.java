package kr.co.pawong.pwbe.adoption.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.enums.ActiveState;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.ProcessState;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindNm;
import kr.co.pawong.pwbe.shelter.application.domain.Shelter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Adoption")
public class AdoptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptionId; // 입양id

    private String desertionNo; // 구조번호

    private LocalDate happenDt; // 접수일

    private String happenPlace; // 발견장소

    @Enumerated(EnumType.STRING)
    private UpKindNm upKindNm; // 축종명

    @Enumerated(EnumType.STRING)
    private UpKindCd upKindCd; // 축종코드

    private String kindNm; // 품종명

    private String kindCd; // 품종코드

    private String colorCd; // 색상

    private Integer age; // 나이 - Long

    private String weight; // 체중

    private String noticeNo; // 공고번호

    private LocalDate noticeSdt; // 공고시작일

    private LocalDate noticeEdt; // 공고종료일

    @Column(columnDefinition = "TEXT")
    private String popfile1; // 이미지1(텍스트)

    @Column(columnDefinition = "TEXT")
    private String popfile2; // 이미지2(텍스트)

    @Enumerated(EnumType.STRING)
    private ProcessState processState; // 상태

    @Enumerated(EnumType.STRING)
    private ActiveState activeState;

    @Enumerated(EnumType.STRING)
    private SexCd sexCd; // 성별

    @Enumerated(EnumType.STRING)
    private NeuterYn neuterYn; // 중성화여부(타입)

    private String specialMark; // 특징

    private LocalDateTime updTm; // 수정일

    private String searchField;

    private String tagsField;

    // Adoption -> AdoptionEntity
    public static AdoptionEntity from(Adoption adoption) {
        AdoptionEntity entity = new AdoptionEntity();

        entity.adoptionId = adoption.getAdoptionId();
        entity.desertionNo = adoption.getDesertionNo();
        entity.happenDt = adoption.getHappenDt();
        entity.happenPlace = adoption.getHappenPlace();
        entity.upKindNm = adoption.getUpKindNm();
        entity.upKindCd = adoption.getUpKindCd();
        entity.kindNm = adoption.getKindNm();
        entity.kindCd = adoption.getKindCd();
        entity.colorCd = adoption.getColorCd();
        entity.age = adoption.getAge();
        entity.weight = adoption.getWeight();
        entity.noticeNo = adoption.getNoticeNo();
        entity.noticeSdt = adoption.getNoticeSdt();
        entity.noticeEdt = adoption.getNoticeEdt();
        entity.popfile1 = adoption.getPopfile1();
        entity.popfile2 = adoption.getPopfile2();
        entity.processState = adoption.getProcessState();
        entity.activeState = adoption.getActiveState();
        entity.sexCd = adoption.getSexCd();
        entity.neuterYn = adoption.getNeuterYn();
        entity.specialMark = adoption.getSpecialMark();
        entity.updTm = adoption.getUpdTm();
        entity.searchField = adoption.getSearchField();
        entity.tagsField = adoption.getTagsField();

        return entity;
    }

    // AdoptionEntity -> Adoption
    public Adoption toModel() {
        Shelter shelter = null;
                //shelterEntity != null ? shelterEntity.toModel() : null;

        return Adoption.builder()
                .adoptionId(adoptionId)
                .desertionNo(desertionNo)
                .upKindCd(upKindCd)
                .activeState(activeState)
                .sexCd(sexCd)
                .neuterYn(neuterYn)
                .specialMark(specialMark)
                .updTm(updTm)
                .tagsField(tagsField)
                .searchField(searchField)
                .build();
    }
}
