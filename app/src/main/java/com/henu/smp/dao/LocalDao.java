package com.henu.smp.dao;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.util.DbUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
public class LocalDao {
    public List<Song> getSongs(Context context) {
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
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

    public void createSongList(Context context, List<Song> songList) {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context.getApplicationContext());
        config.setDbName("SAOMusicPlayer");
        config.setDbVersion(1);
        DbUtils db = DbUtils.create(config);
        try {
            //db.dropTable(Song.class);
            for (Song song : songList) {
                if (!this.songExists(song, db)) {
                    db.save(song);
                }
            }
        } catch (DbException e) {
            Log.i("LOCAL DAO", e.getMessage());
        }
        db.close();
    }

    public void save(Context context, Menu menu) {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context.getApplicationContext());
        config.setDbName("SAOMusicPlayer");
        config.setDbVersion(1);
        DbUtils db = DbUtils.create(config);
        try {
            //db.dropTable(Menu.class);
            db.save(menu);
        } catch (DbException e) {
            Log.i("LOCAL DAO", e.getMessage());
        }
        db.close();
    }

    public List<Song> getSongsByMenuId(int menuId, Context context) {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context.getApplicationContext());
        config.setDbName("SAOMusicPlayer");
        config.setDbVersion(1);
        DbUtils db = DbUtils.create(config);
        List<Song> songList = new ArrayList<>();
        try {
            songList = db.findAll(Selector.from(Song.class).where("menu_id", "=", menuId));
        } catch (DbException e) {
            Log.i("LOCAL DAO", e.getMessage());
        }
        db.close();
        return songList;
    }

    public boolean songExists(Song song, DbUtils db) throws DbException {
        return db.findFirst(Selector.from(Song.class)
                .where("song_id", "=", song.getSongId())
                .and("menu_id", "=", song.getMenuId())) != null;
    }
}
