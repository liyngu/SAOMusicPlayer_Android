package com.henu.smp.dao.impl;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.henu.smp.base.BaseDao;
import com.henu.smp.dao.SongDao;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyngu on 1/7/16.
 */
public class SongDaoImpl extends BaseDao implements SongDao {
    
    @Override
    public void save(Song song) {
        try {
            getDbManager().saveOrUpdate(song);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Song song) {
        try {
            getDbManager().delete(song);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Song> songList) {
        try {
            for (Song song : songList) {
                if (!this.exist(song)) {
                    getDbManager().save(song);
                }
            }
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            getDbManager().dropTable(Song.class);
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll(int menuId) {
        try {
            getDbManager().delete(Menu.class, WhereBuilder.b("menu_id", "=", menuId));
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Song> getByMenuId(int menuId) {
        try {
            return getDbManager().selector(Song.class)
                    .where("menu_id", "=", menuId).findAll();
        } catch (DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean exist(Song song) throws DbException {
        return getDbManager().selector(Song.class)
                .where("song_id", "=", song.getSongId())
                .and("menu_id", "=", song.getMenuId()).findFirst() != null;
    }

    @Override
    public List<Song> findAll() {
        ContentResolver contentResolver = getContentResolver();
        List<Song> songs = new ArrayList<>();
        if (contentResolver == null) {
            return songs;
        }
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return songs;
        }

        int cursorCount = cursor.getCount();
        for (int i = 0; i < cursorCount; i++) {
            cursor.moveToNext();

            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            // 音乐总时间
            long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            // 文件大小
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            if (time < 60 * 1000 || isMusic == 0) {
                //continue;
            }
            // 文件id
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            // 文件标题，不含文件后缀
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            // 文件名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            // 文件路径
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

            // 新建歌曲
            Song song = new Song();
            song.setSongId(id);
            song.setName(name);
            song.setTitle(title);
            song.setPath(path);
            song.setSize(size);
            song.setTime(time);
            songs.add(song);
        }
        cursor.close();
        return songs;
    }
}
