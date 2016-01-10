package com.henu.smp.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;


/**
 * Created by liyngu on 12/20/15.
 */
public class JSONUtil {
    private static final Gson GSON = new Gson();
    public static String parseToString(Object object) {
        return GSON.toJson(object);
    }
    public static <T> T parseToObject(String jsonStr, Class<T> cls) {
        return GSON.fromJson(jsonStr, cls);
    }
    public static <T> List<T> parseToList(String jsonStr, Class<T> cls) {
        return GSON.fromJson(jsonStr, new ListOfSomething<>(cls));
    }

    public static boolean isArray(String jsonString) {
        if(jsonString.charAt(0) == '[' && jsonString.charAt(jsonString.length() - 1) == ']') {
            return true;
        }
        return false;
    }

    private static class ListOfSomething<T> implements ParameterizedType {
        private Class<T> mContentCls;

        public ListOfSomething(Class<T> cls) {
            mContentCls = cls;
        }
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {mContentCls};
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

        @Override
        public Type getRawType() {
            return List.class;
        }
    }
}
