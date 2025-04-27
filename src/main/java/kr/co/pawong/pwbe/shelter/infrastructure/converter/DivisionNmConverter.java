package kr.co.pawong.pwbe.shelter.infrastructure.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.co.pawong.pwbe.shelter.enums.DivisionNm;

@Converter(autoApply = true)
public class DivisionNmConverter implements AttributeConverter<DivisionNm, String> {

    @Override
    public String convertToDatabaseColumn(DivisionNm attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getName(); // "동물병원" 이런 걸 DB에 저장
    }

    @Override
    public DivisionNm convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (DivisionNm divisionNm : DivisionNm.values()) {
            if (divisionNm.getName().equals(dbData)) {
                return divisionNm;
            }
        }
        throw new IllegalArgumentException("알 수 없는 값: " + dbData);
    }
}