package com.fil.github_client.base;

import android.content.Intent;
import android.view.View;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface ScreenView extends MvpView, ToolbarView{
    int SNACK_DURATION = 1500;

    void showSnackbar(String message, int duration);

    void showSnackbar(String message, int duration, String actionText, View.OnClickListener listener);

    void hideKeyboard();

    void showProgress();

    void hideProgress();

    void setViewResult(int resultCode, Intent data);

    void hideView(int resultCode, Intent data);

    void hideView();
}

