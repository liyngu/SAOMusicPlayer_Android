package com.henu.smp.dao.impl;

import com.henu.smp.base.BaseDao;
import com.henu.smp.dao.UserDao;
import com.henu.smp.entity.User;

import org.xutils.ex.DbException;

/**
 * Created by liyngu on 1/8/16.
 */
public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public void save(User user) {
        try {
            getDbManager().saveOrUpdate(user);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getLocal() {
        try {
            return getDbManager().findFirst(User.class);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            getDbManager().dropTable(User.class);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
