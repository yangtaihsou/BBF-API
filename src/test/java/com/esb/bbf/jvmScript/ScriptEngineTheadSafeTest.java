package com.esb.bbf.jvmScript;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Slf4j
public class ScriptEngineTheadSafeTest {

    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("js");

    /**
     * 如果多线程公用一个ScriptEngine。
     * 在线程并发时候，eval(编译代码)和invokeFunction之间有延迟的话，
     * 存在编译后的代码(两个线程eval编译的代码内容不一样) 被其他线程覆盖(如果方法一样的话)。
     * 另外engine.put的变量，是线程不安全的(eval编译的代码内容一样，但在代码里修改变量值，是修改共享变量)
     * 所以是不安全的。
     *
     * @throws InterruptedException
     */
    @Test
    public void testForOneEngineMultiThread() throws InterruptedException {
        new Thread(() -> {
            Double result = add();
            Assert.assertEquals(result.intValue(), 34);
        }).start();
        new Thread(() -> {
            Double result = sub();
            Assert.assertEquals(result.intValue(), 34);
        }).start();
        Thread.sleep(5000L);
    }

    private Double add() {
        long start = System.currentTimeMillis();
        // ScriptEngine engine = factory.getEngineByName("js");
        log.info("初始化engine耗费时间:{}", (System.currentTimeMillis() - start));
        engine.put("a", 4);
        engine.put("b", 6);

        try {
            log.info("编译加法代码开始");
            engine.eval("function add(){return a+b;}");
            log.info("编译加法代码完毕");
            Thread.sleep(3000L);
            log.info("执行加法开始");
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction("add");
            log.info("执行加法结束,result={}", object);
            return (Double) object;
        } catch (Exception e) {
            log.info("执行加法报错:{}", e.getMessage());
        }
        return null;
    }

    private Double sub() {

        long start = System.currentTimeMillis();
        // ScriptEngine engine = factory.getEngineByName("js");
        log.info("初始化engine耗费时间:{}", (System.currentTimeMillis() - start));
        try {
            //这里时间延至，是为了覆盖上个线程的同样变量
            Thread.sleep(1000L);
        } catch (Exception e) {

        }
        engine.put("a", 40);
        engine.put("b", 6);
        try {
            Thread.sleep(1000L);
            log.info("编译减法代码开始");
            //实际是减法。里面对engine的共享变量进行重新设置，看看下一个线程的变量是否线程线程安全。
            engine.eval("function add(){var result = a-b;a=100;return result;}");
            log.info("编译减法代码完毕");
            log.info("执行减法开始");
            Invocable inv = (Invocable) engine;
            Object object = inv.invokeFunction("add");
            log.info("执行减法结束,result={}", object);
            return (Double) object;
        } catch (Exception e) {
            log.info("执行减法报错:{}", e.getMessage());
        }
        return null;
    }
}
