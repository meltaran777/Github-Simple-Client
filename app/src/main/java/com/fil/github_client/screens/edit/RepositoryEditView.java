package com.fil.github_client.screens.edit;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.github_client.base.ResultView;
import com.fil.github_client.base.ScreenView;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface RepositoryEditView extends ScreenView, ResultView {
    void showRepositoryName(String repositoryName);

    void showRepositoryDescription(String description);
}

