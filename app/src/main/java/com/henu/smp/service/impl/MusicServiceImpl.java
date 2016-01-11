package com.henu.smp.service.impl;

import android.content.Context;

import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.dao.SongDao;
import com.henu.smp.dao.impl.SongDaoImpl;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;

import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
public class MusicServiceImpl {
    private SongDao mSongDao = new SongDaoImpl();

    public void getAllLocalSongs(final BaseAsyncResult<List<Song>> result, final Context context) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                List<Song> songs = mSongDao.findAll();
                result.sendData(songs);
            }
        });
    }

    public void getLocalSongs(final BaseAsyncResult<List<Song>> result, final Menu menu, final Context context) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                List<Song> songs = mSongDao.getByMenu(menu);
                result.sendData(songs);
            }
        });
    }

    public void deleteLocalSongs(final Menu menu, final Context context) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                mSongDao.deleteAll(menu);
            }
        });
    }

    public void saveSongs(final List<Song> songList, final Context context) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                mSongDao.saveAll(songList);
            }
        });
    }

    public void deleteAll(final List<Menu> menuList, final Context context) {
        for (Menu menu : menuList) {
            this.deleteLocalSongs(menu, context);
        }
    }
    public void deleteAll(final Context context) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                mSongDao.deleteAll();
            }
        });
    }
}
