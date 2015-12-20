package com.henu.smp.business;

import android.util.Log;

import com.henu.smp.model.User;
import com.henu.smp.util.JSONUtil;

/**
 * Created by liyngu on 12/20/15.
 */
public class UserService {
    public void login(String username, String password) {

    }

    public void create(User user) {
        Log.i("UserService", JSONUtil.parseToString(user));
    }

    public User getLocal() {
        return null;
    }

    public void getById(int id) {

    }
}
