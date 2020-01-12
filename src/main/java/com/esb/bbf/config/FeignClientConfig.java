package com.esb.bbf.config;

import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.esb.bbf.api.client"})
public class FeignClientConfig {
    /**
     * Error decoder
     *
     * @return exception
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new HystrixErrorDecoder();
    }
}
