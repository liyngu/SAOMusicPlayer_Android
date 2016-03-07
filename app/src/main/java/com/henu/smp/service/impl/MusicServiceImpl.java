package com.henu.smp.service.impl;

import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.dao.SongDao;
import com.henu.smp.dao.impl.SongDaoImpl;
import com.henu.smp.entity.Song;
import com.henu.smp.listener.SimpleHttpCallBack;
import com.henu.smp.service.MusicService;
import com.henu.smp.util.HttpUtil;
import com.henu.smp.util.JSONUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyngu on 12/23/15.
 */
public class MusicServiceImpl implements MusicService {
    private SongDao mSongDao = new SongDaoImpl();

    @Override
    public void findAllLocal(final BaseAsyncResult<List<Song>> result) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                List<Song> songs = mSongDao.findAll();
                result.sendData(songs);
            }
        });
    }

    @Override
    public void findByNet(final BaseAsyncResult<List<Song>> result, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        HttpUtil.doGet("music/search", new SimpleHttpCallBack<String>() {
            @Override
            public void onSuccess(String jsonStr) {
                Log.i("json", jsonStr);
                final List<Song> songList = JSONUtil.parseToList(jsonStr, Song.class);
                for (Song song: songList) {
                    String path = Constants.MUSIC_HOST + song.getPath();
                    Log.i("song", path);
                    song.setPath(path);
                }
                result.execute(new Runnable() {
                    @Override
                    public void run() {
                        result.sendData(songList);
                    }
                });
            }
        }, params);
    }

    @Override
    public List<Song> getByMenuId(int menuId) {
        return mSongDao.getByMenuId(menuId);
    }

    @Override
    public void save(Song song, int menuId) {
        song.setMenuId(menuId);
        if (Constants.HISTORY_MENU_ID == menuId) {
            mSongDao.saveHistorySong(song);
        } else {
            mSongDao.save(song);
        }
    }

    @Override
    public void saveAll(List<Song> songList, int menuId) {
        for (Song song : songList) {
            this.save(song, menuId);
        }
    }

    @Override
    public void delete(int menuId) {
        mSongDao.deleteAll(menuId);
    }

    @Override
    public void deleteAll() {
        mSongDao.deleteAll();
    }

    @Override
    public boolean isCollectedSong(int songId) {
        List<Song> songList = mSongDao.getByMenuId(1);
        for(Song song : songList) {
            if (song.getSongId() == songId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void changeCollectedStatus(int songId) {
        List<Song> songList = mSongDao.getByMenuId(1);
        Song selectedSong = null;
        for(Song song : songList) {
            if (song.getSongId() == songId) {
                selectedSong = song;
                break;
            }
        }
        if (selectedSong != null) {
            mSongDao.delete(selectedSong);
        } else {
            selectedSong = mSongDao.getBySongId(songId);
            selectedSong.setMenuId(1);
            mSongDao.save(selectedSong);
        }
    }
}
