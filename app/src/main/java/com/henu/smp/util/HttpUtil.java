package com.henu.smp.util;

import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.listener.SimpleHttpCallBack;

import org.xutils.common.util.LogUtil;
import org.xutils.db.table.ColumnUtils;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.util.Map;
import java.util.Set;


/**
 * Created by liyngu on 1/3/16.
 */
public class HttpUtil {
    public static void doGet(String path, SimpleHttpCallBack<String> callBack) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        x.http().get(requestParams, callBack);
    }
    public static void doGet(String path, SimpleHttpCallBack<String> callBack, Map<String, String> params) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        Set<String> keySet = params.keySet();
        for (String key : keySet){
            requestParams.addQueryStringParameter(key, params.get(key));
        }
        x.http().get(requestParams, callBack);
    }
    public static void doPost(String path, String data, SimpleHttpCallBack<String> callBack) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        requestParams.addBodyParameter("android", data);
        requestParams.setAsJsonContent(true);
        x.http().post(requestParams, callBack);
    }
    public static void doPost(String path, String data) {
        HttpUtil.doPost(path, data, new SimpleHttpCallBack<String>() {
            @Override
            public void onSuccess(String s) {
            }
        });
    }
    public static void doPut(String path, String data) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        requestParams.addBodyParameter("android", data);
        requestParams.setAsJsonContent(true);
        x.http().request(HttpMethod.PUT, requestParams, new SimpleHttpCallBack<String>() {
            @Override
            public void onSuccess(String s) {
            }
        });
    }
    public static void doDelete(String path) {
        RequestParams requestParams = new RequestParams(Constants.SERVICE_HOST + path);
        x.http().request(HttpMethod.DELETE, requestParams, new SimpleHttpCallBack<String>());
    }
}
