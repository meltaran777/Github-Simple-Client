package com.fil.github_client.repository.github_repositories;

import com.fil.github_client.MyApplication;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.BaseInteraction;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class GithubRepositoriesInteraction extends BaseInteraction {

    @Inject GithubRepositoriesRepository repository;

    private GithubRepositoriesInteractionListener listener;

    public GithubRepositoriesInteraction() {
        MyApplication.getAppComponent().inject(this);
    }

    public void loadRepositories() {
        Disposable subscribe = repository
                .getRepositories()
                .subscribe(listener::onRepositoriesLoaded, getErrorResponseHandler()::handlerError);
        addDisposable(subscribe);
    }

    public void deleteRepository(GitRepository gitRepository) {
        Disposable subscribe = this.repository
                .deleteRepository(gitRepository.getOwner().getLogin(), gitRepository.getName())
                .subscribe(response -> listener.onRepositoryDeleted(gitRepository), getErrorResponseHandler()::handlerError);
        addDisposable(subscribe);
    }

    public void editRepository(String oldName,GitRepository newGitRepository) {
        Disposable subscribe = this.repository
                .editRepository(newGitRepository.getOwner().getLogin(), oldName, newGitRepository)
                .subscribe(listener::onRepositoryEdited, getErrorResponseHandler()::handlerError);
        addDisposable(subscribe);
    }

    public GithubRepositoriesInteractionListener getListener() {
        return listener;
    }

    public void setListener(GithubRepositoriesInteractionListener listener) {
        this.listener = listener;
    }
}
