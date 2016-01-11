package com.henu.smp.base;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import com.henu.smp.Constants;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by liyngu on 1/11/16.
 */
public abstract class BaseDao {
    private DbManager.DaoConfig mDaoConfig = new DbManager.DaoConfig()
            .setDbName(Constants.DB_NAME).setDbVersion(1);
    private DbManager mDbManager = x.getDb(mDaoConfig);
    private SharedPreferences mSharedPreferences = x.app().getApplicationContext()
            .getSharedPreferences(Constants.SP_NAME, Context.MODE_APPEND);
    private ContentResolver mContentResolver = x.app().getApplicationContext()
            .getContentResolver();

    protected DbManager getDbManager() {
        return mDbManager;
    }

    protected SharedPreferences.Editor getSpEditor() {
        return mSharedPreferences.edit();
    }

    protected SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    protected ContentResolver getContentResolver() {
        return mContentResolver;
    }
}
