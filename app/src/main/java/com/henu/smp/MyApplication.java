package com.henu.smp;

import android.app.Application;

import com.henu.smp.entity.User;
import com.henu.smp.service.MusicService;
import com.henu.smp.service.UserService;
import com.henu.smp.service.impl.MusicServiceImpl;
import com.henu.smp.service.impl.UserServiceImpl;

import org.xutils.x;

/**
 * Created by liyngu on 1/11/16.
 */
public class MyApplication extends Application {
    private UserService mUserService;
    private MusicService mMusicService;
    private User mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);

        mUserService = new UserServiceImpl();
        mMusicService = new MusicServiceImpl();
        mUser = mUserService.getLocal();
    }

    public MusicService getMusicService() {
        return mMusicService;
    }

    public UserService getUserService() {
        return mUserService;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
