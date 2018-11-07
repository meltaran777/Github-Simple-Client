package com.fil.github_client.base.presenter;

import android.content.Context;
import android.content.Intent;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.base.ScreenView;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteractionListener;
import com.fil.github_client.util.Const;

import java.util.List;

public abstract class BaseRepositoryPresenter<V extends ScreenView>
        extends BaseInteractionPresenter<V, GithubRepositoriesInteraction, GithubRepositoriesInteractionListener>
        implements GithubRepositoriesInteractionListener {

    protected GitRepository gitRepository;

    public BaseRepositoryPresenter(Context context, GithubRepositoriesInteraction githubRepositoriesInteraction, AppHelper appHelper) {
        super(context, githubRepositoriesInteraction, appHelper);
    }

    public void setGitRepository(Intent intent) {
        gitRepository = getReposItemFromIntent(intent);
    }

    private GitRepository getReposItemFromIntent(Intent intent) {
        return (GitRepository) intent.getParcelableExtra(Const.REPOSITORY_EXTRA_KEY);
    }

    public GitRepository getGitRepository() {
        return gitRepository;
    }

    @Override
    public void onRepositoriesLoaded(List<GitRepository> repositories) {

    }

    @Override
    public void onRepositoryDeleted(GitRepository gitRepository) {

    }

    @Override
    public void onRepositoryEdited(GitRepository newGitRepository) {

    }

    @Override
    protected GithubRepositoriesInteractionListener provideInteractionListener() {
        return this;
    }
}
