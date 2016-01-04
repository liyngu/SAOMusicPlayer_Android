package com.henu.smp.service;

import android.content.Context;
import android.util.Log;

import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.dao.LocalDao;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.entity.User;
import com.henu.smp.util.JSONUtil;

import java.util.List;

/**
 * Created by liyngu on 12/20/15.
 */
public class UserService {
    private LocalDao dao = new LocalDao();
    public void login(String username, String password) {

    }

    public void save(Menu menu, Context context) {
        dao.save(context, menu);
    }

    public void save(List<Song> songs, Context context) {
        dao.createSongList(context, songs);
    }

    public void create(User user) {
        Log.i("UserService", JSONUtil.parseToString(user));
    }

    public User getLocal(Context context) {
        dao.getSongs(context);
        return null;
    }

    public void getSongsByMenu(final BaseAsyncResult<List<Song>> result, final Menu menu, final Context context) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                List<Song> songs = dao.getSongsByMenuId(menu.getId(), context);
                result.sendData(songs);
            }
        });

    }

    public void getById(int id) {

    }

    public void loadMusicByLocal(final BaseAsyncResult<List<Song>> result, final Context context) {
        result.execute(new Runnable() {
            @Override
            public void run() {
                List<Song> songs = dao.getSongs(context);
                result.sendData(songs);
            }
        });
    }

    public User getLocalUser() {
        return null;
    }
}
