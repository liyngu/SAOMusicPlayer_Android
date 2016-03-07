package com.henu.smp.activity;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.entity.Song;
import com.henu.smp.listener.SimpleAnimationListener;
import com.henu.smp.util.IntentUtil;
import com.henu.smp.util.StringUtil;
import com.henu.smp.widget.EmptyDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by hp on 2016/2/29.
 */
@ContentView(R.layout.activity_internet_search)
public class InternetSearchActivity extends BaseDialog {
    private PlayerService.PlayerBinder mPlayerBinder;
    private ServiceConnection mServiceConnection;
    private List<Song> mSongList;

    @ViewInject(R.id.content_input)
    private EditText mContentInput;

    @ViewInject(R.id.songs_list_view)
    private ListView mSongsListView;

    @ViewInject(R.id.result_dialog)
    private EmptyDialog mResultDialog;


    public void setAdapterData(String searchStr){
        if (mSongList != null) {
            return;
        }

        mMusicService.findByNet(new BaseAsyncResult<List<Song>>() {
            @Override
            public void onSuccess(List<Song> result) {
                if (result == null || result.size() == 0) {
                    return;
                }
                mSongList = result;
                BaseAdapter adapter = new ListViewAdapter(mSongList);
                mSongsListView.setAdapter(adapter);
            }
        }, searchStr);
    }

    @Override
    public void onBindService(IBinder binder) {
        mPlayerBinder = (PlayerService.PlayerBinder) binder;
    }

    @Override
    protected void okBtnOnclickListener(View v) {
        String text = mContentInput.getText().toString();
        if (StringUtil.isEmpty(text)) {
            return;
        }
        setAdapterData(text);

        final int offsetX = 300;
        Animation animation = new TranslateAnimation(0, -offsetX, 0, 0);
        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mResultDialog.show();
            }
        });
        getDialog().startAnimation(animation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    private class ListViewAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Song> mItems;

        public ListViewAdapter(List<Song> items) {
            mItems = items;
            mLayoutInflater = LayoutInflater.from(InternetSearchActivity.this);
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
