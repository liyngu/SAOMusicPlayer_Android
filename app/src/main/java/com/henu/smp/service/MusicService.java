package com.henu.smp.service;

import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.entity.Song;

import java.util.List;

/**
 * Created by liyngu on 1/11/16.
 * 包括音乐查找之类以及音乐本地搜寻及存储的业务逻辑
 */
public interface MusicService {

    /**
     * 查找全部本地歌曲
     */
    void findAllLocal(BaseAsyncResult<List<Song>> result);

    /**
     * 查找网络上的歌曲
     */
    void findByNet(BaseAsyncResult<List<Song>> result, String name);

    /**
     * 通过菜单id获得此歌曲列表的歌曲
     * @param menuId
     * @return
     */
    List<Song> getByMenuId(int menuId);

    /**
     * 保存一首歌曲到某个菜单
     * @param song
     */
    void save(Song song, int menuId);

    /**
     * 保存全部歌曲到某个菜单
     * @param songList
     * @param menuId
     */
    void saveAll(List<Song> songList, int menuId);

    /**
     * 删除某个菜单中的全部歌曲
     * @param menuId
     */
    void delete(int menuId);

    /**
     * 删除全部保存在本地的歌曲
     */
    void deleteAll();

    boolean isCollectedSong(int songId);

    void changeCollectedStatus(int songId);
}
