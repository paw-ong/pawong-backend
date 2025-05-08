package kr.co.pawong.pwbe.global.error.exception;

import kr.co.pawong.pwbe.global.error.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
