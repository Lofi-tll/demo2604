package com.org.democommon.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashUtil{

    // 实例化一个BCryptPasswordEncoder对象，参数12表示加密强度，数值越大加密越安全但也越耗时
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    // 对原始字符串进行加密，返回加密后的字符串
    public static String encode(String raw) {
        return bCryptPasswordEncoder.encode(raw);
    }

    // 对登录密码进行校验，raw是用户输入的原始密码，encoded是数据库中存储的加密密码，返回true表示匹配成功，false表示匹配失败
    public static boolean matches(String raw, String encoded) {
        return bCryptPasswordEncoder.matches(raw, encoded);
    }

}