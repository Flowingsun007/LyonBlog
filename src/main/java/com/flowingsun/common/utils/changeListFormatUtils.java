package com.flowingsun.common.utils;

public class changeListFormatUtils {

    public static Integer[] str2intList(String[] strList) throws NumberFormatException{
        Integer[] intList = new Integer[strList.length];
        for(Integer i=0;i<strList.length;i++){
            intList[i] = Integer.parseInt(strList[i]);
        }
        return intList;
    }
}
