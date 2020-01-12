package com.esb.bbf.console.service;

import com.esb.bbf.console.domain.AppDemoInfo;

public interface AppDemoInfoService  extends BaseService<AppDemoInfo>{
    AppDemoInfo queryByAppId(String appId);
}
