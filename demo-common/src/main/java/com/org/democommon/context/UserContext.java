package com.org.democommon.context;

import com.org.democommon.security.LoginUser;

public class UserContext {

    private static final ThreadLocal<LoginUser> USER_HOLDER = new ThreadLocal<>();

    public static void setUser(LoginUser loginUser) {
        USER_HOLDER.set(loginUser);
    }

    public static LoginUser getUser(){
        return USER_HOLDER.get();
    }

    public static void removeUser(){
        USER_HOLDER.remove();
    }

}
