package com.henu.smp.model;

/**
 * Created by liyngu on 12/7/15.
 */
public class SmpMenu {
    private int index;
    private String name;
    private String displayName;
    private SmpMenu subMenu;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubMenu(SmpMenu subMenu) {
        this.subMenu = subMenu;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public SmpMenu getSubMenu() {
        return subMenu;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }
}
