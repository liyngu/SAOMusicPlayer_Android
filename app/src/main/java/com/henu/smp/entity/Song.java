package com.henu.smp.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by liyngu on 12/23/15.
 */
@Table(name = "song")
public class Song {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "song_id")
    private int songId;

    @Column(name = "menu_id")
    private int menuId;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private long size;

    @Column(name = "time")
    private long time;

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getSongId() {
        return songId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getId() {
        return id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSize() {
        return size;
    }

    public long getTime() {
        return time;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return title;
    }
}
