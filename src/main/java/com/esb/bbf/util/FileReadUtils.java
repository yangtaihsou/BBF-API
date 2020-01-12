package com.esb.bbf.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReadUtils {
    /**
     * 读取文件内容
     *
     * @param fileName
     * @param charset
     * @return
     */
    public static String readContext(String fileName, Charset charset) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Files.lines(Paths.get(fileName), charset).forEach(x -> {
            stringBuffer.append(x).append("\r\n");
        });
        return stringBuffer.toString();
    }

    /**
     * 将文本生成生成本地文件
     *
     * @param fileSrcPath
     * @param fileName
     * @param text
     */
    public static void writeSrcFile(String fileSrcPath, String fileName, String text, Charset charset) throws IOException {
        String path = fileSrcPath;
        if (!Files.isDirectory(Paths.get(path))) {
            Files.createDirectories(Paths.get(path));
        }
        Files.write(Paths.get(path + fileName), text.getBytes(charset.name()));
    }

    /**
     * 读取指定目录下的文件
     * 读取多个文件，也不耗时。见单元测试
     * @param dir
     * @return
     */
    public static File[] getDirFiles(String dir) {
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        return file.listFiles();
    }
}
