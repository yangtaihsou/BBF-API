package com.esb.bbf.console.dao;

import com.esb.bbf.console.domain.AppInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;

import java.util.List;

public interface AppInfoMapper {
    List<AppInfo> queryBySelective(Query<AppInfo> appInfo);

    long queryCountBySelective(AppInfo appInfo);

    AppInfo queryByPrimaryKey(Long id);

    Integer save(AppInfo appInfo);

    List<AppInfo> queryBySelectiveForPagination(PageQuery<AppInfo> appInfo);

    Integer updateByPrimaryKey(AppInfo appInfo);

    AppInfo queryByAppId(String appId);


}
