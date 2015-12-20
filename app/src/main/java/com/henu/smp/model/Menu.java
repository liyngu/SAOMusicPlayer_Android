package com.henu.smp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyngu on 12/7/15.
 * JSON基本形式
 * {"user":{"id":10001,"username":"test","password":"123","menu":[{"index":0,"name":"user","displayname":"","menu":[{"index":0,"name":"userinfo","displayname":"用户信息","menu":[]},{"index":1,"name":"userhistory","displayname":"播放历史","menu":[]}]},{"index":1,"name":"list","displayname":"","menu":[{"index":0,"name":"collection","displayname":"我的收藏","menu":[]}]}]}}
 */
public class Menu {
    private int index;
    private String name;

    @SerializedName("displayname")
    private String text;
    private List<Menu> menus;


    public void setName(String name) {
        this.name = name;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
