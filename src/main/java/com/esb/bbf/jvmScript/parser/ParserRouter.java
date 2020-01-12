package com.esb.bbf.jvmScript.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * User:
 * Date18-6-19
 * Time: 下午3:35
 */
public class ParserRouter {
    static Map<String,CaseParser> parserMap = new HashMap<String, CaseParser>();
    static {
        parserMap.put("groovy",new GroovyCaseParser());
        parserMap.put("js",new JsCaseParser());
        parserMap.put("javascript",new JsCaseParser());
    }
    public static CaseParser getParser(String parser){
        return parserMap.get(parser);
    }
}
