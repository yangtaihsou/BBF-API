package com.esb.bbf.util;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User:
 * Date18-6-13
 * Time: 上午11:42
 */
public class StringTest {
    @Test
    public void wrap(){
        String text = "clouds;monokai;twilight;xcode;clouds_midnight;vibrant_ink;tomorrow_night_eighties;tomorrow_night_bright;tomorrow_night_blue;tomorrow_night;tomorrow;textmate;terminal;sqlserver;solarized_light;solarized_dark;solarized_dark;pastel_on_dark;mono_industrial;merbivore_soft;merbivore;kuroir;kr_theme;katzenmilch;iplastic;idle_fingers;gruvbox;gob;github;eclipse;dreamweaver;dracula;dawn;crimson_editor;cobalt;chrome;chaos;ambiance";
        String[] textArray = text.split(";");
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(" [");
        for(String mode:textArray){
            stringBuilder.append(" { text: '"+mode+"', value: '"+mode+"' },");
        }

        stringBuilder.append(" ]");
        System.out.println("------------");
        System.out.println(stringBuilder.toString());
    }
    public static String PATTERN_L2DOMAIN = "\\w*\\.\\w*:";
    public static String PATTERN_IP = "(\\d*\\.){3}\\d*";
    String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    Pattern pat = Pattern.compile(rexp);
    @Test
    public void test() throws MalformedURLException {
        java.net.URL url = new java.net.URL("http://blog.csdn.net/zhujianlin1990");
        String host = url.getHost();// 获取主机名 
        System.out.println("host:" + host);// 结果 blog.csdn.net
        url = new java.net.URL("http://user/zhujianlin1990");
          host = url.getHost();// 获取服务名
        System.out.println("host:" + host);//

        url = new java.net.URL("http://191u8.123.123.1:880/zhujianlin1990");
        host = url.getHost();// 获取服务名
        System.out.println("host:" + host);//
        Matcher mat = pat.matcher(host);
        boolean ipAddress = mat.find();
        System.out.println("host:" + host+"，是否是ip="+ipAddress);//
    }
}
