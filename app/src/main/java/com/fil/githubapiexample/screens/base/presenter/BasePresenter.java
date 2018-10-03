package com.fil.githubapiexample.screens.base.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.arellomobile.mvp.MvpPresenter;
import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.helper.NetworkHelper;
import com.fil.githubapiexample.screens.base.BaseView;


public abstract class BasePresenter<T extends BaseView> extends MvpPresenter<T> {

    protected Context context;

    protected AppHelper appHelper;

    protected NetworkHelper networkHelper;

    public BasePresenter(Context context, AppHelper appHelper) {
        this.context = context.getApplicationContext();
        this.appHelper = appHelper;
        this.networkHelper = appHelper.getNetworkHelper();
    }

    protected void runOnBackgroundThread(final Runnable runnable) {
        new Thread(runnable).start();
    }

    protected void runOnUiThread(final Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
