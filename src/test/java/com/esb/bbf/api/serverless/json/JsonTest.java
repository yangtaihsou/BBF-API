package com.esb.bbf.api.serverless.json;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class JsonTest {
    @Test
    public void test(){
        Json json = new Json();
        List list = json.array("[\"asddsfd\",\"b\",\"c\",\"d\"]");
        log.info(list.get(0).toString());
    }


    @Test
    public void testCC(){
        String jsonStr = "{\"name\":\"\\x22\\x5cx\",\"age\":10}";
        Object jsonobj = JSON.parse(jsonStr);
        log.info(JSON.toJSONString(jsonobj));
    }
}
