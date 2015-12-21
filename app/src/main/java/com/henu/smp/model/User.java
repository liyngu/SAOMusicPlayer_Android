package com.henu.smp.model;


import java.util.List;

/**
 * Created by liyngu on 12/20/15.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private List<Menu> menus;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
