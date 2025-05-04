package kr.co.pawong.pwbe.adoption.fake;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;

public class FakeEmbeddingAdapter implements EmbeddingProcessorPort {
    @Override
    public float[] embed(String completion) {
        if (completion.equals("fail"))
            throw new RuntimeException("fail embed");
        return new float[]{1.01f, 1.02f};
    }
}
