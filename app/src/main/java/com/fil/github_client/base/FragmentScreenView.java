package com.fil.github_client.base;

import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class FragmentScreenView extends MvpAppCompatFragment implements ScreenView {

    @Override
    public void hideView() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }

    @Override
    public void showSnackbar(String message, int duration) {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).showSnackbar(message, duration);
        }
    }

    @Override
    public void showSnackbar(String message, int duration, String actionText, View.OnClickListener listener) {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).showSnackbar(message, duration, actionText, listener);
        }
    }

    @Override
    public void hideKeyboard() {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).hideKeyboard();
        }
    }

    @Override
    public void showProgress() {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).hideProgress();
        }
    }

    @Override
    public void setupTitle(String title) {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).setupTitle(title);
        }
    }

    @Override
    public void setupTitle(int resId) {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).setupTitle(resId);
        }
    }

    @Override
    public void enableBackButton() {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).enableBackButton();
        }
    }

    @Override
    public void disableBackButton() {
        if (getActivity() instanceof ScreenView) {
            ((ScreenView) getActivity()).disableBackButton();
        }
    }
}
