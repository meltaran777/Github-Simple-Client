package com.fil.github_client.di.modules;

import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.network.GithubApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ErrorHandlerModule {
    @Provides
    public ErrorResponseHandler provideErrorResponseHandler(){
        return new ErrorResponseHandler();
    }
}
