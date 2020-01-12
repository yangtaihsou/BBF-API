package com.esb.bbf.auth;

import javax.servlet.http.Cookie;

public class CookieUtils {


    /**
     * @param cookies cookies
     * @param key     key
     * @return return
     */
    public static String get(final Cookie[] cookies, final String key) {
        String result = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    result = cookie.getValue();
                }
            }
        }

        return result;
    }

    /**
     * constructor
     */
    private CookieUtils() {
    }
}
