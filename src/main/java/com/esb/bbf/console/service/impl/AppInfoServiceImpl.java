package com.esb.bbf.console.service.impl;

import com.esb.bbf.console.domain.AppInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.dao.AppInfoMapper;
import com.esb.bbf.console.service.AppInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoMapper appInfoMapper;
    @Override
    public List<AppInfo> queryBySelective(Query<AppInfo> appInfo) {
        List<AppInfo> appInfoList = appInfoMapper.queryBySelective(appInfo);
        return appInfoList;
    }

    @Override
    public Long queryCountBySelective(AppInfo appInfo) {
        Long count = appInfoMapper.queryCountBySelective(appInfo);
        return count;
    }

    @Override
    public AppInfo queryByPrimaryKey(Long id) {
        AppInfo appInfo = appInfoMapper.queryByPrimaryKey(id);
        return appInfo;
    }


    @Override
    public Boolean save(AppInfo appInfo) {
        appInfo.setUpdateDate(new Date());
        appInfo.setCreatedDate(appInfo.getUpdateDate());
        Integer id = appInfoMapper.save(appInfo);
        //appInfoMapper.updateByPrimaryKey(appInfo);
        return id == 0 ? false : true;
    }

    @Override
    public List<AppInfo> queryBySelectiveForPagination(PageQuery<AppInfo> appInfo) {
        List<AppInfo> appInfoList = appInfoMapper.queryBySelectiveForPagination(appInfo);
        return appInfoList;
    }

    @Override
    public void updateByPrimaryKey(AppInfo appInfo) {
        appInfo.setUpdateDate(new Date());
        appInfoMapper.updateByPrimaryKey(appInfo);
    }


    @Override
    public AppInfo queryByAppId(String appId) {
        return appInfoMapper.queryByAppId(appId);
    }
}
