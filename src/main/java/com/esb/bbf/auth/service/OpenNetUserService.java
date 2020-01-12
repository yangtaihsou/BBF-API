package com.esb.bbf.auth.service;

import com.esb.bbf.auth.user.UserInfo;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 公网登录用户信息
 */
@Service("openNetUserService")
@Slf4j
public class OpenNetUserService implements NetUserService {
    @Resource
    private Cache<String, UserInfo> openNetMemCache;


    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    @SuppressWarnings("Duplicates")
    public UserInfo getUser(final HttpServletRequest request) throws Exception {
        UserInfo userInfo = UserInfo.builder().build();
        //这里首次验证登录(在缓存时间范围内的首次)是性能瓶颈。应该将验证放给edge网关。即走DirectNetUserService TODO
        log.debug("解析公网用户:{},登录耗时:{}", userInfo.getUserName());
        return userInfo;
    }


}
