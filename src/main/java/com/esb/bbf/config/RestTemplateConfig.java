package com.esb.bbf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

@Configuration
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "resttemplate.connection")
public class RestTemplateConfig {
    @NotNull
    private Integer connectTimeout;
    //如果时间超过hystrix的时间，以hystrix为准
    @NotNull
    private Integer readTimeout;
    @NotNull
    private Integer writeTimeout;
    @Bean(name = "lbRestTemplate")
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        factory.setWriteTimeout(writeTimeout);
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }

    @Bean(name = "directRestTemplate")
    public RestTemplate directRestTemplate() {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        factory.setWriteTimeout(writeTimeout);
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }
}
