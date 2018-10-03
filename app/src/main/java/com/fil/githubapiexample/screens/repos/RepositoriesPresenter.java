package com.fil.githubapiexample.screens.repos;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.arellomobile.mvp.InjectViewState;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.screens.base.presenter.BaseReposPresenter;
import com.fil.githubapiexample.screens.base.BaseView;
import com.fil.githubapiexample.adapter.repository.ReposItemInteractionListener;
import com.fil.githubapiexample.util.Const;

import java.util.ArrayList;
import java.util.List;

import static com.fil.githubapiexample.screens.base.BaseView.SNACK_DURATION;

@InjectViewState
public class RepositoriesPresenter extends BaseReposPresenter<RepositoriesView> implements ReposItemInteractionListener {

    private List<Repository> data;

    private String login;
    private int    openItemPosition;

    public RepositoriesPresenter(Context context, AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context, appHelper, githubApiHelper);
        data = new ArrayList<>();
    }

    public void updateUi(Intent data) {
        setRepository(data);
        getViewState().updateItem(repository, openItemPosition);
    }

    public void loadRepositories() {
        if (!isDataLoaded()) {
            if (networkHelper.isConnected(context)) {
                getViewState().showProgress();
                githubApiHelper.loadRepositories();
            } else {
                getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
            }
        }
    }

    public boolean isDataLoaded() {
        return !data.isEmpty();
    }

    public void setLogin(Intent intent) {
        this.login = intent.getStringExtra(Const.LOGIN_EXTRA_KEY);
        getViewState().setupTitle(context.getString(R.string.repos_activity_title, login));
    }

    @Override
    public void onRepositoriesLoaded(List<Repository> repositories) {
        data = repositories;
        getViewState().showRepositories(repositories);
        getViewState().hideProgress();
    }

    @Override
    public void onReposItemClicked(Repository repository, int position) {
        openItemPosition = position;
        getViewState().startRepositoryDetailsActivity(repository);
    }

    public String getLogin() {
        return login;
    }

}
