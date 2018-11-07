package com.fil.github_client.repository.github_repositories;

import com.fil.github_client.model.GitRepository;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.BaseInteraction;

import io.reactivex.disposables.Disposable;

public class GithubRepositoriesInteraction extends BaseInteraction<GithubRepositoriesInteractionListener> {

    private GithubRepositoriesRepository repository;

    public GithubRepositoriesInteraction(GithubRepositoriesRepository repository, ErrorResponseHandler errorResponseHandler) {
        super(errorResponseHandler);
        this.repository = repository;
    }

    public void loadRepositories() {
        Disposable subscribe = repository
                .getRepositories()
                .subscribe(listener::onRepositoriesLoaded, errorResponseHandler::handlerError);
        addDisposable(subscribe);
    }

    public void deleteRepository(GitRepository gitRepository) {
        Disposable subscribe = this.repository
                .deleteRepository(gitRepository.getOwner().getLogin(), gitRepository.getName())
                .subscribe(response -> listener.onRepositoryDeleted(gitRepository), errorResponseHandler::handlerError);
        addDisposable(subscribe);
    }

    public void editRepository(String oldName,GitRepository newGitRepository) {
        Disposable subscribe = this.repository
                .editRepository(newGitRepository.getOwner().getLogin(), oldName, newGitRepository)
                .subscribe(listener::onRepositoryEdited, errorResponseHandler::handlerError);
        addDisposable(subscribe);
    }
}
