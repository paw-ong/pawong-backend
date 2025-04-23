package kr.co.pawong.pwbe.adoption.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum UpKindCd {
    DOG("417000"),
    CAT("422400"),
    ETC("429900");

    private final String value;

    UpKindCd(String value) {
        this.value = value;
    }

    public static UpKindCd fromValue(String value) {
        for (UpKindCd kind : UpKindCd.values()) {
            if (kind.getValue().equals(value)) {
                return kind;
            }
        }
        log.warn("종류코드 오류: {}", value);
        return null;
    }
}
