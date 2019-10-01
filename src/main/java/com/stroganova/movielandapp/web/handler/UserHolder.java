package com.stroganova.movielandapp.web.handler;

import com.stroganova.movielandapp.entity.User;

public class UserHolder {

    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static User getLoggedUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void setLoggedUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }
    public static void clear(){
        USER_THREAD_LOCAL.remove();
    }

}
