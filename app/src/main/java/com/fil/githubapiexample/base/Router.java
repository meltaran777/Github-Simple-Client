package com.fil.githubapiexample.base;

import android.content.Intent;

import com.fil.githubapiexample.model.Repository;

public interface Router {
    int REPOSITORY_ACTIVITY_REQUEST_CODE = 0;
    int DETAILS_REPOSITORY_ACTIVITY_REQUEST_CODE = 1;
    int EDIT_REPOSITORY_ACTIVITY_REQUEST_CODE = 2;

    int DATA_CHANGED_RESULT_CODE = 7;

    void startRepositoriesActivity(String login);

    void startRepositoryDetailsActivity(Repository repository);

    void startRepositoryEditActivity(Repository repository);

    void setResult(int resultCode, Intent data);

    void finishActivity(int resultCode, Intent data);

    void finishActivity();
}
