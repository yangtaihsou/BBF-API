package com.esb.bbf.console.controller;

import com.esb.bbf.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/console/home")
@Slf4j
public class ConsoleController {
    @Autowired
    private Map<String,String> systemConfig;
    @RequestMapping(value = "isLogin", method = RequestMethod.POST)
    @ResponseBody
    public R isLogin() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return R.ok().put("uuid", uuid);
    }

    @RequestMapping(value = "getSystems", method = RequestMethod.GET)
    @ResponseBody
    public R getSystems(){
        return R.ok().put("data",systemConfig);
    }

}
