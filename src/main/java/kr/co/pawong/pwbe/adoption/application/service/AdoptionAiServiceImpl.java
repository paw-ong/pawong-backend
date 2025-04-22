package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import kr.co.pawong.pwbe.adoption.application.service.port.PromptProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionAiServiceImpl implements AdoptionAiService {

    private final EmbeddingProcessorPort embeddingPort;
    private final PromptProcessorPort promptPort;

    @Override
    public String refineSearchCondition(String searchTerm) {
        return "";
    }

    @Override
    public float[] embed(String completion) {
        return new float[0];
    }

}
