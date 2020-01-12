package com.esb.bbf.config;

import org.springframework.cloud.sleuth.zipkin2.ZipkinProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipkinConfig {
    @Bean
    public ZipkinProperties config(){
        return new ZipkinProperties();
    }
}
