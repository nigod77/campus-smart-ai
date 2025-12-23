package com.nijiahao.common.core.domain;

public class UserContext {

    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();

    /**
     * 设置用户id
     * @param userId
     */
    public static void setUserId(String userId) {
        USER_ID.set(userId);
    }

    /**
     * 获得用户id
     * @return
     */
    public static String getUserId() {
        return USER_ID.get();
    }

    /**
     * 清除
     */
    public static void clear() {
        USER_ID.remove();
    }
}
