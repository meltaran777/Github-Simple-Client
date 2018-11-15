package com.fil.github_client.di;

import android.content.Context;

import com.fil.github_client.di.modules.ContextModule;
import com.fil.github_client.di.modules.InteractionModule;
import com.fil.github_client.repository.BaseInteraction;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.repository.user.GithubUserInteraction;
import com.fil.github_client.screens.details.RepositoryDetailsPresenter;
import com.fil.github_client.screens.edit.RepositoryEditPresenter;
import com.fil.github_client.screens.login.LoginPresenter;
import com.fil.github_client.screens.repositories.RepositoriesPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, InteractionModule.class})
public interface AppComponent {
    Context getContext();

    void inject(LoginPresenter loginPresenter);

    void inject(BaseInteraction baseInteraction);

    void inject(GithubRepositoriesInteraction githubRepositoriesInteraction);

    void inject(GithubUserInteraction githubUserInteraction);

    void inject(RepositoriesPresenter repositoriesPresenter);

    void inject(RepositoryEditPresenter repositoryEditPresenter);

    void inject(RepositoryDetailsPresenter repositoryDetailsPresenter);
}
