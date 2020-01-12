package com.esb.bbf.console.bussiness;

import com.esb.bbf.console.domain.AppInfo;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.service.AppInfoService;
import com.esb.bbf.console.service.JvmScriptInfoService;
import com.esb.bbf.console.domain.AppDemoInfo;
import com.esb.bbf.console.service.AppDemoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiInfoQryClientImpl implements IApiInfoQryClient {
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private JvmScriptInfoService jvmScriptInfoService;
    @Autowired
    private AppDemoInfoService appDemoInfoService;

    @Override
    public ApiInfoQryInfo queryApiInfo(String apiId) {
        AppInfo appInfo = appInfoService.queryByAppId(apiId);
        if (appInfo == null) {
            return null;
        }
        if (appInfo.getStatus() != null && appInfo.getStatus() != 1) {
            return null;
        }
        AppDemoInfo appDemoInfo = appDemoInfoService.queryByAppId(apiId);
        Query query = Query.builder().query(
                JvmScriptInfo.builder().appId(apiId).build()
        ).build();
        List<JvmScriptInfo> jvmScriptInfoList = jvmScriptInfoService.queryBySelective(query);

        ApiInfoQryInfo apiInfoQryInfo = ApiInfoQryInfo.builder()
                .appInfo(appInfo)
                .jvmScriptInfoList(jvmScriptInfoList)
                .appDemoInfo(appDemoInfo)
                .build();
        return apiInfoQryInfo;
    }
}
