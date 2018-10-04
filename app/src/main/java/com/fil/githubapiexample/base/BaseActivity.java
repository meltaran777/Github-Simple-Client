package com.fil.githubapiexample.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.fil.githubapiexample.dialogs.ProgressDialog;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.screens.details.RepositoryDetailsActivity;
import com.fil.githubapiexample.screens.edit.EditReposActivity;
import com.fil.githubapiexample.screens.repos.RepositoriesActivity;
import com.fil.githubapiexample.util.Const;

public abstract class BaseActivity extends MvpAppCompatActivity implements BaseView, IBaseActivity, RouterCallbacks {
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
            case Router.DATA_CHANGED_RESULT_CODE:
                onDataChanged(data);
                break;
        }
    }

    @Override
    public void onDataChanged(Intent data) {

    }

    @Override
    public void startRepositoriesActivity(String login) {
        Intent intent = new Intent(this, RepositoriesActivity.class);
        intent.putExtra(Const.LOGIN_EXTRA_KEY, login);
        startActivityForResult(intent, Router.REPOSITORY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void startRepositoryDetailsActivity(Repository repository) {
        Intent intent = new Intent(this, RepositoryDetailsActivity.class);
        intent.putExtra(Const.REPOSITORY_EXTRA_KEY, (Parcelable) repository);
        startActivityForResult(intent, Router.DETAILS_REPOSITORY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void startRepositoryEditActivity(Repository repository) {
        Intent intent = new Intent(this, EditReposActivity.class);
        intent.putExtra(Const.REPOSITORY_EXTRA_KEY, (Parcelable) repository);
        startActivityForResult(intent, Router.REPOSITORY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void finishActivity(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }

    @Override
    public void finishActivity() {
        finish();
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
