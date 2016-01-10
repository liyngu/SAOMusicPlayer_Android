package com.henu.smp.dao;

import com.henu.smp.dto.MenuTree;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.User;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by liyngu on 12/20/15.
 */
public interface UserDao {
    void save(User user);
    User getLocal();
    void saveMenu(Menu menu);
    void saveMenuTree(MenuTree menuTree);
    List<Menu> getMenuTreeData();
    List<Menu> getMusicMenus();
    void deleteAll();
}
