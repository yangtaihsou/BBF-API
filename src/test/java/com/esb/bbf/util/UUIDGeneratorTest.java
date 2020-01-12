package com.esb.bbf.util;

import java.util.UUID;

public class UUIDGeneratorTest {
    public UUIDGeneratorTest() {
    }
    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        //去掉“-”符号
        return s.replace("-","");
    }
    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] ss = new String[number];
        for(int i=0;i<number;i++){
            ss[i] = getUUID();
        }
        return ss;
    }
    public static void main(String[] args){
        String[] ss = getUUID(10);
        for(int i=0;i<ss.length;i++){
            System.out.println(ss[i]);
        }
    }

}
