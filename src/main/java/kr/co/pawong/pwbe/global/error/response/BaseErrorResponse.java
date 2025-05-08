package kr.co.pawong.pwbe.global.error.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class BaseErrorResponse {
    private final String code;
    private final String message;
}
