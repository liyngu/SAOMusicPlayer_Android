package com.henu.smp.dao.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.dao.UserDao;
import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.User;
import com.henu.smp.proxy.DaoProxyHandler;
import com.henu.smp.util.JSONUtil;
import com.henu.smp.util.StringUtil;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyngu on 1/8/16.
 */
public class UserDaoImpl extends DaoProxyHandler implements UserDao {
    public UserDaoImpl(Context context) {
        super(context);
    }

    @Override
    public void save(User user) {
        try {
            getDbUtils().save(user);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getLocal() {
        try {
            return getDbUtils().findFirst(Selector.from(User.class));
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveMenu(Menu menu) {
        try {
            getDbUtils().save(menu);
            Menu createdMenu = getDbUtils().findFirst(menu);
            menu.setId(createdMenu.getId());
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveMenuTree(MenuTree menuTree) {
        String menuTreeStr = JSONUtil.parseToString(menuTree.parseData());
        Menu menu = new Menu();
        menu.setMenus(menuTree.parseData());
        SharedPreferences sp = getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("menus", menuTreeStr);
        editor.apply();
    }

    @Override
    public List<Menu> getMenuTreeData() {
        SharedPreferences sp = getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_APPEND);
        String menuTreeStr = sp.getString("menus", Constants.EMPTY_STRING);
        if (StringUtil.isEmpty(menuTreeStr)) {
            return new ArrayList<>();
        }
        return JSONUtil.parseToList(menuTreeStr, Menu.class);
    }

    @Override
    public List<Menu> getMusicMenus() {
        try {
            return getDbUtils().findAll(Selector.from(Menu.class));
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        SharedPreferences sp = getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("menus");
        editor.apply();

        try {
            getDbUtils().dropTable(Menu.class);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
