package com.esb.bbf.console.controller;

import com.alibaba.fastjson.JSON;
import com.esb.bbf.console.domain.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginTestController {

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public DemoData testResponseParaList(@RequestBody DemoData demoData) {
        log.info("登录检查参数:{}", JSON.toJSONString(demoData));
        demoData.setAge(10L);
        return demoData;
    }

}
