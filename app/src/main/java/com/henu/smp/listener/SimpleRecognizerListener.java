package com.henu.smp.listener;

import com.henu.smp.util.JSONUtil;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * Created by hp on 2016/2/25.
 */
public class SimpleRecognizerListener implements RecognizerDialogListener {
    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
    }

    @Override
    public void onError(SpeechError speechError) {
        int errorCoder = speechError.getErrorCode();
        switch (errorCoder) {
            case 10118:
                System.out.println("user don't speak anything");
                break;
            case 10204:
                System.out.println("can't connect to internet");
                break;
            default:
                break;
        }
    }
}
