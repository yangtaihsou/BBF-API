package com.esb.bbf.api.serverless.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Json {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    /**
     * 将对象转换为字符串
     *
     * @param obj 对象
     * @return 字符串
     */
    public final String string(final Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将json字符串转换为map
     *
     * @param jsonStr json字符串
     * @return map
     */
    public Map<String, Object> map(final String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为对象列表
     *
     * @param jsonStr json字符串
     * @return 对象实例列表
     */
    public List array(final String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            /*JavaType t = objectMapper.getTypeFactory()
                    .constructParametricType(List.class, objClass);*/
            return objectMapper.readValue(jsonStr, new TypeReference<List<Object>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
