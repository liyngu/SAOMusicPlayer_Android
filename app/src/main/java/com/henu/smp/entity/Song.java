package com.henu.smp.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

/**
 * Created by liyngu on 12/23/15.
 */
@Table(name = "song")
public class Song {

    @Unique
    @Id(column = "id")
    private int id;

    @Column(column = "song_id")
    private int songId;

    @Column(column = "menu_id")
    private int menuId;

    @Column(column = "name")
    private String name;

    @Column(column = "title")
    private String title;

    @Column(column = "path")
    private String path;

    @Column(column = "size")
    private long size;

    @Column(column = "time")
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
