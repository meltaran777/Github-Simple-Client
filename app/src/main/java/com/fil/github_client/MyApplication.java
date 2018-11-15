package com.fil.github_client;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.fil.github_client.di.AppComponent;
import com.fil.github_client.di.DaggerAppComponent;
import com.fil.github_client.di.modules.ContextModule;

import timber.log.Timber;

public class MyApplication extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        Timber.plant(new Timber.DebugTree());
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent) {
        sAppComponent = appComponent;
    }
}
