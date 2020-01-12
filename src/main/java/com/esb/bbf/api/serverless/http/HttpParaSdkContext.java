package com.esb.bbf.api.serverless.http;

import com.esb.bbf.auth.LoginContext;
import com.esb.bbf.auth.user.UserInfo;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class HttpParaSdkContext {

    private InheritableThreadLocal<Map<String, String>> httpHeaderPool = new InheritableThreadLocal<>();
    private InheritableThreadLocal<MultiValueMap<String, String>> httpHeaderMPool = new InheritableThreadLocal<>();
    private InheritableThreadLocal<Map<String, String>> httpUriParaPool = new InheritableThreadLocal<>();
    private InheritableThreadLocal<Map<String, String>> httpParaPool = new InheritableThreadLocal<>();
    private InheritableThreadLocal<MultiValueMap<String, String>> httpParaMPool = new InheritableThreadLocal<>();
    private InheritableThreadLocal<String> apiIdPool = new InheritableThreadLocal<>();
    private InheritableThreadLocal<Object> httpBodyPool = new InheritableThreadLocal<>();


    public Map<String, String> httpHeader = httpHeaderPool.get();
    public MultiValueMap<String, String> httpHeaderM = httpHeaderMPool.get();
    public Map<String, String> httpUriPara = httpUriParaPool.get();
    public Map<String, String> httpPara = httpParaPool.get();
    public MultiValueMap<String, String> httpParaM = httpParaMPool.get();
    public String apiId = apiIdPool.get();
    public Object httpBody = httpBodyPool.get();
    public UserInfo userInfo = LoginContext.user();

    public void httpHeader(Map<String, String> map){
        this.httpHeaderPool.set(map);
    }
    public void httpHeaderM(MultiValueMap<String, String> map){
        this.httpHeaderMPool.set(map);
    }
    public void httpUriPara(Map<String, String> map){
        this.httpUriParaPool.set(map);
    }
    public void httpPara(Map<String, String> map){
        this.httpParaPool.set(map);
    }
    public void httpParaM(MultiValueMap<String, String> map){
        this.httpParaMPool.set(map);
    }
    public void apiId(String map){
        this.apiIdPool.set(map);
    }
    public void httpBody(Object object){
        this.httpBodyPool.set(object);
    }

}
