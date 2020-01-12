package com.esb.bbf.api.script;

import com.esb.bbf.monitor.CatProfile;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;

@Service
public class ScriptInvoke {

    @CatProfile(type = "method_api")
    public Object invokeMethod(ScriptEngine engine,String methodName) throws Exception {
        Invocable inv = (Invocable) engine;
        Object object = inv.invokeFunction(methodName);
        return object;
    }
}
