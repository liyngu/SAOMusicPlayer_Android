package com.henu.smp.util;

import com.google.gson.Gson;


/**
 * Created by liyngu on 12/20/15.
 */
public class JSONUtil {
    private static final Gson GSON = new Gson();
    public static String parseToString(Object object) {
        return GSON.toJson(object);
    }

    public static boolean isArray(String jsonString) {
        if(jsonString.charAt(0) == '[' && jsonString.charAt(jsonString.length() - 1) == ']') {
            return true;
        }
        return false;
    }
}
