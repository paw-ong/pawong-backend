package kr.co.pawong.pwbe.adoption.application.service;

import org.springframework.stereotype.Service;

@Service
public class AdoptionAiServiceImpl implements AdoptionAiService {
    @Override
    public String refineSearchCondition(String searchTerm) {
        return "";
    }

    @Override
    public float[] embed(String completion) {
        return new float[0];
    }
}
