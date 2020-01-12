package com.esb.bbf.config;

import com.esb.bbf.TestConfig;
import com.esb.bbf.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.ApiConfig.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
@Slf4j
public class RestTemplateTest {
    @Autowired
    @Qualifier(value = "lbRestTemplate")
    private RestTemplate restTemplate;

    @Test
    public void testGet() {

        Map<String, Object> paramap = new HashMap<>();
        paramap.put("id", 1L);
        Map responseEntity = restTemplate.getForObject("http://account/api/v1/account/{id}",
                Map.class, paramap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", GsonUtils.toJson(responseEntity));
    }

    @Test
    public void testGetForCode400() {

        Map<String, Object> paramap = new HashMap<>();
        paramap.put("id", 12L);
        try {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://10.102.144" +
                            ".72:33155/api/v1/account/{id}",
                    Map.class, paramap);
            Assert.assertNotNull(responseEntity);
            log.info(GsonUtils.toJson(responseEntity));
        } catch (RestClientException e) {
            log.info("---", e);
        }
    }

    @Test
    public void testPost() {
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("id", 200339L);
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("password", "1234");
        Boolean responseEntity = restTemplate.postForObject("http://account/api/v1/account/{id}",
                request, Boolean.class, paramap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", GsonUtils.toJson(responseEntity));
    }

    @Test
    public void testPostToExchange() {
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("id", 200339L);
        Map<String, String> request = new HashMap<>();
        request.put("password", "12346811");
        MultiValueMap<String, String> request1 = new LinkedMultiValueMap<>();
        request1.add("password", "123468");

        HttpEntity requestEntity = new HttpEntity<>(request, null);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("http://account/api/v1/account/{id}",
                HttpMethod.POST, requestEntity, Boolean.class, paramap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", responseEntity.getBody());
    }

    @Test
    public void testPostFormData() {
        Map<String, Object> paramap = new HashMap<>();
        Map<String, String> request = new HashMap<>();
        request.put("age", "11");
        request.put("name", "yangkuan198533");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> request1 = new LinkedMultiValueMap<>();
        request1.add("age", 11);
        request1.add("name", "样刊订单sdf1122");
        HttpEntity requestEntity = new HttpEntity<>(request1, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange("http://self/page/postFormData",
                HttpMethod.POST, requestEntity, Object.class, paramap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", responseEntity.getBody());
    }

    //postFormDataToJson
    @Test
    public void testPostFormDataToJson() {
        Map<String, Object> paramap = new HashMap<>();
        Map<String, String> request = new HashMap<>();
        request.put("age", "11");
        request.put("name", "yangkuan1985");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> request1 = new LinkedMultiValueMap<>();
        request1.add("age", "11");
        request1.add("name", "样刊订单sdf1122postFormDataToJson");
        HttpEntity requestEntity = new HttpEntity<>(request1, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange("http://self/page/postFormDataToJson",
                HttpMethod.POST, requestEntity, Object.class, paramap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", responseEntity.getBody());
    }   //postFormDataToJson
    @Test
    public void testPostToJson() {
        Map<String, Object> paramap = new HashMap<>();
        Map<String, String> request = new HashMap<>();
        request.put("age", "11");
        request.put("name", "yangkuan1985");
        HttpEntity requestEntity = new HttpEntity<>(request, null);

        ResponseEntity<Object> responseEntity = restTemplate.exchange("http://self/page/postToJson",
                HttpMethod.POST, requestEntity, Object.class, paramap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", responseEntity.getBody());
    }

    /**
     * 没有返回值
     */
    @Test
    public void testPut() {
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("accountId", 1L);
        paramap.put("status", 2);
        Boolean result = null;
        restTemplate.put("http://account/api/v1/account/{accountId}/status/{status}",
                result, paramap);
        Assert.assertNotNull(result);
        log.info("response={}", GsonUtils.toJson(result));
    }

    @Test
    public void testExchange() {
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("accountId", 1L);
        paramap.put("status", 2);
        ResponseEntity<Boolean> result = restTemplate.exchange("http://account/api/v1/account/{accountId}/status/{status}",
                HttpMethod.PUT, null, Boolean.class, paramap);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getBody());
        log.info("response={}", GsonUtils.toJson(result));
    }

    @Test
    public void testPostJson() {
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("raw", "15300270491");
        paramap.put("type", 11);
        Map responseEntity = restTemplate.postForObject("http://account/api/cryption/encrypt",
                paramap, Map.class);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", GsonUtils.toJson(responseEntity));
    }

    @Test
    public void testPostJson003() {
        Map<String, Object> paramap = new HashMap<>();
        List<String> raws = new ArrayList<>();
        raws.add("15300270491");
        raws.add("18910170823");
        paramap.put("raw", raws);
        paramap.put("type", 11);
        Object responseEntity = restTemplate.postForObject("http://account/api/cryption/batchEncrypt",
                paramap, Object.class);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", GsonUtils.toJson(responseEntity));
    }

    @Test
    public void testPostJson002() {
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("skey", "2//1560173198258//2e5517f325037586acf9c011f5bb46a1");
        Map responseEntity = restTemplate.postForObject("http://account/api/cryption/decrypt",
                paramap, Map.class);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", GsonUtils.toJson(responseEntity));
    }

    @Test
    public void testPostJson004() {
        Map<String, Object> requestPathmap = new HashMap<>();
        requestPathmap.put("typeId", "1");
        List<Long> param = new ArrayList<>();
        param.add(699L);
        param.add(671L);
        param.add(33L);
        Object responseEntity = restTemplate.postForObject("http://account/api/v1/it_test/failed/type/{typeId}",
                param, Object.class, requestPathmap);
        Assert.assertNotNull(responseEntity);
        log.info("response={}", GsonUtils.toJson(responseEntity));
    }


}
