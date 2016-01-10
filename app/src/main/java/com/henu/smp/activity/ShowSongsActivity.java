package com.henu.smp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.service.UserService;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
public class ShowSongsActivity extends BaseDialog {
    @ViewInject(R.id.listView)
    private ListView mListView;
    private List<Song> mSongList;
    private PlayerService.PlayerBinder mPlayerBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerBinder = (PlayerService.PlayerBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);
        Bundle bundle = getBundle();
        int menuId = bundle.getInt(Constants.SHOW_SONGS_MENU_ID);
        Menu menu = new Menu();
        menu.setId(menuId);
        this.setAdapterData(menu);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPlayerBinder.start(position, mSongList);
            }
        });

        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    public void setAdapterData(Menu menu){
        mMusicService.getLocalSongs(new BaseAsyncResult<List<Song>>() {
            @Override
            public void onSuccess(List<Song> result) {
                if (result == null || result.size() == 0) {
                    return;
                }
                mSongList = result;
                ArrayAdapter<Song> adapter = new ArrayAdapter<>(ShowSongsActivity.this,
                        android.R.layout.simple_list_item_1, result);
                mListView.setAdapter(adapter);
            }
        }, menu, this);
    }
}
