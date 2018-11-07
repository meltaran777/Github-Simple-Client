package com.fil.github_client.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.base.ActivityScreenView;
import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.user.GithubUserInteraction;
import com.fil.github_client.screens.repositories.RepositoriesActivity;
import com.fil.github_client.util.Const;

public class LoginActivity extends ActivityScreenView implements LoginView {

    TextInputEditText loginEditText;
    TextInputEditText passwordEditText;

    Button loginButon;

    @InjectPresenter
    LoginPresenter presenter;

    @ProvidePresenter
    LoginPresenter provideLoginPresenter() {
        GithubUserInteraction githubUserInteractor = new GithubUserInteraction(
                MyApplication.getGithubUserRepository(),
                new ErrorResponseHandler());

        AppHelper appHelper = MyApplication.getAppHelper();

        return new LoginPresenter(this.getApplicationContext(), githubUserInteractor, appHelper);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTitle(getString(R.string.login_activity_title_text));

        loginEditText = findViewById(R.id.login_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButon = findViewById(R.id.login_button);

        loginEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                supportInvalidateOptionsMenu();
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                supportInvalidateOptionsMenu();
            }
        });

        loginButon.setOnClickListener(view -> presenter.login(getLogin(), getPassword()));
    }

    @Override
    public void showRepositoriesView(String login) {
        Intent intent = new Intent(this, RepositoriesActivity.class);
        intent.putExtra(Const.LOGIN_EXTRA_KEY, login);
        startActivityForResult(intent, Const.REPOSITORY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_action:
                presenter.login(getLogin(), getPassword());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.login_action).setEnabled(presenter.isValidForm(getLogin(), getPassword()));
        return true;
    }

    private String getLogin() {
        return String.valueOf(loginEditText.getText());
    }

    private String getPassword() {
        return String.valueOf(passwordEditText.getText());
    }

    @NonNull
    @Override
    protected View provideView() {
        return loginButon;
    }
}

