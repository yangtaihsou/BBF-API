package com.esb.bbf.console.dao;

import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.domain.AppDemoInfo;

import java.util.List;

public interface AppDemoInfoMapper {
    List<AppDemoInfo> queryBySelective(Query<AppDemoInfo> appDemoInfo);

    long queryCountBySelective(AppDemoInfo appDemoInfo);

    AppDemoInfo queryByPrimaryKey(Long id);

    Integer save(AppDemoInfo appDemoInfo);

    Integer updateByPrimaryKey(AppDemoInfo appDemoInfo);

    List<AppDemoInfo> queryBySelectiveForPagination(PageQuery<AppDemoInfo> appDemoInfo);

    AppDemoInfo queryByAppId(String appId);
}
