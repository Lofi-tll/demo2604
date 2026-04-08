package com.org.democontroller.handler;

import com.org.democommon.Result.R;
import com.org.democommon.exception.UsualException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsualException.class)
    public R<String> handleException(UsualException e) {
        // 这里可以根据不同的异常类型返回不同的错误信息
        return R.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public R<String> handleRuntimeException(Exception e) {
        return R.error(500, "发生了一个未知错误: " + e.getMessage());
    }
}
