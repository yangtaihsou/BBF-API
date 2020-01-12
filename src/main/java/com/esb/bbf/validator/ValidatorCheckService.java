package com.esb.bbf.validator;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.annotation.CatTransaction;
import com.esb.bbf.console.domain.AppInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ValidatorCheckService {
    @CatTransaction(type = "method")
    public String checkHttpRequest(AppInfo appInfo, Map httpParaSdk) {
        if (StringUtils.isEmpty(appInfo.getCheckPara())) {
            return null;
        }
        List<RequestParaCheckConfig> listConfig = convertToCheckList(appInfo.getCheckPara(), appInfo.getAppId());
        StringBuffer checkTipMsgBuffer = new StringBuffer();
        for (RequestParaCheckConfig requestParaCheckConfig : listConfig) {
            String paraName = requestParaCheckConfig.getName();
            if (requestParaCheckConfig.getValidatorConfigList() == null
                    || requestParaCheckConfig.getValidatorConfigList().size() == 0) {
                continue;
            }
            //某个参数的验证器列表
            for (ValidatorConfig validatorConfig : requestParaCheckConfig.getValidatorConfigList()) {
                String scope = validatorConfig.getScope();
                if (scope.equals("httpPara")) {
                    if(!checkHttpPara(httpParaSdk,validatorConfig,paraName)) {
                        String checkTipMsg = validatorConfig.getMessage();
                        checkTipMsgBuffer.append(checkTipMsg).append("\n");
                    }
                }
                if (scope.equals("httpBody")) {
                    if(!checkHttpBody(httpParaSdk,validatorConfig,paraName)) {
                        String checkTipMsg = validatorConfig.getMessage();
                        checkTipMsgBuffer.append(checkTipMsg).append("\n");
                    }
                }
                if (scope.equals("httpHeader")) {
                    if(!checkHttpHeader(httpParaSdk,validatorConfig,paraName)) {
                        String checkTipMsg = validatorConfig.getMessage();
                        checkTipMsgBuffer.append(checkTipMsg).append("\n");
                    }
                }
            }
        }
        return checkTipMsgBuffer.toString();
    }


    @SuppressWarnings("Duplicates")
    private boolean checkHttpHeader(Map httpParaSdk, ValidatorConfig validatorConfig, String paraName) {
        Map<String, String> httpPara = (Map<String, String>) httpParaSdk.get("httpHeader");
        if (httpPara == null || httpPara.size() == 0) {
            if (ValidatorEnum.getValidator(validatorConfig.getValidator()).getClass().getName().equals(
                    ValidatorEnum.notNullValidator.getValidator().getClass().getName()) ) {
                return false;
            }
            return true;
        }
        String paraValue = httpPara.get(paraName);
        return ValidatorEnum.getValidator(validatorConfig.getValidator())
                .isValid(paraValue, validatorConfig);

    }

    @SuppressWarnings("Duplicates")
    private boolean checkHttpPara(Map httpParaSdk, ValidatorConfig validatorConfig, String paraName) {
        Map<String, String> httpPara = (Map<String, String>) httpParaSdk.get("httpPara");
        if (httpPara == null || httpPara.size() == 0) {
            if (ValidatorEnum.getValidator(validatorConfig.getValidator()).getClass().getName().equals(
                    ValidatorEnum.notNullValidator.getValidator().getClass().getName()) ) {
                return false;
            }
            return true;
        }
        String paraValue = httpPara.get(paraName);
        return ValidatorEnum.getValidator(validatorConfig.getValidator())
                .isValid(paraValue, validatorConfig);

    }

    private boolean checkHttpBody(Map httpParaSdk, ValidatorConfig validatorConfig, String paraName) {
        Object httpBody = httpParaSdk.get("httpBody");
        if (httpBody == null) {
            if (ValidatorEnum.getValidator(validatorConfig.getValidator()).getClass().getName().equals(
                    ValidatorEnum.notNullValidator.getValidator().getClass().getName()) ) {
                return false;
            }
            return true;
        }
        if (httpBody instanceof Map) {
            Map<String, Object> httpBodyMap = (Map<String, Object>) httpBody;
            Object paraValue = httpBodyMap.get(paraName);
            return ValidatorEnum.getValidator(validatorConfig.getValidator())
                    .isValid(paraValue, validatorConfig);
        }
        if (httpBody instanceof List) {
            List<Object> httpBodyList = (List<Object>) httpBody;
            //如果要全部验证，需要回归遍历
            for (Object subObject : httpBodyList) {
                if (subObject instanceof Map) {
                    Map<String, Object> httpBodyMap = (Map<String, Object>) subObject;
                    Object paraValue = httpBodyMap.get(paraName);
                    return ValidatorEnum.getValidator(validatorConfig.getValidator())
                            .isValid(paraValue, validatorConfig);
                }
            }
        }
        return true;
    }

    public String checkValidatorConfig(AppInfo appInfo) {
        if (StringUtils.isEmpty(appInfo.getCheckPara())) {
            return null;
        }
        try {

            List<RequestParaCheckConfig> listConfig = convertToCheckList(appInfo.getCheckPara(), appInfo.getAppId());
            for (RequestParaCheckConfig requestParaCheckConfig : listConfig) {
                if (requestParaCheckConfig.getValidatorConfigList() == null
                        || requestParaCheckConfig.getValidatorConfigList().size() == 0) {
                    continue;
                }
                //某个参数的验证器列表
                for (ValidatorConfig validatorConfig : requestParaCheckConfig.getValidatorConfigList()) {
                    if (StringUtils.isEmpty(validatorConfig.getValidator())) {
                        return requestParaCheckConfig.getName() + "的validator设置为空";
                    }
                    Validator validator = ValidatorEnum.getValidator(validatorConfig.getValidator());
                    if (validator == null) {
                        return requestParaCheckConfig.getName() + "的validator不存在";
                    }
                }
            }
        } catch (Exception e) {
            log.error("appId:{},设置参数验证器异常:{}", appInfo.getAppId(), e);
            return "设置参数验证器异常,请检查配置";
        }
        return null;
    }

    public List<RequestParaCheckConfig> convertToCheckList(String checkPara, String appId) {
        List<RequestParaCheckConfig> listConfig = JSON.parseArray(checkPara,
                RequestParaCheckConfig.class);
        for (RequestParaCheckConfig requestParaCheckConfig : listConfig) {
            //某个参数的验证器列表
            List<ValidatorConfig> validatorConfigList = JSON.parseArray(requestParaCheckConfig.getConfig(),
                    ValidatorConfig.class);
            requestParaCheckConfig.setValidatorConfigList(validatorConfigList);
        }
        return listConfig;
    }
}
