package com.fil.github_client.base;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface ResultView extends MvpView {
    void setViewResult(int resultCode, Intent data);
}
