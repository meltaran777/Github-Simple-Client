package com.fil.github_client.screens.login;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.github_client.base.ScreenView;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface LoginView extends ScreenView {
    void showRepositoriesView(String login);
}

