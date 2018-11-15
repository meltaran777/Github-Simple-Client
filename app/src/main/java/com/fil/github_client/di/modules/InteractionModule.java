package com.fil.github_client.di.modules;

import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.repository.user.GithubUserInteraction;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppHelperModule.class, RepositoryModule.class, ErrorHandlerModule.class})
public class InteractionModule {
    @Provides
    public GithubUserInteraction provideGithubUserInteraction() {
        return new GithubUserInteraction();
    }

    @Provides
    public GithubRepositoriesInteraction provideGithubRepositoriesInteraction() {
        return new GithubRepositoriesInteraction();
    }
}
