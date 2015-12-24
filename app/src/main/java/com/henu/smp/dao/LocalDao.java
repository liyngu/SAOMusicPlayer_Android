package com.henu.smp.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.henu.smp.model.Song;

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
                continue;
            }
            // 文件标题，不含文件后缀
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            // 文件名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            // 文件路径
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

            // 新建歌曲
            Song song = new Song();
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
