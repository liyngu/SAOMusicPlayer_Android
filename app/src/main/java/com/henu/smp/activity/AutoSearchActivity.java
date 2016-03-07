package com.henu.smp.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseAsyncResult;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.entity.Menu;
import com.henu.smp.entity.Song;
import com.henu.smp.listener.SimpleAnimationListener;
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
            finishActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Menu> menuList = mUserService.getSongListMenus();
        for (Menu menu : menuList) {
            if (Constants.HISTORY_MENU_ID == menu.getId()) {
                continue;
            }
            RectButton rb = new RectButton(this);
            rb.setData(menu);
            mChooseListMenu.addView(rb);
            rb.setOnClickListener(mChooseMenuListener);
        }

        mSongsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSong = mSongList.get(position);
                mSongsListView.setEnabled(false);
                view.setBackgroundColor(Color.YELLOW);
                final int offsetX = mChooseListMenu.getWidth();
                Animation animation = new TranslateAnimation(0, -offsetX, 0, 0);
                animation.setFillAfter(true);
                animation.setDuration(100);
                animation.setAnimationListener(new SimpleAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mChooseListMenu.setLocationByView(getDialog());
                        mChooseListMenu.setX(mChooseListMenu.getX() - offsetX);
                        mChooseListMenu.show();
                    }
                });
                getDialog().startAnimation(animation);
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
//                ArrayAdapter<Song> adapter = new ArrayAdapter<>(AutoSearchActivity.this,
//                        android.R.layout.simple_list_item_1, result);
                BaseAdapter adapter = new ListViewAdapter(mSongList);
                mSongsListView.setAdapter(adapter);
            }
        });
    }

    private class ListViewAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Song> mItems;

        public ListViewAdapter(List<Song> items) {
            mItems = items;
            mLayoutInflater = LayoutInflater.from(AutoSearchActivity.this);
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
