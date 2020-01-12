package com.esb.bbf.console.controller;

import com.esb.bbf.auth.LoginContext;
import com.esb.bbf.console.domain.AppDetailInfo;
import com.esb.bbf.console.domain.AppInfo;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.PageRes;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.service.AppInfoService;
import com.esb.bbf.console.service.JvmScriptInfoService;
import com.esb.bbf.util.R;
import com.esb.bbf.validator.ValidatorCheckService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User:
 * Date18-6-11
 * Time: 下午3:39
 */
@Controller
@RequestMapping("/console/appInfo")
@Getter
@Setter
public class AppInfoController {
    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private JvmScriptInfoService jvmScriptInfoService;

    @Autowired
    private ValidatorCheckService validatorCheckService;

    @Autowired
    private Map<String,String> systemConfig;

    private String rootUserId;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("Duplicates")
    public R save(@Valid AppInfo appInfo) {
        appInfo.setUserId(LoginContext.userName());
        appInfo.setUserName(LoginContext.userName());
        appInfo.setLastModifyUser(LoginContext.userName());
        appInfo.setSystemName(systemConfig.get(appInfo.getSystemCode()));
        appInfo.setStatus(1);
        String checkTips = validatorCheckService.checkValidatorConfig(appInfo);
        if(!StringUtils.isEmpty(checkTips)){
            return R.error(checkTips).put("appInfo", appInfo);
        }
        appInfoService.save(appInfo);
        return R.ok().put("appInfo", appInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("Duplicates")
    public R update(@Valid AppInfo appInfo) {
        appInfo.setUserName(appInfo.getUserId());
        appInfo.setLastModifyUser(LoginContext.userName());
        appInfo.setSystemName(systemConfig.get(appInfo.getSystemCode()));
        String checkTips = validatorCheckService.checkValidatorConfig(appInfo);
        if(!StringUtils.isEmpty(checkTips)){
            return R.error(checkTips).put("appInfo", appInfo);
        }
        appInfoService.updateByPrimaryKey(appInfo);
        return R.ok().put("appInfo", appInfo);
    }
    @RequestMapping(value = "/editSimple", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("Duplicates")
    public R editSimple(AppInfo appInfo) {
        appInfoService.updateByPrimaryKey(appInfo);
        return R.ok().put("appInfo", appInfo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(PageQuery<AppInfo> pageQuery, AppInfo appInfo) {
        pageQuery.setQuery(appInfo);
        pageQuery.setStartRow((pageQuery.getPageNo() - 1) * pageQuery.getPageSize());
        if(!rootUserId.equals(LoginContext.userName())){
            appInfo.setUserIds(LoginContext.userName());
        }
        List<AppInfo> appInfoList = appInfoService.queryBySelectiveForPagination(pageQuery);
        Long count = appInfoService.queryCountBySelective(pageQuery.getQuery());
        PageRes pageRes = new PageRes(appInfoList, count == null ? 0 : count.intValue(), pageQuery);
        return R.ok().put("page", pageRes);
    }

    @ResponseBody
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AppInfo appInfo = appInfoService.queryByPrimaryKey(id);
        return R.ok().put("appInfo", appInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/info/detail", method = RequestMethod.GET)
    public R detailInfo(AppDetailInfo appDetailInfo) {

        Query<JvmScriptInfo> jvmScriptInfoQuery = new Query<>();
        jvmScriptInfoQuery.setQuery(
                JvmScriptInfo.builder().appId(appDetailInfo.getAppId()).build());
        List<JvmScriptInfo> jvmScriptInfoList = jvmScriptInfoService.queryBySelective(jvmScriptInfoQuery);
        List<AppDetailInfo> appDetailInfoList = null;
        if (jvmScriptInfoList != null && jvmScriptInfoList.size() > 0) {
            appDetailInfoList = new ArrayList<>(jvmScriptInfoList.size());
            for (JvmScriptInfo jvmScriptInfo : jvmScriptInfoList) {
                appDetailInfoList.add(
                        AppDetailInfo.builder().appId(jvmScriptInfo.getAppId())
                                .detailUuid(jvmScriptInfo.getScriptId())
                                .detailName(jvmScriptInfo.getScriptName())
                                .remark(jvmScriptInfo.getRemark())
                                .indexOrder(jvmScriptInfo.getIndexOrder())
                                .typeDesc("脚本")
                                .type(1)
                                .id(jvmScriptInfo.getId())
                                .updateDate(jvmScriptInfo.getUpdateDate())
                                .createdDate(jvmScriptInfo.getCreatedDate()).build()
                );
            }
        }
        return R.ok().put("page", appDetailInfoList);
    }

}
