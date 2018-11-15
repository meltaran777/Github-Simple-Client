package com.fil.github_client.base.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.fil.github_client.base.ScreenView;
import com.fil.github_client.network.ErrorHandler;

public abstract class BasePresenter<V extends ScreenView>  extends MvpPresenter<V> implements ErrorHandler {
    @Override
    public void handleError(int code, String errorMsg) {
        getViewState().hideProgress();
        getViewState().hideKeyboard();
        getViewState().showSnackbar(errorMsg, ScreenView.SNACK_DURATION);
    }
}
