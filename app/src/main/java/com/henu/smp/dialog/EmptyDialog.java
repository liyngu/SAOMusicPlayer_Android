package com.henu.smp.dialog;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.henu.smp.R;
import com.henu.smp.base.BaseDialog;

/**
 * Created by liyngu on 12/22/15.
 */
public class EmptyDialog extends BaseDialog {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.dialog_empty);
    }
}
