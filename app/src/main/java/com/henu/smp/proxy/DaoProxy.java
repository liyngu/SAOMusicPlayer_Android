package com.henu.smp.proxy;

import android.content.Context;
import android.util.Log;

import com.henu.smp.dao.impl.SongDaoImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by liyngu on 1/7/16.
 */
public class DaoProxy {

    public static <T> T getInstance(Class<T> cls, Context context) {
        Object result = null;
        try {
            DaoProxyHandler handler = (DaoProxyHandler) cls.getConstructor(Context.class).newInstance(context);
            result = Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), handler);
        } catch (Exception e) {
            Log.i("PROXY", e.getMessage());
        }
        return (T) result;
    }
}
