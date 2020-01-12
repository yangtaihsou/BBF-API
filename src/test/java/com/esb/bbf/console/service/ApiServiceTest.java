package com.esb.bbf.console.service;

import com.esb.bbf.TestConfig;
import com.esb.bbf.api.service.ApiService;
import com.esb.bbf.jvmScript.ScriptRun;
import com.esb.bbf.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.ApiConfig.class)
@WebAppConfiguration
@Slf4j
public class ApiServiceTest {
    @Autowired
    private ApiService apiService;

    @Test
    public void test() throws IOException {
        String accountId = String.valueOf(422L);
        Map scripParaMap = new HashMap();
        scripParaMap.put("accountId", accountId);
        scripParaMap.put("log", log);
        scripParaMap.put("rpc", apiService);
        scripParaMap.put("studentId", accountId);
        scripParaMap.put("button", true);
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("studentId", accountId);
        paramap.put("button", true);
        paramap.put("accountId", accountId);
        String scripteText2 = getScriptTex("Test2Http.py");
        Object[] params = new Object[2];
        params[0] = paramap;
        Map<String, Object> m = new HashMap<>();
        scripteText2 = "def exe(p,m){" + scripteText2 + "}";
        Object result = ScriptRun.runScriptM(scripteText2, "exe", "groovy", scripParaMap, m);
        scripParaMap.remove("rpc");
        log.info("name={},para={},result={}",
                "Test2Http",
                GsonUtils.toJson(scripParaMap),
                GsonUtils.toJson(result));
    }

    @Test
    public void testJs() throws IOException {
        String accountId = String.valueOf(422L);
        Map scripParaMap = new HashMap();
        scripParaMap.put("accountId", accountId);
        scripParaMap.put("log", log);
        scripParaMap.put("rpc", apiService);
        scripParaMap.put("studentId", accountId);
        scripParaMap.put("button", true);
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("studentId", accountId);
        paramap.put("button", true);
        paramap.put("accountId", accountId);
        String scripteText2 = getScriptTex("Js2Http.py");
        Object result = ScriptRun.runScript(scripteText2, "exe", scripParaMap, "js");
        scripParaMap.remove("rpc");
        log.info("name={},para={},result={}",
                "Js2Http",
                GsonUtils.toJson(scripParaMap),
                GsonUtils.toJson(result));
    }

    @Test
    public void testPython() throws IOException {
        String accountId = String.valueOf(422L);
        Map scripParaMap = new HashMap();
        scripParaMap.put("accountId", accountId);
        scripParaMap.put("log", log);
        scripParaMap.put("rpc", apiService);
        scripParaMap.put("studentId", accountId);
        scripParaMap.put("button", true);
        Map<String, Object> paramap = new HashMap<>();
        paramap.put("studentId", accountId);
        paramap.put("button", true);
        paramap.put("accountId", accountId);
        String scripteText2 = getScriptTex("Python2Http.py");
        Object result = ScriptRun.runScript(scripteText2, "exe", scripParaMap, "python");
        scripParaMap.remove("rpc");
        log.info("name={},para={},result={}",
                "Js2Http",
                GsonUtils.toJson(scripParaMap),
                GsonUtils.toJson(result));
    }

    public String getScriptTex(String scriptName) throws IOException {
        File file = new File(this.getClass().getResource("/" + scriptName).getPath());
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();
        String txt = new String(filecontent, encoding);
        return txt;
    }
}
