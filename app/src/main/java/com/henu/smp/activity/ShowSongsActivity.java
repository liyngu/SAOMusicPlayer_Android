package com.henu.smp.activity;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @ViewInject(R.id.songs_list_view)
    private ListView mSongsListView;

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

        mSongsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPlayerBinder.start(position, mSongList);
                finish();
            }
        });

        mServiceConnection = IntentUtil.bindService(this, PlayerService.class);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    public void setAdapterData(int menuId) {
        mSongList = mMusicService.getByMenuId(menuId);
        if (mSongList == null || mSongList.size() == 0) {
            return;
        }
//        ArrayAdapter<Song> adapter = new ArrayAdapter<>(ShowSongsActivity.this,
//                android.R.layout.simple_list_item_1, mSongList);
        ListViewAdapter adapter = new ListViewAdapter(mSongList);
        mSongsListView.setAdapter(adapter);
    }

    private class ListViewAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Song> mItems;

        public ListViewAdapter(List<Song> items) {
            mItems = items;
            mLayoutInflater = LayoutInflater.from(ShowSongsActivity.this);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item;
            if (convertView == null) {
                item = new Item();
                convertView = mLayoutInflater.inflate(R.layout.auto_search_list_item, null);
                item.contentTxt = (TextView) convertView.findViewById(R.id.content_txt);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();
            }

            item.contentTxt.setText(mItems.get(position).getTitle());
            return convertView;
        }

        private class Item {
            public TextView contentTxt;
        }
    }
}
