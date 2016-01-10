package com.henu.smp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.util.IntentUtil;
import com.henu.smp.util.StringUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by liyngu on 12/23/15.
 */
public class CreateListActivity extends BaseDialog {

    @ViewInject(R.id.content_input)
    private EditText contentInput;

    @ViewInject(R.id.create_operation_menu)
    private BaseMenu createOperationMenu;

    @ViewInject(R.id.create_menu_list_btn)
    private BaseButton createMenuListBtn;

    @ViewInject(R.id.create_music_list_btn)
    private BaseButton createMusicListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        setOkButtonOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = contentInput.getText().toString();
                if (StringUtil.isEmpty(text)) {
                    return;
                }
                createOperationMenu.show();
            }
        });
        createMenuListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        createMusicListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_CREATE_LIST);
                bundle.putString(Constants.CREATE_LIST_NAME, contentInput.getText().toString());
                IntentUtil.sendBroadcast(CreateListActivity.this, bundle);
                finish();
            }
        });

    }
}
