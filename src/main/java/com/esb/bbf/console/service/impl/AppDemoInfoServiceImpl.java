package com.esb.bbf.console.service.impl;

import com.esb.bbf.console.dao.AppDemoInfoMapper;
import com.esb.bbf.console.domain.AppDemoInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.service.AppDemoInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("appDemoInfoService")
public class AppDemoInfoServiceImpl implements AppDemoInfoService {

    private static final Logger logger = LoggerFactory.getLogger(AppDemoInfoServiceImpl.class);
    @Autowired
    private AppDemoInfoMapper appDemoInfoMapper;


    @Override
    public List<AppDemoInfo> queryBySelective(Query<AppDemoInfo> appDemoInfo) {
        List<AppDemoInfo> appDemoInfoList = appDemoInfoMapper.queryBySelective(appDemoInfo);
        return appDemoInfoList;
    }

    @Override
    public Long queryCountBySelective(AppDemoInfo appDemoInfo) {
        Long count = appDemoInfoMapper.queryCountBySelective(appDemoInfo);
        return count;
    }

    @Override
    public AppDemoInfo queryByPrimaryKey(Long id) {
        AppDemoInfo appDemoInfo = appDemoInfoMapper.queryByPrimaryKey(id);
        return appDemoInfo;
    }


    @Override
    public Boolean save(AppDemoInfo appDemoInfo) {
        appDemoInfo.setUpdateDate(new Date());
        appDemoInfo.setCreatedDate(appDemoInfo.getUpdateDate());
        Integer id = appDemoInfoMapper.save(appDemoInfo);
        return id == 0 ? false : true;
    }

    @Override
    public void updateByPrimaryKey(AppDemoInfo appDemoInfo) {
        appDemoInfo.setUpdateDate(new Date());
        appDemoInfoMapper.updateByPrimaryKey(appDemoInfo);
    }

    @Override
    public List<AppDemoInfo> queryBySelectiveForPagination(PageQuery<AppDemoInfo> appDemoInfo) {
        List<AppDemoInfo> appDemoInfoList = appDemoInfoMapper.queryBySelective(appDemoInfo);
        return appDemoInfoList;
    }


    @Override
    public AppDemoInfo queryByAppId(String appId) {
        return appDemoInfoMapper.queryByAppId(appId);
    }
}
