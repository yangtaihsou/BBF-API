package com.esb.bbf.auth.service;

import com.esb.bbf.auth.user.UserInfo;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 办公网络登录用户信息
 */
@Service("officeNetUserService")
@Slf4j
public class OfficeNetUserService implements NetUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PORTAL = "BBF-API";
    private static final String HTTP_COOKIE_SSOTOKEN_LOCALNET = "ssoToken";
    @Resource
    private Cache<String, UserInfo> localNetMemCache;

    @Value("${hosts.sso}")
    private String ssoHost;

    @Value("${spring.profiles.active}")
    private String profile;
    @Resource
    private RestTemplate directRestTemplate;

    /**
     * 根据cookie解析域账户登录的用户名
     *
     * @param request httprequest
     * @return UserInfo
     * @throws Exception exception
     */
    @Override
    @SuppressWarnings("Duplicates")
    public UserInfo getUser(final HttpServletRequest request) throws Exception {
        //用户信息如果放给上层鉴权网关更好。即走DirectNetUserService TODO
        UserInfo userInfo = UserInfo.builder().build();
        return userInfo;
    }


}
