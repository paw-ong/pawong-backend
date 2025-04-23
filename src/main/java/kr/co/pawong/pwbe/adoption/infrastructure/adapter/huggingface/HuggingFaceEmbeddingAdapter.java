package kr.co.pawong.pwbe.adoption.infrastructure.adapter.huggingface;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;

public class HuggingFaceEmbeddingAdapter implements EmbeddingProcessorPort {
    @Override
    public float[] embed(String completion) {
        return new float[0];
    }
}
