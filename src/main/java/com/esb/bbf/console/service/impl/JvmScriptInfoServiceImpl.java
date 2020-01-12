package com.esb.bbf.console.service.impl;

import com.alibaba.fastjson.JSON;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.console.domain.query.PageQuery;
import com.esb.bbf.console.domain.query.Query;
import com.esb.bbf.console.dao.JvmScriptInfoHistoryMapper;
import com.esb.bbf.console.dao.JvmScriptInfoMapper;
import com.esb.bbf.console.domain.JvmScriptInfoHistory;
import com.esb.bbf.console.service.JvmScriptInfoService;
import com.esb.bbf.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JvmScriptInfoServiceImpl implements JvmScriptInfoService {

    private static final Logger logger = LoggerFactory.getLogger(JvmScriptInfoServiceImpl.class);
    @Autowired
    private JvmScriptInfoMapper jvmScriptInfoMapper;

    @Autowired
    private JvmScriptInfoHistoryMapper jvmScriptInfoHistoryMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Override
    public List<JvmScriptInfo> queryBySelective(Query<JvmScriptInfo> jvmScriptInfo) {
        List<JvmScriptInfo> jvmScriptInfoList = jvmScriptInfoMapper.queryBySelective(jvmScriptInfo);
        return jvmScriptInfoList;
    }

    @Override
    public Long queryCountBySelective(JvmScriptInfo jvmScriptInfo) {
        Long count = jvmScriptInfoMapper.queryCountBySelective(jvmScriptInfo);
        return count;
    }

    @Override
    public JvmScriptInfo queryByPrimaryKey(Long id) {
        JvmScriptInfo jvmScriptInfo = jvmScriptInfoMapper.queryByPrimaryKey(id);
        return jvmScriptInfo;
    }


    @Override
    public Boolean save(JvmScriptInfo jvmScriptInfo) {

        jvmScriptInfo.setUpdateDate(new Date());
        jvmScriptInfo.setCreatedDate(jvmScriptInfo.getUpdateDate());
        Integer id = jvmScriptInfoMapper.save(jvmScriptInfo);
        return id == 0 ? false : true;
    }

    @Override
    public List<JvmScriptInfo> queryBySelectiveForPagination(PageQuery<JvmScriptInfo> jvmScriptInfo) {
        List<JvmScriptInfo> jvmScriptInfoList = jvmScriptInfoMapper.queryBySelectiveForPagination(jvmScriptInfo);
        return jvmScriptInfoList;
    }

    @Override
    public void updateByPrimaryKey(JvmScriptInfo jvmScriptInfo) {
        jvmScriptInfo.setUpdateDate(new Date());
        jvmScriptInfoMapper.updateByPrimaryKey(jvmScriptInfo);
    }


    @Override
    public void updateWithBack(JvmScriptInfo jvmScriptInfo) {
        JvmScriptInfo oldJvmScriptInfo = jvmScriptInfoMapper.queryByScriptId(jvmScriptInfo.getScriptId());
        jvmScriptInfo.setUpdateDate(new Date());
        jvmScriptInfoMapper.updateByPrimaryKey(jvmScriptInfo);
        threadPoolTaskExecutor.execute(()->{
            if(!compareCodeVersion(jvmScriptInfo,oldJvmScriptInfo)){
                return;
            }
            JvmScriptInfoHistory jvmScriptInfoHistory = new JvmScriptInfoHistory();
            BeanUtils.copyProperties(oldJvmScriptInfo,jvmScriptInfoHistory);
            jvmScriptInfoHistory.setId(null);
            jvmScriptInfoHistory.setUpdateDate(new Date());
            jvmScriptInfoHistory.setCreatedDate(new Date());
            jvmScriptInfoHistoryMapper.save(jvmScriptInfoHistory);
        });
    }
    private Boolean compareCodeVersion(JvmScriptInfo jvmScriptInfo, JvmScriptInfo oldJvmScriptInfo ){
        oldJvmScriptInfo.setCreatedDate(null);
        jvmScriptInfo.setCreatedDate(null);
        oldJvmScriptInfo.setUpdateDate(null);
        jvmScriptInfo.setUpdateDate(null);
        String newCode = MD5Util.crypt(JSON.toJSONString(jvmScriptInfo));
        String oldCode = MD5Util.crypt(JSON.toJSONString(oldJvmScriptInfo));
        if(newCode.equals(oldCode)){
            return false;
        }
        return true;
    }
}
