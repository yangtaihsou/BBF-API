package com.esb.bbf.jvmScript.parser;

import java.util.Map;

/**
 * User:
 * Date18-6-13
 * Time: 下午4:15
 */
public interface CaseParser {
    Map<String, String> parseCaseMethod(String caseString);
}
