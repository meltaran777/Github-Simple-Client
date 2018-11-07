package com.fil.github_client.repository.user;

import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.BaseInteraction;

import io.reactivex.disposables.Disposable;

public class GithubUserInteraction extends BaseInteraction<GithubUserInteractionListener> {

    private final GithubUserRepository repository;

    public GithubUserInteraction(GithubUserRepository repository, ErrorResponseHandler errorResponseHandler) {
        super(errorResponseHandler);
        this.repository = repository;
    }

    public void login(String login, String password) {
        Disposable subscribe = repository
                .getUser(login, password)
                .subscribe(listener::onUserLogin, errorResponseHandler::handlerError);
        addDisposable(subscribe);
    }
}
