package com.esb.bbf.api.service;

import com.esb.bbf.console.domain.HttpData;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.jvmScript.ScriptRun;
import com.esb.bbf.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Deprecated
public class ApiService {
    @Autowired
    @Qualifier(value = "lbRestTemplate")
    private RestTemplate lbRestTemplate;
   /* public void request(InterfaceInfo interfaceInfo,
                        Map<String, Object> paramap,
                        HttpEntity requestEntity,
                        Map scripParaMap) {
     *//*   List<CheckPoint> checkPointList = (List<CheckPoint>) GsonUtils.fromJson(interfaceInfo.getCheckPoint(), new TypeToken<ArrayList<CheckPoint>>() {
        }.getType());
        if (checkPointList != null && checkPointList.size() > 0) {
            for (CheckPoint checkPoint : checkPointList) {
                String checkValue = checkPoint.getCheckValue();
                String checkVar = checkPoint.getCheckVar();
                Object checkVarSetVar = paramap.get(checkVar);
            }
        }*//*
        if (StatusEnum.ReqType_Status.HTTP.status().equals(interfaceInfo.getType())) {
            this.httpRequest(interfaceInfo,
                    paramap,
                    requestEntity,
                    scripParaMap);
        } else if (StatusEnum.ReqType_Status.SCRIPT.status().equals(interfaceInfo.getType())) {
            this.scriptRun(interfaceInfo,
                    scripParaMap);
        }
    }*/

    public void scriptRun(JvmScriptInfo jvmScriptInfo,
                          Map scripParaMap) {
        Object result = ScriptRun.runScript(jvmScriptInfo.getScriptText(), "exe", scripParaMap, "groovy");
        log.info("name={},para={},result={}",
                jvmScriptInfo.getScriptId(),
                GsonUtils.toJson(scripParaMap),
                result);
    }

/*    public void httpRequest(InterfaceInfo interfaceInfo,
                            Map<String, Object> paramap,
                            HttpEntity requestEntity,
                            Map scripParaMap) {
        String key = interfaceInfo.getAlias();
        requestEntity = this.wrapHttpEntity(scripParaMap, key);
        Object response = this.exchange(interfaceInfo.getUrl(),
                HttpMethod.resolve(interfaceInfo.getMethod()), requestEntity, paramap);
        log.info("name={},response={}",
                interfaceInfo.getName(),
                GsonUtils.toJson(response));
        this.wrapResonpseData(scripParaMap, key, response);
    }*/


    public void wrapResonpseData(Map scripParaMap, String key, Object response) {
        Object httpDataObject = scripParaMap.get(key);
        if (httpDataObject == null) {
            scripParaMap.put(key,
                    HttpData.builder().response(response));
            return;
        }

        HttpData httpData = GsonUtils.fromJson(GsonUtils.toJson(httpDataObject), HttpData.class);
        httpData.setResponse(response);
        scripParaMap.put(key,
                httpData);
    }


    public HttpEntity wrapHttpEntity(Map scripParaMap, String key) {
        HttpEntity requestEntity = new HttpEntity<>(null, null);
        if (scripParaMap.get(key) == null) {
            return requestEntity;
        }
        HttpData httpData = GsonUtils.fromJson(GsonUtils.toJson(scripParaMap.get(key)), HttpData.class);
        Object body = null;
        Map<String, String> headers = null;
        if (httpData.getRequest() != null) {
            body = httpData.getRequest().getBody();
            headers = httpData.getRequest().getHeaders();
        }
        requestEntity = new HttpEntity<>(body, null);
        return requestEntity;
    }

    public Object exchange(String url, HttpMethod method,
                           HttpEntity<?> requestEntity, Map<String, ?> uriVariables) {
        ResponseEntity<Object> responseEntity = lbRestTemplate.exchange(
                url, method, requestEntity, Object.class, uriVariables);
        return responseEntity.getBody();


    }

    public HttpEntity wrapHttpEntity(Object para,
                                     Map<String, Object> header) {
        HttpEntity requestEntity = new HttpEntity<>(null, null);
        if (header == null || header.size() == 0) {
            requestEntity = new HttpEntity<>(para, null);
            return requestEntity;
        }
        //restTemplate要求的httpHeader格式
        MultiValueMap<String, Object> mHeaderMap = new LinkedMultiValueMap<>();
        header.forEach((k, v) -> {
            mHeaderMap.add(k, v);
        });
        if (para == null || !(para instanceof Map)) {
            //默认走json格式参数请求
            requestEntity = new HttpEntity(para, mHeaderMap);
            return requestEntity;
        }
        //判断是否是form表单模式参数请求
        String contentType = (String) header.get("Content-Type");
        if (contentType.contains("x-www-form-urlencoded")
                || contentType.contains("form-data")) {
            //instanceof 默认map
            Map<String, Object> paraMap = (Map) para;
            if (paraMap != null && paraMap.size() > 0) {
                MultiValueMap<String, Object> formDataMap = new LinkedMultiValueMap<>();
                paraMap.forEach((k, v) -> {
                    formDataMap.add(k, v);
                });
                requestEntity = new HttpEntity(formDataMap, mHeaderMap);
                return requestEntity;
            }
        }
        return requestEntity;
    }

    public Object execute(String url, String method,
                          Map<String, Object> uriVariables,
                          Object para,
                          Map<String, Object> header) {
        try {
            if (uriVariables == null) {
                uriVariables = new HashMap<>();
            }
            ResponseEntity<Object> responseEntity = lbRestTemplate.exchange(
                    url, HttpMethod.resolve(method), this.wrapHttpEntity(para, header), Object.class, uriVariables);
            return responseEntity.getBody();

        } catch (Exception e) {
            log.error("执行http远程请求异常:{}", e);
            throw new RuntimeException(e);
        }
    }

}
