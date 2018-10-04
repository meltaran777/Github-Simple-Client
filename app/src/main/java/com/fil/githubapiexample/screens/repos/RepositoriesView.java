package com.fil.githubapiexample.screens.repos;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.base.BaseView;

import java.util.List;

public interface RepositoriesView extends BaseView {
    void onRepositoriesLoaded(List<Repository> repositories);

    @StateStrategyType(SkipStrategy.class)
    void updateItem(Repository repository, int position);

    @StateStrategyType(SkipStrategy.class)
    void deleteItem(int position);
}
