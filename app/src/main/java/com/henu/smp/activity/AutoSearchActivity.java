package com.henu.smp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.henu.smp.R;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.business.UserService;
import com.henu.smp.model.Song;

import java.io.File;
import java.util.List;

/**
 * Created by liyngu on 12/23/15.
 */
public class AutoSearchActivity extends BaseActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_search);
        mListView = (ListView) findViewById(R.id.listView);
        this.setAdapterData();

    }

    public void setAdapterData(){
        UserService service = new UserService();
        service.loadMusicByLocal(new BaseAsyncResult<List<Song>>() {
            @Override
            public void onSuccess(List<Song> result) {
                ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(AutoSearchActivity.this,
                        android.R.layout.simple_list_item_1, result);
                mListView.setAdapter(adapter);
            }
        }, this);
    }
}
