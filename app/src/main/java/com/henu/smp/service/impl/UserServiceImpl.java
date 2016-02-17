package com.henu.smp.service.impl;

import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.dao.MenuDao;
import com.henu.smp.dao.UserDao;
import com.henu.smp.dao.impl.MenuDaoImpl;
import com.henu.smp.dao.impl.UserDaoImpl;
import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.User;
import com.henu.smp.listener.SimpleHttpCallBack;
import com.henu.smp.service.UserService;
import com.henu.smp.util.HttpUtil;
import com.henu.smp.util.JSONUtil;

import java.util.List;

/**
 * Created by liyngu on 12/20/15.
 */
public class UserServiceImpl implements UserService {
    private UserDao mUserDao = new UserDaoImpl();
    private MenuDao mMenuDao = new MenuDaoImpl();

    @Override
    public void saveSongListMenu(Menu menu) {
        if (Constants.CREATE_TYPE_MUSIC_LIST == menu.getType()) {
            mMenuDao.save(menu);
        }
    }

    @Override
    public List<Menu> getSongListMenus() {
        return mMenuDao.getAllSongList();
    }

    @Override
    public void saveMenuTree(final MenuTree menuTree) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                mMenuDao.saveAll(menuTree);
            }
        });
    }

    @Override
    public User getLocal() {
        User user = mUserDao.getLocal();
        if (user != null) {
            user.setMenus(mMenuDao.getAll());
        }
        return user;
    }

    @Override
    public void save(final User user) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.save(user);
                if (user.getId() != Constants.EMPTY_INTEGER) {
                    HttpUtil.doPut("user/" + user.getId(), JSONUtil.parseToString(user));
                }
                HttpUtil.doPost("user", JSONUtil.parseToString(user));
            }
        });
    }

    @Override
    public void saveAndMergeMenuTree(final User user, final MenuTree menuTree) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.save(user);
                user.setMenus(menuTree.parseData());
                mMenuDao.saveAll(menuTree);
            }
        });
    }

    @Override
    public void deleteAll() {
        mUserDao.delete();
        mMenuDao.deleteAll();
    }
}
