package com.fil.github_client.screens.repositories;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.base.presenter.BaseRepositoryPresenter;
import com.fil.github_client.helper.NetworkHelper;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.screens.repositories.adapter.RepositoryListItemInteractionListener;
import com.fil.github_client.util.Const;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.fil.github_client.base.ScreenView.SNACK_DURATION;

@InjectViewState
public class RepositoriesPresenter extends BaseRepositoryPresenter<RepositoriesView> implements RepositoryListItemInteractionListener {

    @Inject Context       context;
    @Inject NetworkHelper networkHelper;

    @Inject GithubRepositoriesInteraction interaction;

    private List<GitRepository> data;

    private String login;
    private int    openItemPosition, itemToDeletePosition;

    public RepositoriesPresenter() {
        MyApplication.getAppComponent().inject(this);
        interaction.setListener(this);
        interaction.getErrorResponseHandler().subscribe(this);
        data = new ArrayList<>();
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
            interaction.loadRepositories();
        } else {
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    public void updateItem(Intent data) {
        setGitRepository(data);
        getViewState().updateItem(gitRepository, openItemPosition);
    }

    public void setLogin(Intent intent) {
        this.login = intent.getStringExtra(Const.LOGIN_EXTRA_KEY);
        getViewState().setupTitle(context.getString(R.string.repos_activity_title, login));
        getViewState().disableBackButton();
    }

    @Override
    public void onRepositoriesLoaded(List<GitRepository> repositories) {
        data = repositories;
        getViewState().hideProgress();
        if (isDataLoaded()) {
            getViewState().setNoRepositoryTextViewVisibility(View.GONE);
            getViewState().showRepositories(repositories);
        } else getViewState().setNoRepositoryTextViewVisibility(View.VISIBLE);
    }

    @Override
    public void onRepositoryDeleted(GitRepository gitRepository) {
        getViewState().hideProgress();
        getViewState().deleteItem(itemToDeletePosition);
    }

    @Override
    public void onRepositoryItemClicked(GitRepository gitRepository, int position) {
        openItemPosition = position;
        getViewState().showRepositoryDetailsView(gitRepository);
    }

    @Override
    public void onRepositoryMenuDeleteItemClicked(GitRepository gitRepository, int position) {
        itemToDeletePosition = position;
        if (networkHelper.isConnected(context)) {
            getViewState().showProgress();
            interaction.deleteRepository(gitRepository);
        } else {
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interaction.getErrorResponseHandler().unsubscribe(this);
        interaction.destroy();
    }

    public List<GitRepository> getData() {
        return data;
    }

    public String getLogin() {
        return login;
    }

    private boolean isDataLoaded() {
        return !data.isEmpty();
    }

}
