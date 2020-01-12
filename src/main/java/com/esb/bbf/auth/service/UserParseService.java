package com.esb.bbf.auth.service;

import com.esb.bbf.auth.LoginContext;
import com.esb.bbf.auth.user.UserInfo;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserParseService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NetUserService officeNetUserService;
    @Autowired
    private NetUserService openNetUserService;
    @Autowired
    private DirectNetUserService directNetUserService;
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 根据request，解析用户信息。
     */
    public void parseUser() {
        try {

            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            HttpServletResponse response =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            UserInfo userInfo = directNetUserService.getUser(request);
            //最理想的架构，走到这里就返回。
            if (userInfo != null) {
                LoginContext.set(userInfo);
                return;
            }
            String url = request.getRequestURI();
            if (url.startsWith("/api/rundApi/")) {
                userInfo = openNetUserService.getUser(request);
                if (userInfo == null) {
                    response.sendError(HttpStatus.SC_FORBIDDEN, "登录Authorization错误");
                    return;
                }
                LoginContext.set(userInfo);
            }
            if (url.startsWith("/console/")||url.startsWith("/api/office/rundApi/")) {
                userInfo = officeNetUserService.getUser(request);
                if (userInfo == null) {
                    response.sendError(HttpStatus.SC_FORBIDDEN, "登录token错误");
                    throw new RuntimeException("登录token错误");
                }
                LoginContext.set(userInfo);
            }

        } catch (Exception e) {
            logger.info("parse login error:{}", e);
            throw new RuntimeException(e);
        }
    }


}
