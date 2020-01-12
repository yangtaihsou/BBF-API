package com.esb.bbf.api.serverless.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpParaSdkContextTest {
    @Test
    public void test(){
        HttpParaSdkContext httpParaSdkContext = new HttpParaSdkContext();
      //  log.info("httpHeader={}",httpParaSdkContext.httpHeader);
        Map<String, String> map = new HashMap<>();
        map.putIfAbsent("name","yangtaishou");
        httpParaSdkContext.httpHeader(map);
        log.info(httpParaSdkContext.httpHeader.get("name"));
    }
}
