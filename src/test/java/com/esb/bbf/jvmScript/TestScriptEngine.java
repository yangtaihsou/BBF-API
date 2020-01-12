package com.esb.bbf.jvmScript;

import com.esb.bbf.api.serverless.ServerlessSdk;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.Compilable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestScriptEngine {
    @Test
    public void testAll() {
        ScriptEngineManager manager = new ScriptEngineManager();

        // Get the list of all available engines
        List<ScriptEngineFactory> list = manager.getEngineFactories();

        // Print the details of each engine
        for (ScriptEngineFactory f : list) {
            System.out.println(f.toString());
            System.out.println("Engine Name:" + f.getEngineName());
            System.out.println("Engine Version:" + f.getEngineVersion());
            System.out.println("Language Name:" + f.getLanguageName());
            System.out.println("Language Version:" + f.getLanguageVersion());
            System.out.println("Engine Short Names:" + f.getNames());
            System.out.println("Mime Types:" + f.getMimeTypes());
            //https://blogs.oracle.com/nashorn/nashorn-multithreading-and-mt-safety
            System.out.println("是否支持多线程:" + f.getParameter("THREADING"));
            System.out.println("===============");
        }
    }

    @Test
    public void testNewEngine() {
        long start = System.currentTimeMillis();
        ScriptEngineManager factory = new ScriptEngineManager();
        log.info("factory初始化时间：{}", (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ScriptEngine scriptEngine = factory.getEngineByName("js");
        log.info("js Engine初始化时间：{}", (System.currentTimeMillis() - start));
        System.out.println(scriptEngine.toString());
        ScriptEngineManager factory1 = new ScriptEngineManager();
        start = System.currentTimeMillis();
        ScriptEngine scriptEngine1 = factory1.getEngineByName("js");
        log.info("js Engine初始化时间：{}", (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ScriptEngine scriptEngine001 = factory1.getEngineByName("js");
        log.info("js Engine初始化时间：{}", (System.currentTimeMillis() - start));
        System.out.println(scriptEngine1.toString());
        System.out.println(scriptEngine001.toString());
    }

    @Test
    public void testScriptEngine() {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine scriptEngine = factory.getEngineByName("js");
        System.out.println("js=" + scriptEngine);
        if (scriptEngine instanceof Compilable) {
            System.out.println("js Script compilation is supported.");
        } else {
            System.out.println("Script compilation is not supported.");
        }

        scriptEngine = factory.getEngineByName("JavaScript");
        System.out.println("JavaScript=" + scriptEngine);

        if (scriptEngine instanceof Compilable) {
            System.out.println("JavaScript Script compilation is supported.");
        } else {
            System.out.println("Script compilation is not supported.");
        }
        scriptEngine = factory.getEngineByName("Groovy");
        System.out.println("Groovy=" + scriptEngine);
        if (scriptEngine instanceof Compilable) {
            System.out.println("Groovy Script compilation is supported.");
        } else {
            System.out.println("Script compilation is not supported.");
        }
        scriptEngine = factory.getEngineByName("groovy");
        System.out.println("groovy=" + scriptEngine);
        scriptEngine = factory.getEngineByName("jython");
        System.out.println("jython=" + scriptEngine);

        if (scriptEngine instanceof Compilable) {
            System.out.println("jython Script compilation is supported.");
        } else {
            System.out.println("Script compilation is not supported.");
        }
        scriptEngine = factory.getEngineByName("python");
        System.out.println("python=" + scriptEngine);
        scriptEngine = factory.getEngineByName("jruby");
        System.out.println("jruby=" + scriptEngine);
        scriptEngine = factory.getEngineByName("ruby");
        System.out.println("ruby=" + scriptEngine);
        scriptEngine = factory.getEngineByName("scala");
        System.out.println("scala=" + scriptEngine);
    }

    @Test
    public void test() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("a", 4);
        engine.put("b", 6);
        try {
            Object maxNum = engine.eval("function max_num(a,b){return (a>b)?a:b;}max_num(a,b);");
            System.out.println("max_num:" + maxNum);
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction("max_num");
            System.out.println("max_num:" + object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test001() {
        String text = "function exe(){var list = new Array();\n" +
                "list.push('a');\n" +
                "list.push('b');\n" +
                "list.push('c');\n" +
                "list.push('d');\n" +
                "//将数组转换字符串打印\n" +
                "$p.log.info('-----list={}',$p.json.string(list))\n" +
                "\n" +
                "var listStr = '[\"a\",\"b\",\"c\",\"d\"]';\n" +
                "//将字符串转换为map\n" +
                "var strToList = $p.json.array(listStr);\n" +
                "$p.log.info('-----strToList,index0={}',strToList[0])\n" +
                "$p.log.info('-----strToList,index,get(0)={}',strToList.get(0))\n" +
                "var mapStr = '{\"name\":\"jim\"}'\n" +
                "var strToMap = $p.json.map(mapStr);\n" +
                "$p.log.info('-----strToMap,name={}',strToMap.name)\n" +
                "var response={}\n" +
                "response.listStr = $p.json.string(list)\n" +
                "response.strToList = strToList\n" +
                "response.strToMap = strToMap\n" +
                "return response}";
        ServerlessSdk sdk = new ServerlessSdk();
        Map serverlessSdk = sdk.loadServerlessSdk();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("$p", serverlessSdk);
        engine.put("p", serverlessSdk);
        try {
            Object response = engine.eval(text);
            System.out.println("eval response:" + response);
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction("exe");
            System.out.println("invokeFunction response:" + object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
