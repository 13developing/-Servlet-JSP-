package com.Forming.sys.utils;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

public class StringUtils {
    public static  Boolean isEmpty(String msg) {
        if (!"".equals(msg) && msg != null) {
            return false;
        }
        return true;
    }

    public static  Boolean isNotEmpty(String msg) {
        if (!"".equals(msg) && msg != null) {
            return true;
        }
        return false;
    }
}
