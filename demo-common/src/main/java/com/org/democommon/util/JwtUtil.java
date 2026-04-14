package com.org.democommon.util;


import com.org.democommon.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

public class JwtUtil {

    //过期时间 ： 7天(用毫秒为单位)
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    //生成密钥(复杂字符串)
    private static final String SECRET_KEY = "xiang_hui_dao_guo_qu_shi_zhe_ba_gu_shi_ji_xu_zhi_shao_bu_zai_rang_ni_li_wo_er_qu";

    //生成SecretKey对象，供签名和验证使用
    private static final SecretKey SECRET_KEY_OBJ = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    /**
     * 生成Token
     *
     * @param userName 用户名
     * @param role     用户角色
     * @return 生成的Token字符串
     */

    public static String createToken(String userName, Integer role, Long id) {
        return Jwts.builder()
                .setSubject(userName) // 设置用户名为主题
                .claim("role", role) // 添加用户角色作为自定义声明
                .claim("id", id)  // 添加用户id作为自定义声明
                .signWith(SECRET_KEY_OBJ) // 使用生成密钥进行签名，自动选择算法
                .setExpiration(new java.util.Date(System.currentTimeMillis() + EXPIRE_TIME)) // 设置过期时间
                .compact(); // 生成Token字符串
    }

    /**
     * 解析Token为username，id，role（新版API）
     */
    public static LoginUser parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY_OBJ) // 用密钥解析
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String userName = claims.getSubject();
        Long id = ((Number) claims.get("id")).longValue();
        Integer role = (Integer) claims.get("role");

        LoginUser loginUser = new LoginUser();

        loginUser.setUsername(userName);
        loginUser.setId(id);
        loginUser.setRole(role);

        return loginUser;
    }

}
