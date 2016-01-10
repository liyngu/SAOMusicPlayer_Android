package com.henu.smp.base;

import android.os.Handler;
import android.os.Message;

/**
 * Created by liyngu on 12/23/15.
 */
public class BaseAsyncResult<T> {
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            onSuccess(mData);
        }
    };
    private T mData;

    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }

    public void sendData(T data) {
        mData = data;
        mHandler.post(mRunnable);
    }

    public void onSuccess(T result) {

    }

    //    public BaseAsyncResult() {
//        mHandler = new SafeHandler<T>(this);
//    }
//    private static class SafeHandler<T> extends Handler {
//        private BaseAsyncResult<T> mAsyncResult;
//
//        public SafeHandler(BaseAsyncResult<T> asyncResult) {
//            mAsyncResult = asyncResult;
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg != null && msg.obj != null) {
//                mAsyncResult.onSuccess((T) msg.obj);
//            }
//        }
//    }
}

