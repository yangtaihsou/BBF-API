package com.esb.bbf.api.script;

import com.dianping.cat.annotation.CatTransaction;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.monitor.CatProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.util.Map;

@Service
@Slf4j
public class JvmScriptRun {

    @Autowired
    private JvmScriptFactory jvmScriptFactory;

    @CatTransaction(type = "method")
    @CatProfile(type = "method_api")
    public Object runScriptExeMethod(JvmScriptInfo scriptInfo, Map httpParaSdk) {
        String scriptType = scriptInfo.getScriptType();
        if (scriptType == null || scriptType.equals("")) {
            scriptType = "groovy";
        }
        try {
            String exeMethodName = "exe";
            String scriptText = jvmScriptFactory.wrapScriptText(scriptInfo, exeMethodName);
            long start = System.currentTimeMillis();
            ScriptEngine engine = jvmScriptFactory.getScriptEngineById(scriptInfo.getScriptId(), scriptType);
            log.debug("Api={},执行脚本={},引擎engine创建花费的时间={}", scriptInfo.getAppId(),
                    scriptInfo.getScriptId(), (System.currentTimeMillis() - start));
           /* s参数不放在engine里共享，后期会反向挂载新属性*/
            start = System.currentTimeMillis();
            //编译脚本成class
            engine.eval(scriptText);
            log.debug("Api={},执行脚本={},eval编译花费的时间={}", scriptInfo.getAppId(),
                    scriptInfo.getScriptId(), (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction(exeMethodName, httpParaSdk, httpParaSdk);
            log.debug("Api={},执行脚本={},invokeFunction花费的时间={}", scriptInfo.getAppId(),
                    scriptInfo.getScriptId(), (System.currentTimeMillis() - start));
            return object;
        } catch (Exception e) {
            log.error("appId:{},scriptId:{},脚本类型:{},执行ScriptRun的runScript异常:{}",
                    scriptInfo.getAppId(), scriptInfo.getScriptId(), scriptType, e);
            throw new RuntimeException(e);
        }
    }


}
