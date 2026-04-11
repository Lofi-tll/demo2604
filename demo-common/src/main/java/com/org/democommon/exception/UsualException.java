package com.org.democommon.exception;

import com.org.democommon.enumeration.ErrorCode;
import lombok.Getter;

/**
 * 自定义异常内容类，包含错误码和错误信息。提供多个构造函数，支持直接传入错误码和信息，或者通过ErrorCode枚举类创建异常。
 * 还提供一个默认构造函数，返回一个通用的未知错误信息和默认错误码500。
 */

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
