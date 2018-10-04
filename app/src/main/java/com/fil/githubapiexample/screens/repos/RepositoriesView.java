package com.fil.githubapiexample.screens.repos;

import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.screens.base.BaseView;

import java.util.List;

public interface RepositoriesView extends BaseView {
    void showRepositories(List<Repository> repositories);

    void updateItem(Repository repository, int position);

    void deleteItem(int position);
}
