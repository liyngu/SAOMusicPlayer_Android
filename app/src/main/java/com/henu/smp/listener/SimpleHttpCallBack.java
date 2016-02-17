package com.henu.smp.listener;

import org.xutils.common.Callback;

/**
 * Created by hp on 2016/1/29.
 */
public class SimpleHttpCallBack<ResultType> implements Callback.CommonCallback<ResultType> {
    @Override
    public void onSuccess(ResultType resultType) {

    }

    @Override
    public void onError(Throwable throwable, boolean b) {

    }

    @Override
    public void onCancelled(CancelledException e) {

    }

    @Override
    public void onFinished() {

    }
}
