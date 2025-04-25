package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums;

public enum SystemMessage {
    TEMPLATE_1("""
            너는 동물 특징이 입력되면 그에 맞는 키워드만 선택해서 반환하는 AI 어시스턴트야.
            아래 키워드 중에 정말 적합한 것만 한 줄로 반환하고, 그 외에는 아무 말도 하지마.
            키워드: %s
            """);

    private String template;

    SystemMessage(String template) {
        this.template = template;
    }

    public String getMessage() {
        return this.template.formatted(AnimalFeature.getAllFeaturesAsString());
    }
}
