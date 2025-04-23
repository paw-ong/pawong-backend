package kr.co.pawong.pwbe.adoption.infrastructure.repository.document;

import kr.co.pawong.pwbe.adoption.application.domain.Adoption;
import kr.co.pawong.pwbe.adoption.enums.NeuterYn;
import kr.co.pawong.pwbe.adoption.enums.SexCd;
import kr.co.pawong.pwbe.adoption.enums.UpKindCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "Adoption")
public class AdoptionDocument {
    @Id
    @Field(type = FieldType.Keyword, name = "adoptionId")
    private Long adoptionId; // 입양id

    @Field(type = FieldType.Keyword, name = "upKindCd")
    private UpKindCd upKindCd; // 축종코드

    @Field(type = FieldType.Text, name = "KindNm", analyzer = "korean")
    private String kindNm; // 품종명

    @Field(type = FieldType.Text, name = "colorCd", analyzer = "korean")
    private String colorCd; // 색상

    @Field(type = FieldType.Integer, name = "age")
    private Integer age; // 나이(년생) Long

    @Field(type = FieldType.Keyword, name = "sexCd")
    private SexCd sexCd; // 성별

    @Field(type = FieldType.Keyword, name = "neuterYn")
    private NeuterYn neuterYn; // 중성화여부(타입)

    @Field(type = FieldType.Text, name = "specialMark", analyzer = "korean")
    private String specialMark; // 특징

    @Setter
    @Field(type = FieldType.Text, analyzer = "korean")
    private String searchField; // AI가 정제한 동물 정보

    @Setter
    @Field(type = FieldType.Dense_Vector, dims = 1536)
    private float[] embedding; // searchField 임베딩

    public static AdoptionDocument fromEntity(Adoption adoption) {
        return AdoptionDocument.builder()
                .adoptionId(adoption.getAdoptionId())
                .upKindCd(adoption.getUpKindCd())
                .kindNm(adoption.getKindNm())
                .colorCd(adoption.getColorCd())
                .age(adoption.getAge())
                .sexCd(adoption.getSexCd())
                .neuterYn(adoption.getNeuterYn())
                .specialMark(adoption.getSpecialMark())
                .searchField("")
                .embedding(null)
                .build();
    }
}
