package com.henu.smp.activity;

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
import com.henu.smp.base.BaseDialog;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.util.IntentUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
@ContentView(R.layout.activity_show_songs)
public class ShowSongsActivity extends BaseDialog {
    private PlayerService.PlayerBinder mPlayerBinder;
    private ServiceConnection mServiceConnection;
    private List<Song> mSongList;

    @ViewInject(R.id.listView)
    private ListView mListView;

    @Override
    public void onBindService(IBinder binder) {
        mPlayerBinder = (PlayerService.PlayerBinder) binder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getBundle();
        int menuId = bundle.getInt(Constants.SHOW_SONGS_MENU_ID);
        this.setAdapterData(menuId);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPlayerBinder.start(position, mSongList);
            }
        });

        mServiceConnection = IntentUtil.bindService(this, PlayerService.class);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    public void setAdapterData(int menuId){
        mSongList = mMusicService.getByMenuId(menuId);
        if (mSongList == null || mSongList.size() == 0) {
            return;
        }
        ArrayAdapter<Song> adapter = new ArrayAdapter<>(ShowSongsActivity.this,
                android.R.layout.simple_list_item_1, mSongList);
        mListView.setAdapter(adapter);
    }
}
