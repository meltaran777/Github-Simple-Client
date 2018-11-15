package com.fil.github_client.repository;

import com.fil.github_client.MyApplication;
import com.fil.github_client.network.ErrorResponseHandler;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseInteraction {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ErrorResponseHandler errorResponseHandler;

    public BaseInteraction() {
        MyApplication.getAppComponent().inject(this);
    }

    public void addDisposable(final Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void destroy() {
        compositeDisposable.dispose();
    }

    public ErrorResponseHandler getErrorResponseHandler() {
        return errorResponseHandler;
    }
}
