package com.henu.smp.dao.impl;

import android.content.SharedPreferences;

import com.henu.smp.Constants;
import com.henu.smp.base.BaseDao;
import com.henu.smp.dao.MenuDao;
import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;
import com.henu.smp.util.JSONUtil;
import com.henu.smp.util.StringUtil;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by liyngu on 1/11/16.
 */
public class MenuDaoImpl extends BaseDao implements MenuDao {

    @Override
    public void save(Menu menu) {
        try {
            getDbManager().saveBindingId(menu);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveAll(MenuTree menuTree) {
        String menuTreeStr = JSONUtil.parseToString(menuTree.parseData());
        SharedPreferences.Editor editor = getSpEditor();
        editor.putString(Constants.SP_ITEM_MENUS, menuTreeStr);
        editor.apply();
    }

    @Override
    public List<Menu> getAll() {
        SharedPreferences sp = getSharedPreferences();
        String menuTreeStr = sp.getString(Constants.SP_ITEM_MENUS, Constants.EMPTY_STRING);
        if (StringUtil.isEmpty(menuTreeStr)) {
            return null;
        }
        return JSONUtil.parseToList(menuTreeStr, Menu.class);
    }

    @Override
    public List<Menu> getAllSongList() {
        try {
            return getDbManager().findAll(Menu.class);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Menu menu) {
        try {
            getDbManager().delete(menu);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        SharedPreferences.Editor editor = getSpEditor();
        editor.remove(Constants.SP_ITEM_MENUS);
        editor.apply();
        try {
            getDbManager().dropTable(Menu.class);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
