package com.fil.githubapiexample.rest.helper;

public interface GitHubHelperErrorCallback {

    int REQUEST_FAILED_ERROR  = 0;
    int LOGIN_ERROR           = 1;
    int USER_REPOS_LOAD_ERROR = 2;
    int SAVE_REPOSITORY_ERROR = 3;

    void onError(int errorType, String errorMsg);
}
