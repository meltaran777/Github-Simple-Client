package com.fil.github_client.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.fil.github_client.dialogs.ProgressDialog;
import com.fil.github_client.util.Const;

public abstract class ActivityScreenView extends MvpAppCompatActivity implements ScreenView {

    @NonNull
    protected abstract View provideView();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog();
    }

    @Override
    public void showSnackbar(String message, int duration) {
        Snackbar.make(provideView(), message, duration).show();
    }

    @Override
    public void showSnackbar(String message, int duration, String actionText, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(provideView(), message, duration);
        snackbar.setAction(actionText, view -> {
            snackbar.dismiss();
            listener.onClick(view);
        }).show();
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(provideView().getWindowToken(), 0);
        }
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && progressDialog.isCanShow()) {
            progressDialog.setCanShow(false);
            progressDialog.show(getFragmentManager(), "progress");
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && !progressDialog.isCanShow()) {
            progressDialog.dismiss();
            progressDialog.setCanShow(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Const.REPOSITORY_EDITED_RESULT_CODE:
                onDataChanged(data);
                break;
        }
    }

    protected void onDataChanged(Intent data) {
    }

    @Override
    public void hideView(int resultCode, Intent data) {
        setViewResult(resultCode, data);
        finish();
    }

    @Override
    public void hideView() {
        finish();
    }

    @Override
    public void setViewResult(int resultCode, Intent data) {
        setResult(resultCode, data);
    }

    @Override
    public void enableBackButton() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void setupTitle(String title) {
        setTitle(title);
    }

    @Override
    public void setupTitle(@StringRes int resId) {
        setTitle(getString(resId));
    }
}
