package com.esb.bbf.auth;

import com.esb.bbf.auth.user.UserInfo;

/**
 * 登录信息的上下文
 */
public class LoginContext {
    private static InheritableThreadLocal<UserInfo> tokenPool = new InheritableThreadLocal<>();

    /**
     * UserInfo
     */
    private LoginContext() {
    }

    /**
     * user current user
     *
     * @return return
     */
    public static UserInfo user() {
        return tokenPool.get();
    }

    /**
     * userId
     *
     * @return userId
     */
    public static String userId() {
        if (tokenPool.get() != null) {
            return tokenPool.get().getUserId();
        }
        return null;
    }
    /**
     * userName
     *
     * @return userName
     */
    public static String userName() {
        if (tokenPool.get() != null) {
            return tokenPool.get().getUserName();
        }
        return null;
    }
    /**
     * set user
     *
     * @param userVO userVO
     */
    public static void set(final UserInfo userVO) {
        tokenPool.set(userVO);
    }

    /**
     * remove user
     */
    public static void remove() {
        if (user() != null) {
            tokenPool.remove();
        }
    }
}
