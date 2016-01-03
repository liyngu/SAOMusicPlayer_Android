package com.henu.smp.dao;

import com.henu.smp.entity.User;

import org.json.JSONObject;

/**
 * Created by liyngu on 12/20/15.
 */
public class UserDao {
    public void login(String username, String password) {

    }

    public void create(User user) {
        JSONObject jsonUser = new JSONObject();
        //jsonUser.put(user.getUsername(), user);
    }

    public void update(User user) {

    }

    public void syncData() {
        JSONObject jsonUser = this.differ("", "");
        // proxy.post(jsonUser);
    }

    private JSONObject differ(String oldFile, String newFile) {
        return null;
    }
}
