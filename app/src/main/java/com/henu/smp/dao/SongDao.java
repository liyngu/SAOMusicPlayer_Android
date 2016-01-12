package com.henu.smp.dao;

import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;

import java.util.List;

/**
 * Created by liyngu on 1/7/16.
 * 除了获取全部歌曲的方法之外，其余全为对本地数据库进行的操作
 */
public interface SongDao {
    /**
     * 保存一首属于某个列表的歌曲，歌曲列表id在上层进行设置
     * @param song
     */
    void save(Song song);

    /**
     * 删除某个列表中的一首歌曲
     * @param song
     */
    void delete(Song song);

    /**
     * 保存选中的所有歌曲到某个列表
     * @param songList
     */
    void saveAll(List<Song> songList);

    /**
     * 删除所有存储的歌曲信息
     */
    void deleteAll();

    /**
     * 删除某个菜单存储的歌曲信息
     * @param menu
     */
    void deleteAll(Menu menu);

    /**
     * 通过一个菜单id去删除这个菜单的歌曲信息
     * @param menuId
     */
    void deleteAll(int menuId);

    /**
     * 通过一个菜单去获得这个菜单里的歌曲列表
     * @param menu
     * @return
     */
    List<Song> getByMenu(Menu menu);

    /**
     * 通过一个菜单id去获得这个菜单里的歌曲列表
     * @param menuId
     * @return
     */
    List<Song> getByMenuId(int menuId);

    /**
     * 这个方法为非数据库操作方法，作用为寻找所有的本地歌曲
     * @return
     */
    List<Song> findAll();
}
