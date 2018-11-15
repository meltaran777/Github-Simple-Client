package com.fil.github_client;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.fil.github_client.di.AppComponent;
import com.fil.github_client.di.DaggerAppComponent;
import com.fil.github_client.di.modules.ContextModule;
import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.helper.NetworkHelper;
import com.fil.github_client.helper.RxHelper;
import com.fil.github_client.helper.StringHelper;
import com.fil.github_client.helper.ValidateHelper;
import com.fil.github_client.helper.image_loader.PicassoImageLoader;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.network.GithubApiClient;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesRepository;
import com.fil.github_client.repository.github_repositories.NetworkGithubRepositoriesRepository;
import com.fil.github_client.repository.user.GithubUserRepository;
import com.fil.github_client.repository.user.NetworkGithubUserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MyApplication extends Application {

    private static Context context;

    private static AppHelper appHelper;

    private static GithubApiClient mGithubApiClient;
    private static ErrorResponseHandler errorResponseHandler;

    private static GithubUserRepository         githubUserRepository;
    private static GithubRepositoriesRepository githubRepositoriesRepository;

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        Timber.plant(new Timber.DebugTree());

        mGithubApiClient = new GithubApiClient();

        errorResponseHandler = new ErrorResponseHandler();

        appHelper = new AppHelper(
                new RxHelper(Schedulers.io(), AndroidSchedulers.mainThread()),
                new NetworkHelper(),
                new ValidateHelper(),
                new StringHelper(),
                new PicassoImageLoader(),
                new ErrorResponseHandler());

        githubRepositoriesRepository = new NetworkGithubRepositoriesRepository(
                getGithubApiClient(),
                getAppHelper());

        githubUserRepository = new NetworkGithubUserRepository(
                getGithubApiClient(),
                getAppHelper());
    }

    public static Context getContext() {
        return context;
    }

    public static AppHelper getAppHelper() {
        return appHelper;
    }

    public static GithubApiClient getGithubApiClient() {
        return mGithubApiClient;
    }

    public static GithubRepositoriesRepository getGithubRepositoriesRepository() {
        return githubRepositoriesRepository;
    }

    public static GithubUserRepository getGithubUserRepository() {
        return githubUserRepository;
    }

    public static ErrorResponseHandler getErrorResponseHandler() {
        return errorResponseHandler;
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }


    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent) {
        sAppComponent = appComponent;
    }
}
