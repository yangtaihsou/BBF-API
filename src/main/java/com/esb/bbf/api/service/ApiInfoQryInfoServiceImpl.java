package com.esb.bbf.api.service;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.annotation.CatTransaction;
import com.esb.bbf.api.script.WarmupService;
import com.google.common.cache.Cache;
import com.esb.bbf.console.bussiness.ApiInfoQryInfo;
import com.esb.bbf.console.bussiness.IApiInfoQryClient;
import com.esb.bbf.util.FileReadUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

@Service
@Slf4j
public class ApiInfoQryInfoServiceImpl implements IApiInfoQryInfoService {
    @Autowired
    private IApiInfoQryClient iApiInfoQueryClient;

    @Resource
    private Cache<String, ApiInfoQryInfo> apiInfoQryInfoCache;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private WarmupService warmupService;
    private String targetSrcDir = "/opt/esb/bbfapi/";

    private Charset utf_charset = Charset.forName("UTF-8");

    @CatTransaction(type = "method")
    @Override
    public ApiInfoQryInfo queryApiInfo(String apiId) {
        ApiInfoQryInfo apiInfoQryInfo = readFromMemCache(apiId);
        if (apiInfoQryInfo != null) {
            return apiInfoQryInfo;
        }
        try {
            apiInfoQryInfo = readFromRemote(apiId);
            //写入内存比较快
            this.writeToMemCache(apiId, apiInfoQryInfo);
            if (apiInfoQryInfo == null) {
                return apiInfoQryInfo;
            }
            ApiInfoQryInfo finalApiInfoQryInfo = apiInfoQryInfo;
            threadPoolTaskExecutor.execute(() -> {
                this.writeToDisk(apiId, finalApiInfoQryInfo);
            });
            return apiInfoQryInfo;
        } catch (Exception e) {
            log.info("远程配置中心获取api信息异常,api={},异常={}", apiId, e);
            //如果远程请求异常，从本地磁盘获取是否存在
            return readFromDisk(apiId);
        }
    }


    private ApiInfoQryInfo readFromRemote(String apiId) {
        return iApiInfoQueryClient.queryApiInfo(apiId);
    }

    private ApiInfoQryInfo readFromMemCache(String apiId) {
        try {
            return apiInfoQryInfoCache.getIfPresent(apiId);
        } catch (Exception e) {
            log.info("内存获取api信息异常,api={},异常={}", apiId, e);
            return null;
        }
    }

    private void writeToMemCache(String apiId, ApiInfoQryInfo apiInfoQryInfo) {
        try {
            apiInfoQryInfoCache.put(apiId, apiInfoQryInfo);
        } catch (Exception e) {
            log.info("内存写入api信息异常,api={},异常={}", apiId, e);
        }
    }

    public ApiInfoQryInfo readFromDisk(String apiId) {
        try {
            String path = targetSrcDir + apiId;
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            long fileLastTime = file.lastModified();
            DateTime dateTime = new DateTime();
            //时间可以调整
            long lasttime = dateTime.plusDays(-1).getMillis();
            //api信息存盘的数据更新时间，应该不落后一天。
            //假如配置中心的api删除或者下线，那么本地磁盘需要清除。这个起到类似清除的机制。
            if (fileLastTime < lasttime) {
                return null;
            }
            //TODO 应该是首先判断文件是否存在，和失效
            String text = FileReadUtils.readContext(path, utf_charset);
            if (!StringUtils.isEmpty(text)) {
                return JSON.parseObject(text, ApiInfoQryInfo.class);
            }
        } catch (Exception e) {
            log.info("读取apiInfo文件发生异常,api={},异常={}", apiId, e);
        }
        return null;
    }

    private void writeToDisk(String apiId, ApiInfoQryInfo apiInfoQryInfo) {
        try {
            FileReadUtils.writeSrcFile(targetSrcDir, apiId, JSON.toJSONString(apiInfoQryInfo), utf_charset);
        } catch (Exception e) {
            log.info("生成apiInfo文件发生异常,api={},异常={}", apiId, e);
        }
    }

    @PostConstruct
    public void warmUp() {
        try {
            long startTime = System.currentTimeMillis();
            List<File> fileList = warmupService.getScriptcodeFiles(targetSrcDir, -30, 100);
            if (fileList == null || fileList.size() == 0) {
                return;
            }
            for (File file : fileList) {
                String text = FileReadUtils.readContext(file.getPath(), utf_charset);
                if (!StringUtils.isEmpty(text)) {
                    warmupService.warmupScript(
                            JSON.parseObject(text, ApiInfoQryInfo.class)
                                    .getJvmScriptInfoList()
                                    .get(0));
                     }
            }

            long endTime = System.currentTimeMillis() - startTime;
            log.info("预热api脚本,耗费的时间:{}", endTime);
        } catch (Exception e) {
            log.error("随着容器启动，预热api脚本失败:{}", e);
        }
    }


}
