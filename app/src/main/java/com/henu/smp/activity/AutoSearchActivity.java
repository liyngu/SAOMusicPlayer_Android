package com.henu.smp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henu.smp.R;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.service.UserService;
import com.henu.smp.entity.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
public class AutoSearchActivity extends BaseDialog {
    private ListView mListView;
    private List<Song> mSongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_search);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Song> songs = new ArrayList<>();
                Song song = mSongList.get(position);
                song.setMenuId(1);
                songs.add(song);
                userService.save(songs, AutoSearchActivity.this);
            }
        });
        this.setAdapterData();

    }

    public void setAdapterData(){
        UserService service = new UserService();
        service.loadMusicByLocal(new BaseAsyncResult<List<Song>>() {
            @Override
            public void onSuccess(List<Song> result) {
                mSongList = result;
                ArrayAdapter<Song> adapter = new ArrayAdapter<>(AutoSearchActivity.this,
                        android.R.layout.simple_list_item_1, result);
                mListView.setAdapter(adapter);
            }
        }, this);
    }
}
