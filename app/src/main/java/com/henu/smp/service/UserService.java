package com.henu.smp.service;

import android.content.Context;
import android.util.Log;

import com.henu.smp.dao.UserDao;
import com.henu.smp.dao.impl.UserDaoImpl;
import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.User;
import com.henu.smp.proxy.DaoProxy;
import com.henu.smp.util.JSONUtil;

import java.util.List;

/**
 * Created by liyngu on 12/20/15.
 */
public class UserService {
    public void login(String username, String password) {

    }

    public void deleteLocal(Context context) {
        UserDao userDao = DaoProxy.getInstance(UserDaoImpl.class, context);
        userDao.deleteAll();
    }

    public void saveMenu(Menu menu, Context context) {
        UserDao userDao = DaoProxy.getInstance(UserDaoImpl.class, context);
        userDao.saveMenu(menu);
    }

    public List<Menu> getMusicMenus(Context context) {
        UserDao userDao = DaoProxy.getInstance(UserDaoImpl.class, context);
        return userDao.getMusicMenus();
    }

    public void saveMenuTree(MenuTree menuTree, Context context) {
        UserDao userDao = DaoProxy.getInstance(UserDaoImpl.class, context);
        userDao.saveMenuTree(menuTree);
    }

    public void save(User user, Context context) {
        UserDao userDao = DaoProxy.getInstance(UserDaoImpl.class, context);
        userDao.save(user);
    }

    public void create(User user) {
        Log.i("UserService", JSONUtil.parseToString(user));
    }

    public User getLocal(Context context) {
        UserDao userDao = DaoProxy.getInstance(UserDaoImpl.class, context);
        User user = userDao.getLocal();
        if (user != null) {
            user.setMenus(userDao.getMenuTreeData());
        }
        return user;
    }
}
