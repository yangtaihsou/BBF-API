package com.esb.bbf.api.script;

import com.dianping.cat.annotation.CatTransaction;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.util.FileReadUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WarmupService {

    @Autowired
    private Map<String, ScriptEngine> scriptEngineConfig;

    @Autowired
    private JvmScriptFactory jvmScriptFactory;

    /**
     * 取出需要预热加载的api脚本
     * 预热策略，不同公司有不同的要求(比如晚上重启、高并发时重启)。
     * 这里比较简单
     *
     * @param targetSrcDir  api磁盘备份的目录
     * @param beforeMinutes 最近备份的时间
     * @param topNum        取出的文件数量。如果符合的文件，超过topNum，那么最多取topNum个文件
     * @return
     */
    @CatTransaction(type = "method")
    public List<File> getScriptcodeFiles(String targetSrcDir, int beforeMinutes, int topNum) {
        long startTime = System.currentTimeMillis();
        File[] files = FileReadUtils.getDirFiles(targetSrcDir);
        if (files == null || files.length == 0) {
            return null;
        }
        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            long fileLastTime = file.lastModified();
            DateTime dateTime = new DateTime();
            //时间可以调整。加载30分钟之类备份更新的api脚本
            long lasttime = dateTime.plusMinutes(beforeMinutes).getMillis();
            if (fileLastTime < lasttime) {
                continue;
            }
            fileList.add(file);
        }
        if (fileList.size() > topNum) {
            fileList = fileList.stream()
                    .sorted(Comparator.comparing(File::lastModified))
                    .collect(Collectors.toList());
            fileList = fileList.subList(fileList.size() - topNum, fileList.size());
        }
        long endTime = System.currentTimeMillis() - startTime;
        log.info("获取符合策略的磁盘备份API文件,耗费的时间:{}", endTime);
        return fileList;
    }

    /**
     * 预热。 性能损耗有两个地方： 初次创建ScriptEngine;具体的一个engine初次编译脚本(eval方法)
     * 另外注意engine的eval会将编译后的文件缓存，但缓存是SoftReference。如果内存空间不足了，就会回收这些对象的内存
     * @param scriptInfo
     */
    @CatTransaction(type = "method")
    public void warmupScript(JvmScriptInfo scriptInfo) {
        try {
            String scriptType = scriptInfo.getScriptType();
            if (scriptType == null || scriptType.equals("")) {
                scriptType = "groovy";
            }
            ScriptEngine engine = jvmScriptFactory.getScriptEngineById(scriptInfo.getScriptId(), scriptType);
            engine.eval(jvmScriptFactory.wrapScriptText(scriptInfo, "exe"));
            log.debug("预热api:{},scriptId:{}完毕",scriptInfo.getAppId(),
                    scriptInfo.getScriptId());
        } catch (Exception e) {
            log.error("预热api:{},scriptId:{},的脚本异常:{}",
                    scriptInfo.getAppId(),
                    scriptInfo.getScriptId(),
                    e);
        }

    }
}
