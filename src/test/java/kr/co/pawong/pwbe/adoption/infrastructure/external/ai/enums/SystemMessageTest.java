package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemMessageTest {

    @Test
    void AI_시스템_메시지_확인() {
        System.out.println(SystemMessage.TEMPLATE_1.getMessage());
    }
}