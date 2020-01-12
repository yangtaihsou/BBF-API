package com.esb.bbf.console.service;

import com.esb.bbf.TestConfig;
import com.esb.bbf.console.domain.HttpData;
import com.esb.bbf.jvmScript.ScriptRun;
import com.esb.bbf.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.ApiConfig.class)
@WebAppConfiguration
@Slf4j
public class APItest {
    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    private RestTemplate restTemplate;

    @Test
    public void test() throws IOException {
        String accountId = String.valueOf(422L);

        long startTime = System.currentTimeMillis();
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("studentId", accountId);
        paramap.put("button", true);

        HttpEntity requestEntity = new HttpEntity<>(null, null);
        Map scripParaMap = new HashMap();
        scripParaMap.put("accountId", accountId);
        scripParaMap.put("log", log);

        Object result = null;
        {
            String key = "welcomeInfo";
            requestEntity = this.wrapHttpEntity(scripParaMap, key);
            Object response1 = this.exchange(
                    "http://user/api/v1/student/{studentId}/equipment_test/welcome?button={button}",
                    HttpMethod.GET, requestEntity, paramap);
            Assert.assertNotNull(response1);
            log.info("response1={}", GsonUtils.toJson(response1));
            this.wrapResonpseData(scripParaMap, key, response1);
        }

        {
            paramap.put("accountId", accountId);
            String key = "studentInfo";
            requestEntity = this.wrapHttpEntity(scripParaMap, key);
            Object response2 = this.exchange(
                    "http://account/api/v1/account/student/{accountId}",
                    HttpMethod.GET, requestEntity, paramap);
            Assert.assertNotNull(response2);
            log.info("response2={}", GsonUtils.toJson(response2));
            this.wrapResonpseData(scripParaMap, key, response2);
        }

        {
            String scripteText2 = getScriptTex("Test2.py");
            result = ScriptRun.runScript(scripteText2, "exe", scripParaMap, "groovy");
            log.info("para2={},result2={}", GsonUtils.toJson(scripParaMap), result);
        }
        {
            String key = "classlevelInfo";
            requestEntity = this.wrapHttpEntity(scripParaMap, key);
            startTime = System.currentTimeMillis();
            Object response3 = this.exchange("http://hera/api/v1/hera/pt/classlevel",
                    HttpMethod.POST, requestEntity, paramap);
            log.info("responseEntity3耗费时间={}", System.currentTimeMillis() - startTime);
            Assert.assertNotNull(response3);
            log.info("response3={}", GsonUtils.toJson(response3));
            this.wrapResonpseData(scripParaMap, key, response3);
        }
        {
            String scripteText3 = getScriptTex("Test3.py");
            startTime = System.currentTimeMillis();
            result = ScriptRun.runScript(scripteText3, "exe", scripParaMap, "groovy");
            log.info("scripteText3耗费时间={}", System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            result = ScriptRun.runScript(scripteText3, "exe", scripParaMap, "groovy");
            log.info("scripteText3再次执行，耗费时间={}", System.currentTimeMillis() - startTime);
            log.info("para3={},result3={}", GsonUtils.toJson(scripParaMap), result);

        }
        {
            if (scripParaMap.get("exeNext") != null && (Boolean) scripParaMap.get("exeNext")) {
                String key = "orderCountInfo";
                requestEntity = this.wrapHttpEntity(scripParaMap, key);
                Object response4 = this.exchange(
                        "http://order/api/v1/count/normal_order?accountId={accountId}",
                        HttpMethod.GET, requestEntity, paramap);
                Assert.assertNotNull(response4);
                log.info("response4={}", GsonUtils.toJson(response4));
                this.wrapResonpseData(scripParaMap, key, response4);
            }

            String scripteText4 = getScriptTex("Test4.py");
            result = ScriptRun.runScript(scripteText4, "exe", scripParaMap, "groovy");
            log.info("para4={},result4={}", GsonUtils.toJson(scripParaMap), result);
        }
        if (scripParaMap.get("returnData") != null) {
            log.info("returnData={}", GsonUtils.toJson(scripParaMap.get("returnData")));
        }
/*        String scripteText21 = getScriptTex("ParaTest.py");
        result = ScriptRun.runScript(scripteText21, "exe", scripParaMap, "groovy");
        log.info("para={},result={}", GsonUtils.toJson(scripParaMap), result);*/
    }

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
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                url, method, requestEntity, Object.class, uriVariables);
        return responseEntity.getBody();

    }

    public String getScriptTex(String scriptName) throws IOException {
        File file = new File(this.getClass().getResource("/" + scriptName).getPath());
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();
        String txt = new String(filecontent, encoding);
        return txt;
    }
}
