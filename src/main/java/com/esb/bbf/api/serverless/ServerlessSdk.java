package com.esb.bbf.api.serverless;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.annotation.CatTransaction;
import com.esb.bbf.api.serverless.date.DateSdk;
import com.esb.bbf.api.serverless.http.HttpServerless;
import com.esb.bbf.api.serverless.json.Json;
import com.esb.bbf.auth.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ServerlessSdk {
    @Autowired
    private HttpServerless httpServerless;

    private Json json = new Json();
    private DateSdk date = new DateSdk();

    public ServerlessSdk() {
    }

    public Map loadServerlessSdk() {
        Map map = new HashMap();
        map.put("log", log);
        map.put("rpc", httpServerless);
        map.put("http", httpServerless);
        map.put("json", json);
        map.put("date", date);
        return map;
    }
    @CatTransaction(type = "method")
    public Map initHttpParaSdk(Map<String, String> httpHeader,
                               MultiValueMap<String, String> httpHeaderM,
                               Map<String, String> httpUriPara,
                               Map<String, String> httpPara,
                               MultiValueMap<String, String> httpParaM,
                               String apiId,
                               HttpServletRequest request) {
        Map map = new HashMap();
        {
            Object httpRequestBodyPara = readHttpRequestBody(request, httpHeader, apiId);
            map.put("httpHeader", httpHeader);
            map.put("httpHeaderM", httpHeaderM);
            map.put("httpUriPara", httpUriPara);
            map.put("httpPara", httpPara);
            map.put("httpParaM", httpParaM);
            map.put("apiId", apiId);
            map.put("httpBody", httpRequestBodyPara);
            //TODO 待添加登录user信息
            map.put("userInfo", LoginContext.user());
        }
        return map;
    }

    private Object readHttpRequestBody(HttpServletRequest request, Map<String, String> headers, String apiId) {

        if (headers == null) {
            return null;
        }
        if (headers.get("content-type") == null) {
            return null;
        }
        String contentType = headers.get("content-type");
        if (contentType.contains("json") || contentType.contains("JSON")) {
            try {
                String body = IOUtils.toString(request.getInputStream(), "utf-8");
                if (!StringUtils.isEmpty(body)) {
                    log.debug("appId={},api request Body={}", apiId, body);
                    try {
                        return JSON.parseObject(body);
                    }catch (Exception e){
                        //说明是数组json
                        return JSON.parseArray(body);
                    }
                } else {
                    log.debug("appId={},api request Body is empty", apiId);
                }
            } catch (Exception e) {
                log.error("appId={},api读取HttpServletRequest request inputstream异常={}",
                        apiId, e);
            }
        }
        return null;
    }
}
