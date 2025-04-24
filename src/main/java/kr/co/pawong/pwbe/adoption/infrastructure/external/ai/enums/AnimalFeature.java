package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum AnimalFeature {
    구조됨,
    아이와잘지냄,
    노인과잘지냄,
    다른개와잘지냄,
    고양이와잘지냄,
    사람경계함,
    사람을잘따름,
    장시간혼자있을수있음,
    가정환경에적합함,
    실내생활적응됨,
    음식가림없음,
    산책을좋아함,
    입양경험있음,
    적응이빠름,
    건강함,
    질병있음,
    온순함,
    활발함,
    조용함,
    사회성이좋음,
    겁이많음,
    잘짖음,
    사나움,
    입질함,
    활동량많음,
    아주어림,         // 3개월 이하, 젖먹이 수준
    나이가어림,       // 대략 0~1살, 강아지/아기 고양이
    나이가중간임,     // 2~6살 정도, 성견/성묘
    나이가많음,       // 7살 이상, 노령
    노령견또는노묘;   // 9살 이상, 어르신 반려로 적합

    public static String getAllFeaturesAsString() {
        return Arrays.stream(AnimalFeature.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
