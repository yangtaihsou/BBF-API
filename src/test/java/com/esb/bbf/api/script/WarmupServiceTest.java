package com.esb.bbf.api.script;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.List;
@Slf4j
public class WarmupServiceTest {
    @Test
    public void testGetScriptcodeFiles(){
        WarmupService warmupService = new WarmupService();
        //本次测试，目录下有个1万文件文件
        String targetSrcDir = "/opt/bbf/";
        List<File> files = warmupService.getScriptcodeFiles(targetSrcDir,-600,100);
        for(File file:files){
            log.info("file name={}",file.getName());
        }
    }
}
