package com.henu.smp.service;

import android.content.Context;

import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.dao.SongDao;
import com.henu.smp.dao.impl.SongDaoImpl;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.proxy.DaoProxy;

import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
public class MusicService {

    public void getAllLocalSongs(final BaseAsyncResult<List<Song>> result, final Context context) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                SongDao songDao = DaoProxy.getInstance(SongDaoImpl.class, context);
                List<Song> songs = songDao.localGetAll();
                result.sendData(songs);
            }
        });
    }

    public void getLocalSongs(final BaseAsyncResult<List<Song>> result, final Menu menu, final Context context) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                SongDao songDao = DaoProxy.getInstance(SongDaoImpl.class, context);
                List<Song> songs = songDao.getByMenu(menu);
                result.sendData(songs);
            }
        });
    }

    public void deleteLocalSongs(final Menu menu, final Context context) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                SongDao songDao = DaoProxy.getInstance(SongDaoImpl.class, context);
                songDao.deleteAll(menu);
            }
        });
    }

    public void saveSongs(final List<Song> songList, final Context context) {
        final BaseAsyncResult<String> result = new BaseAsyncResult<>();
        result.execute(new Runnable() {
            @Override
            public void run() {
                SongDao songDao = DaoProxy.getInstance(SongDaoImpl.class, context);
                songDao.saveAll(songList);
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
                SongDao songDao = DaoProxy.getInstance(SongDaoImpl.class, context);
                songDao.deleteAll();
            }
        });
    }
}
