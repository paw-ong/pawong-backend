package kr.co.pawong.pwbe.global.error.errorcode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {
    /**
     * 400 BAD_REQUEST
     */
    REQUEST_ERROR(BAD_REQUEST, "입력값이 잘못되었습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    TOKEN_INVALIDATE(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    USERNAME_NOT_FOUND(HttpStatus.UNAUTHORIZED, "username 정보로 유저를 찾을 수 없습니다."),

    /**
     * 404 NOT_FOUND
     */
    USER_NOT_FOUND(NOT_FOUND, "유저가 존재하지 않습니다."),
    ADOPTION_NOT_FOUND(NOT_FOUND, "유기동물 정보가 없습니다."),


    /**
     * 500 SERVER_ERROR
     */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버와의 연결에 실패하였습니다."),
    DATABASE_ERROR(INTERNAL_SERVER_ERROR, "데이터베이스 연결에 실패하였습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
