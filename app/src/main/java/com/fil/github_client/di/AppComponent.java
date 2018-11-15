package com.fil.github_client.di;

import android.content.Context;

import com.fil.github_client.di.modules.ContextModule;
import com.fil.github_client.screens.login.LoginPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class})
public interface AppComponent {
    Context getContext();

    void inject(LoginPresenter presenter);
}
