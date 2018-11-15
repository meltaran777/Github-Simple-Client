package com.fil.github_client.di.modules;

import com.fil.github_client.network.GithubApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class GithubApiClientModule {
    @Provides
    @Singleton
    public GithubApiClient provideGithubApiClient(){
        return new GithubApiClient();
    }
}
