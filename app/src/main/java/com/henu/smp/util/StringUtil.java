package com.henu.smp.util;

import com.henu.smp.Constants;

/**
 * Created by liyngu on 12/24/15.
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    public static String format(String str) {
        return str == null ? Constants.EMPTY_STRING : str.trim();
    }
}
