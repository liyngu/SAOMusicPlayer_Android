package com.henu.smp.voice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * Created by hp on 2016/2/25.
 */
public class VoiceDialog extends Activity {
    private static final String APP_ID = "";
    private Context context;
    private Toast mToast;
    //识别窗口
    private RecognizerDialog iatDialog;
    //识别对象
    private SpeechRecognizer iatRecognizer;
    //缓存，保存当前的引擎参数到下一次启动应用程序使用.
    private SharedPreferences mSharedPreferences;
    private RecognizerDialogListener recognizerDialogListener = null;

    public VoiceDialog(Context context, RecognizerDialogListener recognizerDialogListener) {
        this(context, APP_ID, recognizerDialogListener);
    }

    public VoiceDialog(Context context) {
        this(context, APP_ID, null);
    }

    public VoiceDialog(Context context,String APP_ID,RecognizerDialogListener recognizerDialogListener)
    {
        this.context = context;
        SpeechUser.getUser().login(context, null, null
                , "appid=" + APP_ID, listener);
        //初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
        iatDialog =new RecognizerDialog(context);
        //初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
        iatDialog =new RecognizerDialog(context);
        //初始化缓存对象.
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(),MODE_PRIVATE);
        this.recognizerDialogListener = recognizerDialogListener;
    }

    public void GetWordFromVoice()
    {
        boolean isShowDialog = mSharedPreferences.getBoolean("iat_show",true);
        if (isShowDialog) {
            //显示语音听写Dialog.
            showIatDialog();
        } else {
            if(null == iatRecognizer) {
                iatRecognizer=SpeechRecognizer.createRecognizer(this);
            }
            if(iatRecognizer.isListening()) {
                iatRecognizer.stopListening();
            } else {
            }
        }
    }


    private void showTip(String str)
    {
        if(!TextUtils.isEmpty(str))
        {
            mToast.setText(str);
            mToast.show();
        }
    }
    /**
     * 显示听写对话框.
     * @param
     */
    public void showIatDialog()
    {
        if(null == iatDialog) {
            //初始化听写Dialog
            iatDialog =new RecognizerDialog(context);
        }

        //获取引擎参数
        String engine = mSharedPreferences.getString(
                "iat_engine",
                "iat");

        //清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
        iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        //设置听写Dialog的引擎
        iatDialog.setParameter(SpeechConstant.DOMAIN, engine);
        //设置采样率参数，支持8K和16K
        String rate = mSharedPreferences.getString(
                "sf",
                "sf");
        if(rate.equals("rate8k"))
        {
            iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
        }
        else
        {
            iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
        }
        if(recognizerDialogListener == null)
        {
            return;
        }
        //显示听写对话框
        iatDialog.setListener(recognizerDialogListener);
        iatDialog.show();
    }

    /**
     * 用户登录回调监听器.
     */
    private SpeechListener listener = new SpeechListener()
    {

        @Override
        public void onData(byte[] arg0) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if(error != null) {
                System.out.println("user login success");
            }
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
        }
    };
}
