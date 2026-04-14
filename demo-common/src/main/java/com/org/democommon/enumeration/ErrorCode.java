package com.org.democommon.enumeration;


import lombok.Getter;

@Getter
public enum ErrorCode {
    // ==================== 通用 ====================
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未登录，请先登录"),
    FORBIDDEN(403, "无权限访问"),
    SYSTEM_ERROR(500, "系统异常，请稍后重试"),

    // ==================== 图书相关 ====================
    BOOK_NOT_EXIST(1001, "图书不存在"),
    BOOK_STOCK_NOT_ENOUGH(1002, "图书库存不足"),
    BOOK_ALREADY_BORROWED(1003, "图书已被借出，无法重复借阅"),
    BOOK_STATUS_ERROR(1004, "图书状态异常，无法操作"),

    // ==================== 借阅相关 ====================
    BORROW_RECORD_NOT_EXIST(2001, "借阅记录不存在"),
    ALREADY_RETURNED(2002, "图书已归还，无需重复操作"),
    BORROW_OVERDUE(2003, "图书已逾期，请先处理逾期"),
    MAX_BORROW_LIMIT(2004, "已达到最大可借阅数量"),
    ALREADY_BORROW(2005, "图书已借阅成功，无需重复操作"),

    // ==================== 用户相关 ====================
    USER_NOT_EXIST(3001, "用户不存在"),
    USER_STATUS_ERROR(3002, "用户状态异常，无法借阅"),
    PASSWORD_ERROR(3003, "用户名或密码错误"),
    USER_EXIST(3004, "用户已存在");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
