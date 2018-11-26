package com.fil.github_client.base.back_stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.fil.github_client.base.ActivityScreenView;
import com.fil.github_client.helper.back_stack.BackStackListener;
import com.fil.github_client.helper.back_stack.BackStackManager;

public abstract class BackStackActivity extends ActivityScreenView implements BackStackProvider {

    @InjectPresenter
    BackStackActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            createBackStackManager();
        } else {
            presenter.getBackStackManager().setFragmentManager(getSupportFragmentManager());
        }
    }

    private void createBackStackManager() {
        if (presenter.getBackStackManager() == null) {
            presenter.setBackStackManager(new BackStackManager(new BackStackListener() {
                @Override
                public void onBackStackEmpty() {
                    BackStackActivity.this.finish();
                }

                @Override
                public void onRootFragmentChangedBack(Fragment fragment) {

                }
            }, getSupportFragmentManager()));
        }
    }

    @Override
    public BackStackManager getBackStackManager() {
        return presenter.getBackStackManager();
    }

    @Override
    public void onBackPressed() {
        presenter.getBackStackManager().onBackPressed();
    }
}
