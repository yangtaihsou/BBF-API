package com.esb.bbf.jvmScript;

import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Slf4j
public class ScriptRun {


    private static ScriptEngineManager factory = new ScriptEngineManager();

    /**
     * @param scriptText 要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName    要执行的方法名
     * @param params     执行grovvy需要传入的参数
     * @param scriptType
     * @return
     * @desc 执行groovy脚本(需要指定方法名)
     */
    public static Object runScriptM(String scriptText, String funName, String scriptType, Object... params) {
        try {
            if (scriptType == null || scriptType.equals("")) {
                scriptType = "groovy";
            } else if (scriptType.equals("js") || scriptType.equals("javascript")) {
                // scriptType = "nashorn";
            }
            ScriptEngine engine = factory.getEngineByName(scriptType);
            //long start = System.currentTimeMillis();
            engine.eval(scriptText);//线程里首次执行，会较慢
            //System.out.println("执行groovy脚本："+(System.currentTimeMillis()-start));
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction(funName, params);
            return object;
        } catch (Exception e) {
            log.error("脚本类型:{},执行ScriptRun的runScript异常:{}", scriptType, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param scriptText 要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName    要执行的方法名
     * @param params     执行grovvy需要传入的参数
     * @param scriptType
     * @return
     * @desc 执行groovy脚本(需要指定方法名)
     */
    public static Object runScript(String scriptText, String funName, Object params, String scriptType) {
        try {
            if (scriptType == null || scriptType.equals("")) {
                scriptType = "groovy";
            }
            ScriptEngine engine = factory.getEngineByName(scriptType);
            //long start = System.currentTimeMillis();
            engine.eval(scriptText);//线程里首次执行，会较慢
            //System.out.println("执行groovy脚本："+(System.currentTimeMillis()-start));
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction(funName, params);
            return object;
        } catch (Exception e) {
            log.error("脚本类型:{},执行ScriptRun的runScript异常:{}", scriptType, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param scriptText 要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName    要执行的方法名
     * @param params     执行grovvy需要传入的参数
     * @param scriptType
     * @return
     * @desc 执行groovy脚本(需要指定方法名)
     */
    public static void runMain(String scriptText, String funName, Object[] params, String scriptType) {
        try {
            if (scriptType == null || scriptType.equals("")) {
                scriptType = "groovy";
            }
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName(scriptType);
            //long start = System.currentTimeMillis();
            engine.eval(scriptText);//线程里首次执行，会较慢
            //System.out.println("执行groovy脚本："+(System.currentTimeMillis()-start));
            Invocable inv = (Invocable) engine;
        } catch (Exception e) {
            log.error("脚本类型:{},执行ScriptRun的runScript异常:{}", scriptType, e);
            throw new RuntimeException(e);
        }
    }
}
