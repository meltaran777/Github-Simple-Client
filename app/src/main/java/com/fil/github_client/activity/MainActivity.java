package com.fil.github_client.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import com.fil.github_client.R;
import com.fil.github_client.base.RepositoryViewController;
import com.fil.github_client.base.back_stack.BackStackActivity;
import com.fil.github_client.screens.repositories.RepositoriesFragment;
import com.fil.github_client.util.Const;

public class MainActivity extends BackStackActivity implements RepositoryViewController {

    FrameLayout contentRoot;

    Fragment repositoriesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentRoot = findViewById(R.id.main_activity_content);

        if (savedInstanceState == null) {
            if (!getIntent().hasExtra(Const.LOGIN_EXTRA_KEY)) {
                throw new IllegalArgumentException("Must have login extra!");
            }
            String login = getIntent().getStringExtra(Const.LOGIN_EXTRA_KEY);
            repositoriesFragment = RepositoriesFragment.newInstance(login);
            getBackStackManager().addRootFragment(repositoriesFragment, R.id.main_activity_content);
        } else {
            repositoriesFragment = getBackStackManager().findFragment(RepositoriesFragment.class);
        }
    }

    @Override
    public void onRepositoryEdited(Intent data) {
        if (repositoriesFragment instanceof RepositoryViewController){
            ((RepositoryViewController) repositoriesFragment).onRepositoryEdited(data);
        }
    }

    @NonNull
    @Override
    protected View provideView() {
        return contentRoot;
    }
}
