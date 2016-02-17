package com.henu.smp.util;

import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.listener.SimpleHttpCallBack;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.UnsupportedEncodingException;

/**
 * Created by liyngu on 1/3/16.
 */
public class HttpUtil {
    public static void doGet(String path, SimpleHttpCallBack<String> callBack) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        x.http().get(requestParams, callBack);
    }
    public static void doPost(String path, String data, SimpleHttpCallBack<String> callBack) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        requestParams.setRequestBody(HttpUtil.createRequestBody(data));
        x.http().post(requestParams, callBack);
    }
    public static void doPost(String path, String data) {
        HttpUtil.doPost(path, data, new SimpleHttpCallBack<String>());
    }
    public static void doPut(String path, String data) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        requestParams.addBodyParameter("user", data);
        x.http().request(HttpMethod.PUT, requestParams, new SimpleHttpCallBack<String>());
    }
    public static void doDelete(String path) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        x.http().request(HttpMethod.DELETE, requestParams, new SimpleHttpCallBack<String>());
    }
    private static RequestBody createRequestBody(String jsonStr) {
        RequestBody requestBody = null;
        try {
            requestBody = new StringBody(jsonStr, "UTF-8");
            requestBody.setContentType("application/json");
        } catch (Exception e) {
            Log.e("HTTP", e.getMessage());
        }
        return requestBody;
    }
}
