package com.esb.bbf.jvmScript;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ScriptEngineTheadSafe002Test {

    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("js");

    /**
     * 测试执行同一段代码的多线程，变量如果才能安全。需要将变量通过invokeFunction传输才可以
     *
     * @throws InterruptedException
     */
    @Test
    public void testForOneEngineMultiThread() throws InterruptedException {

        engine.put("a", 4);
        engine.put("b", 6);
        new Thread(() -> {
            t1(1);
        }).start();
        new Thread(() -> {
            t2(20);
        }).start();
        Thread.sleep(5000L);
    }

    private Double t1(int num) {
        long start = System.currentTimeMillis();
        log.info("初始化engine耗费时间:{}", (System.currentTimeMillis() - start));
        Map<String,Object> self = new HashMap<>();
        self.put("num",num);
        try {
            log.info("编译002代码开始");
            engine.eval("function add(s){  s.num=s.num+2; return a+b;}");
            Thread.sleep(2000);
            log.info("编译加法代码完毕");
            log.info("执行加法开始");
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction("add",self);
            log.info("t1 self.num={}", self.get("num"));
            log.info("执行加法结束,result={}", object);
            return (Double) object;
        } catch (Exception e) {
            log.info("执行加法报错:{}", e.getMessage());
        }
        return null;
    }
    private Double t2(int num) {
        long start = System.currentTimeMillis();
        log.info("初始化engine耗费时间:{}", (System.currentTimeMillis() - start));
        Map<String,Object> self = new HashMap<>();
        self.put("num",num);
        try {
            engine.eval("function add(s){  s.num=s.num+2; return a+b;}");
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction("add",self);
            log.info("t2 self.num={}", self.get("num"));
            Thread.sleep(3000);
            object = inv.invokeFunction("add",self);
            log.info("t2 self.num={}", self.get("num"));
            return (Double) object;
        } catch (Exception e) {
            log.info("执行加法报错:{}", e.getMessage());
        }
        return null;
    }

}
