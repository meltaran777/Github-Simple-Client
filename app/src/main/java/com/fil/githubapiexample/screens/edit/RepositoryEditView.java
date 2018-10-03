package com.fil.githubapiexample.screens.edit;

import android.graphics.Bitmap;

import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.screens.base.BaseView;

public interface RepositoryEditView extends BaseView {
    void showRepositoryName(String repositoryName);

    void showRepositoryDescription(String description);

    void onRepositorySaved(Repository newRepository);
}

