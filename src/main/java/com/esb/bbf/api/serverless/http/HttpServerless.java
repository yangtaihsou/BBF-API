package com.esb.bbf.api.serverless.http;

import com.dianping.cat.Cat;
import com.dianping.cat.annotation.CatTransaction;
import com.dianping.cat.message.Transaction;
import com.esb.bbf.api.serverless.ApiContext;
import com.esb.bbf.monitor.CatProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HttpServerless {
    @Autowired
    @Qualifier(value = "lbRestTemplate")
    private RestTemplate lbRestTemplate;
    @Autowired
    @Qualifier(value = "directRestTemplate")
    private RestTemplate directRestTemplate;

    /**
     * 用于服务发现的url负载均衡的请求
     * @param url
     * @param paraMap
     * @return
     */
    @CatTransaction(type = "method")
    @CatProfile(type = "method_api")
    public Object call(String url, Map<String, Object> paraMap) {
        if (paraMap.get("httpHeader") != null) {
            Map<String, String> header = (Map<String, String>) paraMap.get("httpHeader");
            String callId = header.get("callId");
            if (!StringUtils.isEmpty(callId)) {
                return this.callWithMonitor(url, paraMap, callId,lbRestTemplate);
            }
        }
        return this.callExe(url, paraMap,lbRestTemplate);
    }

    /**
     * 用于直接通过域名或者ip端口访问
     * @param url
     * @param paraMap
     * @return
     */
    @CatTransaction(type = "method")
    @CatProfile(type = "method_api")
    public Object callD(String url, Map<String, Object> paraMap) {
        if (paraMap.get("httpHeader") != null) {
            Map<String, String> header = (Map<String, String>) paraMap.get("httpHeader");
            String callId = header.get("callId");
            if (!StringUtils.isEmpty(callId)) {
                return this.callWithMonitor(url, paraMap, callId,directRestTemplate);
            }
        }
        return this.callExe(url, paraMap,directRestTemplate);
    }
    public Object callWithMonitor(String url, Map<String, Object> paraMap, String callId,
                                  RestTemplate restTemplate) {
        StringBuffer transNameBuffer = new StringBuffer("HttpServerless.http");
        //如果脚本里开启了异步线程，ApiContext的apiId将为空
        transNameBuffer.append(".")
                .append(ApiContext.apiId())
                .append(".")
                .append(callId);
        Transaction t = Cat.newTransaction("method_api", transNameBuffer.toString());
        t.setStatus(Transaction.SUCCESS);
        try {
            return this.callExe(url, paraMap,restTemplate);
        } catch (Throwable e) {
            t.setStatus(e);
            Cat.logError(e);
            throw e;
        } finally {
            t.complete();
        }
    }

    public Object callExe(String url, Map<String, Object> paraMap,
                          RestTemplate restTemplate) {
        String method = "POST";
        Map<String, Object> uriPara = null;
        Object httpPara = null;
        Map<String, Object> header = null;
        if (paraMap.get("httpMethod") != null) {
            method = (String) paraMap.get("httpMethod");
        }
        if (paraMap.get("httpUriPara") != null) {
            uriPara = (Map<String, Object>) paraMap.get("httpUriPara");
        }
        if (paraMap.get("httpPara") != null) {
            httpPara = paraMap.get("httpPara");
        }
        if (paraMap.get("httpHeader") != null) {
            header = (Map<String, Object>) paraMap.get("httpHeader");
        }
        return this.execute(url, method, uriPara, httpPara, header,restTemplate);
    }

    public Object execute(String url, String method,
                          Map<String, Object> uriVariables,
                          Object para,
                          Map<String, Object> header,
                          RestTemplate restTemplate) {
        try {
            if (uriVariables == null) {
                uriVariables = new HashMap<>();
            }
            if(restTemplate==null){
                restTemplate = lbRestTemplate;
            }
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    url, HttpMethod.resolve(method), this.wrapHttpEntity(para, header), Object.class, uriVariables);
            return responseEntity.getBody();

        } catch (Exception e) {
            log.error("执行http远程请求异常:{}", e);
            throw new RuntimeException(url + "请求服务异常:" + e.getMessage());
        }
    }

    private HttpEntity wrapHttpEntity(Object para,
                                      Map<String, Object> header) {
        HttpEntity requestEntity = new HttpEntity<>(null, null);
        if (header == null || header.size() == 0) {
            requestEntity = new HttpEntity<>(para, null);
            return requestEntity;
        }
        //restTemplate要求的httpHeader格式
        MultiValueMap<String, String> mHeaderMap = new LinkedMultiValueMap<>();
        header.forEach((k, v) -> {
            if (v != null) {
                //如果不是json请求，那么参数值，必须是string类型
                mHeaderMap.add(k, v.toString());
            }
        });
        //判断是否是form表单模式参数请求
        String contentType = (String) header.get("Content-Type");
        if (contentType.contains("application/json")){
            //默认走json格式参数请求
            requestEntity = new HttpEntity(para, mHeaderMap);
            return requestEntity;
        }
        if (contentType.contains("x-www-form-urlencoded")
                || contentType.contains("form-data")) {
            //instanceof 默认map
            Map<String, Object> paraMap = (Map) para;
            if (paraMap != null && paraMap.size() > 0) {
                MultiValueMap<String, String> formDataMap = new LinkedMultiValueMap<>();
                paraMap.forEach((k, v) -> {
                    if (v != null) {
                        //如果不是json请求，那么参数值，必须是string类型
                        formDataMap.add(k, v.toString());
                    }
                });
                requestEntity = new HttpEntity(formDataMap, mHeaderMap);
                return requestEntity;
            }
        }
        return requestEntity;
    }
}
