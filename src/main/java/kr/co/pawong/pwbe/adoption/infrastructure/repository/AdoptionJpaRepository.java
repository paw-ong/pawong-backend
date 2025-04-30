package kr.co.pawong.pwbe.adoption.infrastructure.repository;

import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdoptionJpaRepository extends JpaRepository<AdoptionEntity, Long> {

    /**
     * adoptionId에 해당하는 AdoptionEntity의 embeddingDone 값을 부분 업데이트합니다.
     *
     * @param adoptionId 업데이트할 엔티티의 id
     * @param embeddingDone 임베딩 완료 여부
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AdoptionEntity a SET a.embeddingDone = :embeddingDone WHERE a.adoptionId IN :adoptionId")
    int updateEmbeddingDone(
            @Param("adoptionId") Long adoptionId,
            @Param("embeddingDone") boolean embeddingDone);

    /**
     * adoptionId에 해당하는 AdoptionEntity의 refinedSpecialMark, tagsField, aiProcessed만 부분 업데이트합니다.
     *
     * @param adoptionId  업데이트할 엔티티의 id
     * @param refinedSpecialMark 정제된 필드 값
     * @param tagsField   정제된 태그 필드 값
     * @param aiProcessed AI 정제 완료 여부
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AdoptionEntity a SET a.refinedSpecialMark = :refinedSpecialMark, a.tagsField = :tagsField, a.aiProcessed = :aiProcessed WHERE a.adoptionId = :adoptionId")
    void updateAiFields(
            @Param("adoptionId") Long adoptionId,
            @Param("refinedSpecialMark") String refinedSpecialMark,
            @Param("tagsField") String tagsField,
            @Param("aiProcessed") boolean aiProcessed);
}
