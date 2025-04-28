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

    @Field(type = FieldType.Keyword, name = "sexCd")
    private SexCd sexCd; // 성별

    @Field(type = FieldType.Keyword, name = "neuterYn")
    private NeuterYn neuterYn; // 중성화여부(타입)

    @Field(type = FieldType.Keyword, name = "city")
    private String city; // 시도

    @Field(type = FieldType.Keyword, name = "district")
    private String district; // 시군구

    @Setter
    @Field(type = FieldType.Text, name = "taggingField", analyzer = "korean")
    private String taggingField; // AI가 키워드로 정제한 동물 정보

    @Setter
    @Field(type = FieldType.Text, name = "searchField", analyzer = "korean")
    private String searchField; // AI가 정제한 동물 정보

    @Setter
    @Field(type = FieldType.Dense_Vector, dims = 1536, name = "embedding")
    private float[] embedding; // searchField 임베딩

    public static AdoptionDocument from(Adoption adoption) {
        return AdoptionDocument.builder()
                .adoptionId(adoption.getAdoptionId())
                .upKindCd(adoption.getUpKindCd())
                .sexCd(adoption.getSexCd())
                .neuterYn(adoption.getNeuterYn())
                .city("")
                .district("")
                .taggingField("")
                .searchField("")
                .embedding(null)
                .build();
    }
}
