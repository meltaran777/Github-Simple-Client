package com.fil.githubapiexample.base.presenter;

import android.content.Context;
import android.content.Intent;

import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.BaseView;
import com.fil.githubapiexample.util.Const;

public abstract class BaseReposPresenter<T extends BaseView> extends BaseApiPresenter<T> {

    protected Repository repository;

    public BaseReposPresenter(Context context,AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context,appHelper, githubApiHelper);
    }

    public void setRepository(Intent intent) {
        repository = getReposItemFromIntent(intent);
    }

    private Repository getReposItemFromIntent(Intent intent) {
        return (Repository) intent.getParcelableExtra(Const.REPOSITORY_EXTRA_KEY);
    }

    public Repository getRepository() {
        return repository;
    }
}
