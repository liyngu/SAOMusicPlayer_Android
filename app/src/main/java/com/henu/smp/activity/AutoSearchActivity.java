package com.henu.smp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henu.smp.R;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.widget.RectButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
@ContentView(R.layout.activity_auto_search)
public class AutoSearchActivity extends BaseDialog {
    private List<Song> mSongList;
    private Song mSelectedSong;

    @ViewInject(R.id.songs_list_view)
    private ListView mSongsListView;

    @ViewInject(R.id.choose_list_menu)
    private BaseMenu mChooseListMenu;

    private View.OnClickListener mChooseMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BaseButton btn = (BaseButton) v;
            mMusicService.save(mSelectedSong, btn.getMenuId());
            AutoSearchActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Menu> menuList = mUserService.getSongListMenus();
        for (Menu menu : menuList) {
            RectButton rb = new RectButton(this);
            rb.setData(menu);
            mChooseListMenu.addView(rb);
            rb.setOnClickListener(mChooseMenuListener);
        }

        mSongsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSong = mSongList.get(position);
                mChooseListMenu.show();
            }
        });
        this.setAdapterData();

    }

    public void setAdapterData(){
        if (mSongList != null) {
            return;
        }
        mMusicService.findAllLocal(new BaseAsyncResult<List<Song>>() {
            @Override
            public void onSuccess(List<Song> result) {
                if (result == null || result.size() == 0 ){
                    return;
                }
                mSongList = result;
                ArrayAdapter<Song> adapter = new ArrayAdapter<>(AutoSearchActivity.this,
                        android.R.layout.simple_list_item_1, result);
                mSongsListView.setAdapter(adapter);
            }
        });
    }
}
