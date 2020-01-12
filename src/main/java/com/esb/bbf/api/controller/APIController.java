package com.esb.bbf.api.controller;

import com.alibaba.fastjson.JSON;
import com.esb.bbf.api.serverless.ServerlessSdk;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.api.script.JvmScriptRun;
import com.esb.bbf.api.serverless.ApiContext;
import com.esb.bbf.api.service.IApiInfoQryInfoService;
import com.esb.bbf.console.bussiness.ApiInfoQryInfo;
import com.esb.bbf.console.domain.AppDemoInfo;
import com.esb.bbf.util.R;
import com.esb.bbf.validator.ValidatorCheckService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Slf4j
public class APIController {

    @Autowired
    private ServerlessSdk sdk;

    @Value("${spring.profiles.active}")
    private String profile;
    @Autowired
    private ValidatorCheckService validatorCheckService;

    @Autowired
    private JvmScriptRun jvmScriptRun;
    @Autowired
    private IApiInfoQryInfoService apiInfoQryInfoService;

    /**
     * 外网用户，执行api
     *
     * @return
     */
    @RequestMapping(value = "/rundApi/{apiId}")
    @ResponseBody
    public Object rundApi(
            @RequestHeader Map<String, String> httpHeader,
            @RequestHeader MultiValueMap<String, String> httpHeaderM,
            @PathVariable Map<String, String> httpUriPara,
            @RequestParam Map<String, String> httpPara,
            @RequestParam MultiValueMap<String, String> httpParaM,
            @Valid @NotEmpty @PathVariable String apiId,
            HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            ApiInfoQryInfo apiInfoQryInfo = apiInfoQryInfoService.queryApiInfo(apiId);
            if (apiInfoQryInfo == null
                    || apiInfoQryInfo.getAppInfo() == null) {
                return R.error("非法apiId").put("time", (System.currentTimeMillis() - start));
            }
            if (apiInfoQryInfo.getJvmScriptInfoList() == null
                    || apiInfoQryInfo.getJvmScriptInfoList().size() == 0) {
                return R.error("非法apiId").put("time", (System.currentTimeMillis() - start));
            }

            ApiContext.set(apiId);
            Map httpParaSdk = sdk.initHttpParaSdk(httpHeader,
                    httpHeaderM,
                    httpUriPara,
                    httpPara,
                    httpParaM,
                    apiId,
                    request);

            //验证请求参数
            String checkTip = validatorCheckService.checkHttpRequest(apiInfoQryInfo.getAppInfo(), httpParaSdk);
            if (!StringUtils.isEmpty(checkTip)) {
                return R.error(checkTip).put("time", (System.currentTimeMillis() - start));
            }
            AppDemoInfo appDemoInfo = apiInfoQryInfo.getAppDemoInfo();
            JvmScriptInfo jvmScriptInfo = apiInfoQryInfo.getJvmScriptInfoList().get(0);
            JvmScriptInfo mockJvmScriptInfo = new JvmScriptInfo();
            if (isMock(appDemoInfo, mockJvmScriptInfo)) {
                jvmScriptInfo = mockJvmScriptInfo;
            }
            Object reponse = jvmScriptRun.runScriptExeMethod(jvmScriptInfo,
                    httpParaSdk);
            return R.ok().put("data", reponse).put("time", (System.currentTimeMillis() - start));
        } catch (Exception e) {
            log.error("apiId={},运行脚本异常:{}", apiId, e);
            return R.error("执行api异常：" + e.getMessage()).put("time", (System.currentTimeMillis() - start));
        }
    }

    /**
     * 办公网域账号，执行api
     *
     * @param httpHeader
     * @param httpHeaderM
     * @param httpUriPara
     * @param httpPara
     * @param httpParaM
     * @param apiId
     * @param request
     * @return
     */
    @RequestMapping(value = "/office/rundApi/{apiId}")
    @ResponseBody
    public Object officeRundApi(
            @RequestHeader Map<String, String> httpHeader,
            @RequestHeader MultiValueMap<String, String> httpHeaderM,
            @PathVariable Map<String, String> httpUriPara,
            @RequestParam Map<String, String> httpPara,
            @RequestParam MultiValueMap<String, String> httpParaM,
            @Valid @NotEmpty @PathVariable String apiId,
            HttpServletRequest request) {
        return this.rundApi(httpHeader, httpHeaderM, httpUriPara, httpPara, httpParaM, apiId, request);
    }

    public Boolean isMock(AppDemoInfo appDemoInfo, JvmScriptInfo mockJvmScriptInfo) {
        if (StringUtils.isEmpty(profile) || profile.equals("online")) {
            return false;
        }
        if (appDemoInfo == null || !appDemoInfo.getMocked()) {
            return false;
        }
        String mockInfo = appDemoInfo.getMockInfo();
        if (StringUtils.isEmpty(mockInfo)) {
            return false;
        }
        try {
            JvmScriptInfo mockSciptInfo = JSON.parseObject(mockInfo, JvmScriptInfo.class);
            if (StringUtils.isEmpty(mockSciptInfo.getScriptText())) {
                return false;
            }
            if (StringUtils.isEmpty(mockSciptInfo.getScriptType())) {
                return false;
            }
            mockJvmScriptInfo.setScriptText(mockSciptInfo.getScriptText());
            mockJvmScriptInfo.setScriptType(mockSciptInfo.getScriptType());
        } catch (Exception e) {
            log.error("api:{},mock参数转json对象异常:{}", appDemoInfo.getAppId(), e);
            return false;
        }
        mockJvmScriptInfo.setAppId(appDemoInfo.getAppId());
        return true;
    }

    /**
     * 执行脚本
     *
     * @param jvmScriptRunInfo
     * @return
     */
    @RequestMapping(value = "/runScript", method = RequestMethod.POST)
    @ResponseBody
    public R runScript(JvmScriptInfo jvmScriptRunInfo) {
        try {
            Map map = new HashMap();
            ApiContext.set(jvmScriptRunInfo.getScriptId());
            map.put("httpBody", initInputParaSdk(jvmScriptRunInfo.getDemoInputPara()));
            Object reponseObj = jvmScriptRun.runScriptExeMethod(
                    jvmScriptRunInfo, map);
            return R.ok().put("reponse", JSON.toJSONString(reponseObj));
        } catch (Exception e) {
            log.error("运行脚本异常:{}", e);
            return R.ok().put("reponse", "运行脚本异常" + e.getMessage());
        }
    }


    private Object initInputParaSdk(String inputJsonPara) {
        if (StringUtils.isEmpty(inputJsonPara)) {
            return null;
        }
        try {
            return JSON.parseObject(inputJsonPara);
        } catch (Exception e) {
            return JSON.parseArray(inputJsonPara);
        }
    }

}
