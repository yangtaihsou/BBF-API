package com.esb.bbf.config;

import com.esb.bbf.auth.user.UserInfo;
import com.esb.bbf.console.bussiness.ApiInfoQryInfo;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class LocalMemCacheConfig {
    private static final int CACHE_SIZE = 1000;
    private final int expireTime = 30;
    private final int openNetExpireTime = 60;

    @Bean
    public CacheManager cacheManager() {
        String caffeineSpec = "initialCapacity=50,maximumSize=500,refreshAfterWrite=10s";
        CaffeineSpec spec = CaffeineSpec.parse(caffeineSpec);
        Caffeine caffeine = Caffeine.from(spec);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAllowNullValues(false);
        CacheLoader<Object, Object> cacheLoader = key -> null;
        cacheManager.setCacheLoader(cacheLoader);
        return cacheManager;
    }

    /**
     * user guava cache
     * @return uava cache
     */
    @Bean
    public Cache<String, UserInfo> localNetMemCache() {
        return CacheBuilder.newBuilder().
                expireAfterWrite(expireTime, TimeUnit.SECONDS).
                maximumSize(CACHE_SIZE).build();
    }
    /**
     * user guava cache
     * @return guava cache
     */
    @Bean
    public Cache<String, UserInfo> openNetMemCache() {
        return CacheBuilder.newBuilder().
                expireAfterWrite(openNetExpireTime, TimeUnit.SECONDS).
                maximumSize(CACHE_SIZE).build();
    }

    @Bean
    public Cache<String, ApiInfoQryInfo> apiInfoQryInfoCache() {
        return CacheBuilder.newBuilder().
                expireAfterWrite(expireTime, TimeUnit.SECONDS).
                maximumSize(CACHE_SIZE).build();
    }
}
