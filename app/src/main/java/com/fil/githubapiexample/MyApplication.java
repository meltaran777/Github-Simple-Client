package com.fil.githubapiexample;

import android.app.Application;
import android.util.Base64;

import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.helper.NetworkHelper;
import com.fil.githubapiexample.helper.StringHelper;
import com.fil.githubapiexample.helper.ValidateHelper;
import com.fil.githubapiexample.rest.GithubApiClient;

import static com.fil.githubapiexample.rest.GithubApiInterface.A_COLON;
import static com.fil.githubapiexample.rest.GithubApiInterface.EMPTY_STR;
import static com.fil.githubapiexample.rest.GithubApiInterface.NEW_LINE;

public class MyApplication extends Application {

    private static AppHelper appHelper;

    private static GithubApiClient mGithubApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mGithubApiClient = new GithubApiClient();

        appHelper = new AppHelper(
                new NetworkHelper(), new ValidateHelper(), new StringHelper());
    }

    public static AppHelper getAppHelper() {
        return appHelper;
    }

    public static GithubApiClient getGihubApiClient() {
        return mGithubApiClient;
    }
}
