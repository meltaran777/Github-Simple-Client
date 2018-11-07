package com.fil.github_client.base;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface ToolbarView extends MvpView {
    void setupTitle(String title);

    void setupTitle(@StringRes int resId);

    void enableBackButton();
}
