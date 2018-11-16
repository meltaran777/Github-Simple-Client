package com.fil.github_client.di.modules;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.network.GithubApiClient;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesRepository;
import com.fil.github_client.repository.github_repositories.NetworkGithubRepositoriesRepository;
import com.fil.github_client.repository.user.GithubUserRepository;
import com.fil.github_client.repository.user.NetworkGithubUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppHelperModule.class, GithubApiClientModule.class})
public class RepositoryModule {
    @Provides
    @Singleton
    public GithubUserRepository provideGithubUserRepository(GithubApiClient githubApiClient, AppHelper appHelper) {
        return new NetworkGithubUserRepository(githubApiClient, appHelper);
    }

    @Provides
    @Singleton
    public GithubRepositoriesRepository provideGithubService(GithubApiClient githubApiClient, AppHelper appHelper) {
        return new NetworkGithubRepositoriesRepository(githubApiClient, appHelper);
    }
}
