package com.henu.smp.entity;

/**
 * Created by liyngu on 12/23/15.
 */
public class Song {
    private String name;
    private String title;
    private String path;
    private long size;
    private long time;

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
