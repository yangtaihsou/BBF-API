package com.esb.bbf.console.service;

import com.esb.bbf.console.domain.AppInfo;

public interface AppInfoService  extends BaseService<AppInfo>{
    AppInfo queryByAppId(String appId);
}
