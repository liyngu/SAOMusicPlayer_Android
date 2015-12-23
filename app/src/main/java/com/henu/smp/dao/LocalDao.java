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
        if(contentResolver == null){
            return songs;
        }
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return songs;
        }

        int cursorCount = cursor.getCount();
        Log.i("this", cursorCount+"");
        for (int i = 0; i < cursorCount; i++) {
            cursor.moveToNext();
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            long time=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            if (time < 60 * 1000 || isMusic == 0) {
                continue;
            }
            String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            Log.i("this", title + name);
        }
        cursor.close();
        return songs;
    }
}
