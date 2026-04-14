package com.org.democommon.config;

import com.org.democommon.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // 【开启Spring Security安全控制】，让项目启用权限拦截
@EnableMethodSecurity  // 【开启方法级权限注解】，支持 @PreAuthorize 角色鉴权
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ==================== 1. 关闭CSRF防护 ====================
                // CSRF：浏览器跨站请求伪造防护
                // 前后端分离项目（JWT/Token认证）必须关闭，否则Post请求会被拦截403

            .csrf(AbstractHttpConfigurer::disable)

                // ==================== 2. 关闭Session ====================
                // 前后端分离使用JWT，无状态认证，不使用Session

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

                // ==================== 3. 接口权限规则 ====================

            .authorizeHttpRequests(auth -> auth
                    // 放行：登录、注册 → 不用登录就能访问
                    .requestMatchers("/users/login", "/users/register").permitAll()

                    // 放行：异常界面
                    .requestMatchers("/error").permitAll()

                    // 其他所有请求 → 必须登录！
                    .anyRequest().authenticated()
            )

                // ==================== 4. 添加JWT过滤器 ====================
                // 把我们自己写的 JWT 过滤器 加入到Security过滤链中
                // 位置：在 UsernamePasswordAuthenticationFilter 之前执行
                // 作用：先解析token → 登录用户 → 再走后面的权限校验

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

            // 关闭Spring Security自带的表单登录（前后端分离不需要）
            .formLogin(AbstractHttpConfigurer::disable)
            // 关闭HttpBasic弹窗登录（前后端分离不需要）
            .httpBasic(AbstractHttpConfigurer::disable);

        // 构建并返回安全配置
        return http.build();
    }
}