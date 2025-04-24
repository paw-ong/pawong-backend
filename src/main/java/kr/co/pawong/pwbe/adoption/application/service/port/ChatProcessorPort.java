package kr.co.pawong.pwbe.adoption.application.service.port;

public interface ChatProcessorPort {

    /**
     * 동물 특징을 기반으로 문장을 반환합니다.
     * @param feature - 동물 특징 문자열
     * @return AI 모델 응답 문자열
     */
    String refineByFeature(String feature);
}
