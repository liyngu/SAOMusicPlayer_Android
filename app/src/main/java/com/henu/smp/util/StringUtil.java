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
    public static String formatToTitle(String name) {
        if (StringUtil.isEmpty(name)) {
            return Constants.EMPTY_STRING;
        }
        StringBuilder title = new StringBuilder();
        title.append(name.substring(0, 1).toUpperCase());
        title.append(name.substring(1));
        if (name.endsWith("list")) {
            title.replace(title.indexOf("list"), title.length(), " List");
        }
        return title.toString();
    }
}
