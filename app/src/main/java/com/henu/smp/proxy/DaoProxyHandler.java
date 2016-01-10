package com.henu.smp.proxy;

import android.content.Context;
import android.util.Log;

import com.henu.smp.Constants;
import com.lidroid.xutils.DbUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by liyngu on 1/8/16.
 */
public class DaoProxyHandler implements InvocationHandler {
    private Context mContext = null;
    private DbUtils mDbUtils;

    protected DbUtils getDbUtils() {
        return mDbUtils;
    }

    protected Context getContext() {
        return mContext;
    }

    public DaoProxyHandler(Context context) {
        mContext = context;
    }

    public void before() {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(getContext().getApplicationContext());
        config.setDbName(Constants.DB_NAME);
        config.setDbVersion(1);
        mDbUtils = DbUtils.create(config);
    }

    protected void after() {
        mDbUtils.close();
        mDbUtils = null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        String[] limit = new String[]{"save", "delete", "get"};
        String methodName = method.getName();
        boolean isAccess = false;
        for (int i = 0; i < limit.length; i++) {
            if (methodName.startsWith(limit[i])) {
                isAccess = true;
                break;
            }
        }
        try {
            if (isAccess) {
                this.before();
            }
            result = method.invoke(this, args);
        } catch (Exception e) {
            Log.i("PROXY", e.getMessage());
        } finally {
            if (isAccess) {
                this.after();
            }
        }
        return result;
    }
}
