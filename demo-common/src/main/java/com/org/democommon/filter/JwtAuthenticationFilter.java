package com.org.democommon.filter;

import com.org.democommon.context.UserContext;
import com.org.democommon.security.LoginUser;
import com.org.democommon.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//==============================================================================

        String uri = request.getRequestURI();

        // 放行 Knife4j 接口文档
        if (uri.contains("doc.html") || uri.contains("webjars") ||
                uri.contains("v3/api-docs") || uri.contains("swagger-resources")) {
            filterChain.doFilter(request, response);
            return;
        }

//==============================================================================
        String header = request.getHeader("Authorization");

        log.debug("收到请求头Authorization: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);

            LoginUser loginUser = JwtUtil.parseToken(token); //尝试解析

            log.info("Token解析成功，用户: {}, 角色: {}", loginUser.getUsername(), loginUser.getRole());

            UserContext.setUser(loginUser); //在全线程保存账号信息

            log.debug("线程上下文已设置，当前认证用户: {}", UserContext.getUser().getUsername());

            UsernamePasswordAuthenticationToken authentication = //把用户信息交给security
                    new UsernamePasswordAuthenticationToken(
                            loginUser,
                            null,
                            loginUser.getAuthorities()  // 角色权限
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("Security上下文已设置，当前认证用户: {}", authentication.getName());

        } catch (Exception e) {   //异常就回滚事务
            log.error("Token认证异常：", e);

            UserContext.removeUser();

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response); //放行，交给security判断权限

        UserContext.removeUser();  //最终清除，保证线程安全
        SecurityContextHolder.clearContext();

        log.debug("请求正常完成，回收上下文成功");
    }
}
