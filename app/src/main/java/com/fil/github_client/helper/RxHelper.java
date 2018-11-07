package com.fil.github_client.helper;

import android.support.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

public class RxHelper {

    @NonNull final Scheduler background, main;

    public RxHelper(@NonNull Scheduler background, @NonNull Scheduler main) {
        this.background = background;
        this.main = main;
    }

    @NonNull
    public <T> ObservableTransformer<T, T> async() {
        return observable -> observable
                .subscribeOn(background)
                .observeOn(main);
    }
}
