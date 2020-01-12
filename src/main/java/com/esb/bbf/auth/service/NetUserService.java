package com.esb.bbf.auth.service;


import com.esb.bbf.auth.user.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface NetUserService {
    /**
     * 从htttp参数里解析用户信息
     * @param request httprequest
     * @return UserInfo
     * @throws Exception Exception
     */
    UserInfo getUser(HttpServletRequest request) throws Exception;
}
