package kr.co.pawong.pwbe.adoption.application.service;

public interface AdoptionAiService {

    String refineSearchCondition(String searchTerm);

    float[] embed(String completion);
}
