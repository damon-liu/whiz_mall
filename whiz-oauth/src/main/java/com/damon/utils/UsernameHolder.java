package com.damon.utils;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 15:22
 */
public class UsernameHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();

    public static String getContext() {
        return contextHolder.get();
    }

    public static void setContext(String username) {
        contextHolder.set(username);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
