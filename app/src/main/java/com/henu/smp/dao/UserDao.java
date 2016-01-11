package com.henu.smp.dao;

import com.henu.smp.entity.User;

/**
 * Created by liyngu on 12/20/15.
 * 存储用户信息以及个人本地设置到数据库
 */
public interface UserDao {
    /**
     * 保存一个用户到数据库
     * @param user
     */
    void save(User user);

    /**
     * 获得本地存储的用户
     * @return
     */
    User getLocal();

    /**
     * 删除本地存储的用户
     */
    void delete();
}
