package com.esb.bbf.jvmScript.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsCaseParser implements CaseParser {

    private static final Logger log = LoggerFactory.getLogger(JsCaseParser.class);
    /**
     *
     * @param caseString
     * @return
     */
    public   Map<String, String> parseCaseMethod(String caseString) {
        Map<String, String> caseMethodMap = new HashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(new StringReader(caseString));
            String lineTxt = null;
            StringBuilder methodContext = new StringBuilder();
            String beforeMethodName = "";
            while ((lineTxt = br.readLine()) != null) {
                if (isMainFunction(lineTxt)) {
                    caseMethodMap.put(beforeMethodName, methodContext.toString());
                    beforeMethodName = "main";
                    methodContext = new StringBuilder();
                    methodContext.append(lineTxt);
                }
                String pattern = "\\s*+function .*";//以0个或者多个空格开始，以function+空格结束//示例: "   function   queryPersonMap(paraObj) {"
                boolean isMatch = Pattern.matches(pattern, lineTxt);
                if (isMatch) {
                    String extractPattern = "\\s?function\\s(.*?)\\(";//提取function 和( 之间的字符串。以def为开头，但function前面或者后面可以允许有多个空格
                    Pattern extractRegex = Pattern.compile(extractPattern);
                    Matcher m = extractRegex.matcher(lineTxt);
                    if (m.find()) {//读取到后面的方法，就把内容算作前面方法
                        caseMethodMap.put(beforeMethodName, methodContext.toString());
                        beforeMethodName = m.group(1).replace(" ", "");//去掉方法名字的前后所有空格
                        methodContext = new StringBuilder();
                        methodContext.append(lineTxt);
                    }
                } else {
                    methodContext.append(lineTxt);
                }
            }
            caseMethodMap.put(beforeMethodName, methodContext.toString());//最后一个方法
            br.close();
        } catch (Exception e) {
            System.out.println("read errors :" + e);
            String msg = "read errors :" + e.getMessage();
            log.error(msg);
            throw new RuntimeException(e);
        }
        return caseMethodMap;
    }


    /**
     * 是否包含main函数
     *
     * @return
     */
    private   boolean isMainFunction(String content) {
        String pattern = ".*\\s+main\\s*\\(.*";//main前面至少有一个空格，后面至少有一个空格或者紧贴着(
        boolean isMatch = Pattern.matches(pattern, content);
        if (!isMatch) return false;
        String pattern2 = ".*static.*main.*";//必须同时包含static main

        isMatch = Pattern.matches(pattern2, content);
        return isMatch;
    }
}
