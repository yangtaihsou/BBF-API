package com.esb.bbf.api.script;

import com.esb.bbf.api.serverless.ServerlessSdk;
import com.esb.bbf.api.serverless.raw.RawEnum;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JvmScriptFactory {

    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static final Map<String, ScriptEngine> scriptIdEngineMap = Maps.newConcurrentMap();
    private Map<String, ScriptEngine> scriptEngineMap = new HashMap<>();

    @Autowired
    private ServerlessSdk sdk;

    /**
     * 对各种脚本类型的Engine进行创建预热。
     *
     * @return
     */
    @Bean("scriptEngineConfig")
    public Map<String, ScriptEngine> warmUpScriptEngine() {
        List<ScriptEngineFactory> list = scriptEngineManager.getEngineFactories();
        // Print the details of each engine
        for (ScriptEngineFactory f : list) {
            List<String> engineNameList = f.getNames();
            ScriptEngine scriptEngine = null;
            for (String engineName : engineNameList) {
                if (scriptEngine == null) {
                    scriptEngine = scriptEngineManager.getEngineByName(engineName);
                }
                scriptEngineMap.put(engineName, scriptEngine);
                log.debug("初始化脚本类型:{}的引擎，初始化结果:{}", engineName, scriptEngine.toString());
            }
        }
        return scriptEngineMap;
    }

    /**
     * 为每个代码单独创建一个engine，为了线程安全。
     * 因为scriptEngineMap提前预热了各种getEngineByName，所以这里单独再得到一个，花费时间在10ms
     *
     * @param scriptId
     * @param scripType
     * @return
     */
    public ScriptEngine getScriptEngineById(String scriptId, String scripType) {
        ScriptEngine scriptEngine = scriptIdEngineMap.get(scriptId);
        if (scriptEngine != null) {
            return scriptEngine;
        }
        scriptEngine = scriptEngineManager.getEngineByName(scripType);
        Map serverlessSdk = sdk.loadServerlessSdk();
        scriptEngine.put("$p", serverlessSdk);
        scriptEngine.put("p", serverlessSdk);
        scriptIdEngineMap.putIfAbsent(scriptId, scriptEngine);
        return scriptEngine;
    }

    public String wrapScriptText(JvmScriptInfo scriptInfo, String exeMethodName) {
        String scriptType = scriptInfo.getScriptType();
        String scriptText = scriptInfo.getScriptText();
        if (StringUtils.isEmpty(scriptType) || scriptType.equals("groovy")) {
            scriptText = "def " + exeMethodName + "($s,s){" + scriptText + "}";
        } else if (scriptType.equals("js") || scriptType.equals("javascript")) {
            StringBuffer rawTypes = new StringBuffer();
            rawTypes.append("var Array = Java.type('" + RawEnum.JS_ARRAY + "');");
            scriptText = "function " + exeMethodName + "($s,s){" + rawTypes.toString() + scriptText + "}";
        } else if (scriptType.equals("python") || scriptType.equals("jython")) {
            //scriptText = "# -*- coding: utf-8 -*-\n" + scriptText;
            scriptText = "def " + exeMethodName + "( s,s1 ):\n" + scriptText + "";
            // scriptText = Py.newString(scriptText).toString();
        } else if (scriptType.equals("ruby")) {
            scriptText = "def " + exeMethodName + "\n" + scriptText + "\nend";
        }
        return scriptText;
    }
}
