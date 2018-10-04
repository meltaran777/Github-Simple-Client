package com.fil.githubapiexample.screens.edit;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.base.BaseView;

public interface RepositoryEditView extends BaseView {
    void showRepositoryName(String repositoryName);

    void showRepositoryDescription(String description);

    void onRepositorySaved(Repository newRepository);
}

