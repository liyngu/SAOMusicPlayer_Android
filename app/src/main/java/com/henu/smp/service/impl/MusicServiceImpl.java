package com.henu.smp.service.impl;

import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.dao.SongDao;
import com.henu.smp.dao.impl.SongDaoImpl;
import com.henu.smp.entity.Song;
import com.henu.smp.service.MusicService;

import java.util.List;

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
    public List<Song> getByMenuId(int menuId) {
        return mSongDao.getByMenuId(menuId);
    }

    @Override
    public void save(Song song, int menuId) {
        song.setMenuId(menuId);
        mSongDao.save(song);
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
}
