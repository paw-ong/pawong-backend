package kr.co.pawong.pwbe.adoption.application.service;

public interface AdoptionAiService {

    String refineSearchCondition(String prompt);

    String embed(String completion);
}
