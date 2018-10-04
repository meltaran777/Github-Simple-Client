package com.fil.githubapiexample.screens.base.presenter;

import android.content.Context;

import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.model.User;
import com.fil.githubapiexample.rest.helper.GitHubHelperCallback;
import com.fil.githubapiexample.rest.helper.GitHubHelperErrorCallback;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.screens.base.BaseView;

import java.util.List;

;

public abstract class BaseApiPresenter<T extends BaseView> extends BasePresenter<T>
        implements GitHubHelperCallback, GitHubHelperErrorCallback {

    protected GithubApiHelper githubApiHelper;

    public BaseApiPresenter(Context context,AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context,appHelper);
        this.githubApiHelper = githubApiHelper;

        this.githubApiHelper.setGitHubHelperCallback(this);
        this.githubApiHelper.setGitHubHelperErrorCallback(this);
    }

    @Override
    public void onLogin(User user, String authEncode) {

    }

    @Override
    public void onRepositoriesLoaded(List<Repository> repositories) {

    }

    @Override
    public void onRepoDeleted() {

    }

    @Override
    public void onRepositoryEdited(Repository newRepository) {

    }

    @Override
    public void onError(int errorType, String errorMsg) {
        getViewState().hideProgress();
        getViewState().hideKeyboard();
        getViewState().showSnackbar(errorMsg, BaseView.SNACK_DURATION);
    }
}
