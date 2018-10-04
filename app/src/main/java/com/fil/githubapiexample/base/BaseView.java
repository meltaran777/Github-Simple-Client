package com.fil.githubapiexample.base;

import android.view.View;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface BaseView extends MvpView, Router, IBaseActivity {
    int SNACK_DURATION = 1500;

    void showSnackbar(String message, int duration);

    void showSnackbar(String message, int duration, String actionText, View.OnClickListener listener);

    void hideKeyboard();

    void showProgress();

    void hideProgress();
}

