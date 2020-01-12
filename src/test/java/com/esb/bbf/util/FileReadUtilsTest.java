package com.esb.bbf.util;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class FileReadUtilsTest {
    @Test
    public void testGetDirFiles(){
        String targetSrcDir = "/opt/bbf/";
        long startTime = System.currentTimeMillis();

        File[] files = FileReadUtils.getDirFiles(targetSrcDir);
        for(File file:files){
            if(file.isDirectory()){
                continue;
            }
           // log.info("文件名:{}",file.getName());
            long fileLastTime = file.lastModified();
            DateTime dateTime = new DateTime();

            //时间可以调整
            long lasttime = dateTime.plusMinutes(-1).getMillis();
            if (fileLastTime < lasttime) {
                continue;
            }
        }

        long endTime = System.currentTimeMillis() - startTime;
        log.info("getDirFiles time = {}",endTime);
    }

    @Test
    public void copy() throws IOException {
        File source =   new File("/opt/bbf/1");
        for(int index=3;index<10000;index++) {
            File desc = new File("/opt/bbf/"+index);
            Files.copy(source.toPath(), desc.toPath());
        }
    }
}
