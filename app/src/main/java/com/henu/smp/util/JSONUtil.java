package com.henu.smp.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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

    /**
     * 听写结果的Json格式解析
     * @param json
     * @return
     */
    public static String parseIatResult(String json) {
        if(TextUtils.isEmpty(json))
            return "";

        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 听写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * 识别结果的Json格式解析
     * @param json
     * @return
     */
    public static String parseGrammarResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                for(int j = 0; j < items.length(); j++)
                {
                    JSONObject obj = items.getJSONObject(j);
                    if(obj.getString("w").contains("nomatch"))
                    {
                        ret.append("没有匹配结果.");
                        return ret.toString();
                    }
                    ret.append("【结果】" + obj.getString("w"));
                    ret.append("【置信度】" + obj.getInt("sc"));
                    ret.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.append("没有匹配结果.");
        }
        return ret.toString();
    }

    /**
     * 语义结果的Json格式解析
     * @param json
     * @return
     */
    public static String parseUnderstandResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            ret.append("【应答码】" + joResult.getString("rc") + "\n");
            ret.append("【转写结果】" + joResult.getString("text") + "\n");
            ret.append("【服务名称】" + joResult.getString("service") + "\n");
            ret.append("【操作名称】" + joResult.getString("operation") + "\n");
            ret.append("【完整结果】" + json);
        } catch (Exception e) {
            e.printStackTrace();
            ret.append("没有匹配结果.");
        }
        return ret.toString();
    }
}
