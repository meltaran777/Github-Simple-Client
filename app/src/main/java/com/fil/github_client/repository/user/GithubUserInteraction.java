package com.fil.github_client.repository.user;

import com.fil.github_client.MyApplication;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.BaseInteraction;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class GithubUserInteraction extends BaseInteraction{

    @Inject GithubUserRepository repository;

    private GithubUserInteractionListener listener;

    public GithubUserInteraction() {
        MyApplication.getAppComponent().inject(this);
    }

    public void login(String login, String password) {
        Disposable subscribe = repository
                .getUser(login, password)
                .subscribe(listener::onUserLogin, getErrorResponseHandler()::handlerError);
        addDisposable(subscribe);
    }

    public GithubUserInteractionListener getListener() {
        return listener;
    }

    public void setListener(GithubUserInteractionListener listener) {
        this.listener = listener;
    }
}
