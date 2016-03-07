package com.henu.smp.dao;

import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;

import java.util.List;

/**
 * Created by liyngu on 1/11/16.
 * 对菜单进行操作的接口
 * 一部分为存储到数据库中的方法，只存储歌曲列表的菜单
 * 另一部分为菜单结构信息的json字符串，暂时存储到本地以便于之后发送给服务器
 */
public interface MenuDao {
    /**
     * 只存储是音乐列表的菜单
     * @param menu
     */
    void save(Menu menu);

    /**
     * 存储json菜单结构数据到本地
     * @param menuTree
     */
    void saveAll(MenuTree menuTree);

    /**
     * 获得所有菜单
     * @return
     */
    List<Menu> getAll();

    /**
     * 获得所有歌曲列表菜单
     * @return
     */
    List<Menu> getAllSongList();

    /**
     * 只是删除歌曲某个列表而已
     * @param menuId
     */
    void delete(int menuId);

    /**
     * 删除所有的菜单信息，包括本地存储的json菜单
     */
    void deleteAll();
}
