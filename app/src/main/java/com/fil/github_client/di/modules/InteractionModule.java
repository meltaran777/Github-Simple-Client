package com.fil.github_client.di.modules;

import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesRepository;
import com.fil.github_client.repository.user.GithubUserInteraction;
import com.fil.github_client.repository.user.GithubUserRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppHelperModule.class, RepositoryModule.class,})
public class InteractionModule {
    @Provides
    public GithubUserInteraction provideGithubUserInteraction(GithubUserRepository repository,
                                                              ErrorResponseHandler errorResponseHandler) {
        return new GithubUserInteraction(repository, errorResponseHandler);
    }

    @Provides
    public GithubRepositoriesInteraction provideGithubRepositoriesInteraction(GithubRepositoriesRepository repository,
                                                                              ErrorResponseHandler errorResponseHandler) {
        return new GithubRepositoriesInteraction(repository, errorResponseHandler);
    }
}
