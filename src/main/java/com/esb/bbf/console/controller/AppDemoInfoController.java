package com.esb.bbf.console.controller;

import com.esb.bbf.console.service.AppDemoInfoService;
import com.esb.bbf.util.R;
import com.esb.bbf.console.domain.AppDemoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * User:
 * Date18-6-11
 * Time: 下午3:39
 */
@Controller
@RequestMapping("/console/appDemoInfo")
public class AppDemoInfoController {
    @Autowired
    private AppDemoInfoService appDemoInfoService;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public R save(@Valid @RequestBody AppDemoInfo appDemoInfo) {
        appDemoInfoService.save(appDemoInfo);
        return R.ok().put("appDemoInfo", appDemoInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R update(@Valid @RequestBody AppDemoInfo appDemoInfo) {
        appDemoInfoService.updateByPrimaryKey(appDemoInfo);
        return R.ok().put("appDemoInfo", appDemoInfo);
    }

    @ResponseBody
    @RequestMapping("/info/{appId}")
    public R info(@PathVariable("appId") String appId) {
        AppDemoInfo appDemoInfo = appDemoInfoService.queryByAppId(appId);
        return R.ok().put("appDemoInfo", appDemoInfo);
    }


}
