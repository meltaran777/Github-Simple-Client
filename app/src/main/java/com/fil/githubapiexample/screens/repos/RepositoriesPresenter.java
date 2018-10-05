package com.fil.githubapiexample.screens.repos;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.presenter.BaseReposPresenter;
import com.fil.githubapiexample.adapter.repository.ReposItemInteractionListener;
import com.fil.githubapiexample.util.Const;

import java.util.ArrayList;
import java.util.List;

import static com.fil.githubapiexample.base.BaseView.SNACK_DURATION;

@InjectViewState
public class RepositoriesPresenter extends BaseReposPresenter<RepositoriesView> implements ReposItemInteractionListener {

    private List<Repository> data;

    private String login;
    private int    openItemPosition, itemToDeletePosition;

    public RepositoriesPresenter(Context context, AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context, appHelper, githubApiHelper);
        data = new ArrayList<>();
    }

    public void updateItem(Intent data) {
        setRepository(data);
        getViewState().updateItem(repository, openItemPosition);
    }

    public void loadRepositories() {
        if (!isDataLoaded()) {
            loadData();
        }
    }

    public void refresh() {
        loadData();
    }

    private void loadData() {
        if (networkHelper.isConnected(context)) {
            getViewState().showProgress();
            githubApiHelper.loadRepositories();
        } else {
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    @Override
    public void onRepositoriesLoaded(List<Repository> repositories) {
        data = repositories;
        getViewState().hideProgress();
        if (isDataLoaded()) {
            getViewState().setNoRepositoryTextViewVisibility(View.GONE);
            getViewState().onRepositoriesLoaded(repositories);
        } else getViewState().setNoRepositoryTextViewVisibility(View.VISIBLE);
    }

    @Override
    public void onReposItemClicked(Repository repository, int position) {
        openItemPosition = position;
        getViewState().startRepositoryDetailsActivity(repository);
    }

    @Override
    public void onReposMenuDeleteItemClicked(Repository repository, int position) {
        itemToDeletePosition = position;
        if (networkHelper.isConnected(context)) {
            getViewState().showProgress();
            githubApiHelper.deleteRepository(repository.getOwner().getLogin(), repository.getName());
        } else {
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    @Override
    public void onRepoDeleted() {
        getViewState().hideProgress();
        getViewState().deleteItem(itemToDeletePosition);
    }

    public void setLogin(Intent intent) {
        this.login = intent.getStringExtra(Const.LOGIN_EXTRA_KEY);
        getViewState().setupTitle(context.getString(R.string.repos_activity_title, login));
    }

    public List<Repository> getData() {
        return data;
    }

    public String getLogin() {
        return login;
    }

    private boolean isDataLoaded() {
        return !data.isEmpty();
    }

}
