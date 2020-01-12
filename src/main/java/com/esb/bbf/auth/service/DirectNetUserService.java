package com.esb.bbf.auth.service;

import com.esb.bbf.auth.user.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 直接拿透传的用户信息
 */
@Service("directNetUserService")
public class DirectNetUserService implements NetUserService {
    /**
     * 解析Edge网关直接将验证后的userId透传过来。
     * 这里的userId名字只是假设。
     *
     * @param request httprequest
     * @return UserInfo
     */
    @Override
    public UserInfo getUser(final HttpServletRequest request) throws Exception {
        //后面token可能是别的关键字
        String userId = request.getHeader("userId");
        if (StringUtils.isEmpty(userId)) {
            userId = (String) request.getAttribute("userId");
        }
        if (!StringUtils.isEmpty(userId)) {
            return UserInfo.builder().userId(userId).build();
        }
        return null;
    }
}
