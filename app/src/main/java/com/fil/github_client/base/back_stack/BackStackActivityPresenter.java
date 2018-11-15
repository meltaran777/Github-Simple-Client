package com.fil.github_client.base.back_stack;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.fil.github_client.helper.back_stack.BackStackManager;

@InjectViewState
public class BackStackActivityPresenter extends MvpPresenter<MvpView> {

    private BackStackManager backStackManager;

    public BackStackManager getBackStackManager() {
        return backStackManager;
    }

    public void setBackStackManager(BackStackManager backStackManager) {
        this.backStackManager = backStackManager;
    }
}
