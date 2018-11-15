package com.fil.github_client.screens.repositories;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.base.ScreenView;

import java.util.List;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface RepositoriesView extends ScreenView {
    void showRepositories(List<GitRepository> repositories);

    void setNoRepositoryTextViewVisibility(int visibility);

    @StateStrategyType(SkipStrategy.class)
    void showRepositoryDetailsView(GitRepository gitRepository);

    void updateItem(GitRepository gitRepository, int position);

    void deleteItem(int position);
}
