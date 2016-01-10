package com.henu.smp.dao;

import android.content.Context;

import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.Collection;
import java.util.List;

/**
 * Created by liyngu on 1/7/16.
 */
public interface SongDao {
    void save(Song song);
    void delete(Song song);
    void saveAll(List<Song> songList);
    void deleteAll();
    void deleteAll(Menu menu);
    List<Song> localGetAll();
    List<Song> getByMenu(Menu menu);
}
