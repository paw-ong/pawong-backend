package kr.co.pawong.pwbe.adoption.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum UpKindNm {
    DOG("개"),
    CAT("고양이"),
    ETC("기타");

    private final String value;

    UpKindNm(String value) {
        this.value = value;
    }

    public static UpKindNm fromValue(String value) {
        for (UpKindNm kind : UpKindNm.values()) {
            if (kind.getValue().equals(value)) {
                return kind;
            }
        }
        log.warn("종류명 오류: {}", value);
        return null;
    }
}
