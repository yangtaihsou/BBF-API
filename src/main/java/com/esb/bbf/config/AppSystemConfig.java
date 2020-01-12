package com.esb.bbf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class AppSystemConfig {
    @Bean
    public Map<String, String> systemConfig() {
        Map<String, String> map = new HashMap<>();
        map.put("parentfront", "家长小前端");
        map.put("test", "测试");
        map.put("student", "学生网关");
        map.put("math", "数学网关");
        map.put("management", "management管理端");
        map.put("apoll", "apoll系统");
        return map;
    }
}
