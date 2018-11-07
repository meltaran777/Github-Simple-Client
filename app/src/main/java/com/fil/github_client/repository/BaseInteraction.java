package com.fil.github_client.repository;

import com.fil.github_client.network.ErrorResponseHandler;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseInteraction<L extends InteractionListener> {

    public L listener;

    protected final CompositeDisposable compositeDisposable;

    protected final ErrorResponseHandler errorResponseHandler;

    public BaseInteraction(final ErrorResponseHandler errorResponseHandler) {
        this.errorResponseHandler = errorResponseHandler;
        compositeDisposable = new CompositeDisposable();
    }

    public void addDisposable(final Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void destroy() {
        listener = null;
        compositeDisposable.dispose();
    }

    public ErrorResponseHandler getErrorResponseHandler() {
        return errorResponseHandler;
    }

    public L getListener() {
        return listener;
    }

    public void setListener(L listener) {
        this.listener = listener;
    }
}
