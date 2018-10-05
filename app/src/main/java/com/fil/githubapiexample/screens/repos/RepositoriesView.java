package com.fil.githubapiexample.screens.repos;

import android.view.View;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.base.BaseView;

import java.util.List;

public interface RepositoriesView extends BaseView {
    void onRepositoriesLoaded(List<Repository> repositories);

    void setNoRepositoryTextViewVisibility(int visibility);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateItem(Repository repository, int position);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void deleteItem(int position);
}
