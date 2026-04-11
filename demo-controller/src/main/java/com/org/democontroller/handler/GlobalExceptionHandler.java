package com.org.democontroller.handler;

import com.org.democommon.Result.R;
import com.org.democommon.exception.UsualException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**全局异常捕获器，demo的异常处理板块只收集和捕获错误码错误信息的内容
 * (请求路径
 * 请求方法（GET/POST/PUT…）
 * 异常类型
 * 异常堆栈简要信息
 * 时间戳)    未做处理，实际项目中可以根据需要进行增添
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
       处理valid参数校验异常
       这里只返回第一个错误信息，实际项目中可以根据需要返回所有错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String msg = fieldError.getDefaultMessage();
        return R.error(400,msg);
    }

    /**
        处理自定义异常
        demo版只定义一个通用的异常类，搭配错误信息的枚举类(防止硬编码)，统一处理所有业务错误
     * @param e
     * @return
     */

    @ExceptionHandler(UsualException.class)
    public R<Void> handleException(UsualException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    /**
        处理其他未捕获的异常
        兜底未被定义的信息，
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleRuntimeException(Exception e) {
        return R.error(500, e.getMessage());
    }

}
