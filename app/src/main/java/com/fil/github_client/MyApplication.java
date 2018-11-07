package com.fil.github_client;

import android.app.Application;
import android.content.Context;

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

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();

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
}
