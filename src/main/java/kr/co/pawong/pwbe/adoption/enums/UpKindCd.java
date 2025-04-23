package kr.co.pawong.pwbe.adoption.enums;

import lombok.Getter;

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
        throw new IllegalArgumentException("종류코드 오류: " + value);
    }
}
