package com.henu.smp.service;

import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.User;

import java.util.List;

/**
 * Created by liyngu on 1/11/16.
 * 包含用户以及菜单的本地操作以及联网操作
 */
public interface UserService {
    /**
     * 保存一个歌曲列表，验证菜单是不是歌曲列表菜单
     * @param menu
     */
    void saveSongListMenu(Menu menu);

    /**
     * 获得所有的歌曲列表菜单
     * @return
     */
    List<Menu> getSongListMenus();

    /**
     * 保存所有的菜单信息到本地
     */
    void saveMenuTree(MenuTree menuTree);

    /**
     * 获得本地存储的用户，并且组装用户信息与菜单列表信息
     * @return
     */
    User getLocal();

    /**
     * 保存用户到本地，不保存菜单信息
     * @param user
     */
    void save(User user);

    /**
     * 删除本地数据
     */
    void deleteAll();
}
