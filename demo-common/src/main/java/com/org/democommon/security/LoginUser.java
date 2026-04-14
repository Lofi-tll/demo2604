package com.org.democommon.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class LoginUser implements UserDetails {
    private String username;
    private Long id;
    private Integer role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 角色 1 = ROLE_admin
        // 角色 0 = ROLE_user
        String roleCode = (role == 1) ? "ROLE_admin" : "ROLE_user";

        // 返回权限 → Spring 只认这个！
        return Collections.singletonList(new SimpleGrantedAuthority(roleCode));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
