package com.org.democommon.exception;

import com.org.democommon.enumeration.ErrorCode;
import lombok.Getter;

@Getter
public class UsualException extends RuntimeException {
    Integer code;
    public UsualException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public UsualException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
    public UsualException() {
        super("发生了一个未知错误");
        this.code = 500;
    }
}
