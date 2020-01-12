package com.esb.bbf.jvmScript;

import com.alibaba.fastjson.JSON;
import com.esb.bbf.api.serverless.ServerlessSdk;
import com.esb.bbf.console.domain.JvmScriptInfo;
import com.esb.bbf.TestConfig;
import com.esb.bbf.api.script.JvmScriptRun;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.core.Assert;
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
public class ScriptRunTest {

    @Autowired
    private JvmScriptRun jvmScriptRun;
    @Autowired
    private ServerlessSdk sdk;

    /**
     * 测试js请求rpc对参数的属性是数组的情况支持
     * @throws IOException
     */
    @Test
    public void testJsArray() throws IOException {

        Map serverlessSdk = sdk.loadServerlessSdk();
        Map httpParaSdk = new HashMap();
        JvmScriptInfo jvmScriptInfo = new JvmScriptInfo();
        jvmScriptInfo.setScriptType("js");
        jvmScriptInfo.setScriptText(this.getScriptTex("testResponseParaList.js"));
        Object reponse = jvmScriptRun.runScriptExeMethod(jvmScriptInfo,
                httpParaSdk);
        log.info(JSON.toJSONString(reponse));
        Assert.isNotNull(reponse);

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
