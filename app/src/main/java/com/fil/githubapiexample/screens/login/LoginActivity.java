package com.fil.githubapiexample.screens.login;

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
import com.fil.githubapiexample.MyApplication;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginView {

    TextInputEditText loginEditText;
    TextInputEditText passwordEditText;

    Button loginButon;

    @InjectPresenter
    LoginPresenter presenter;

    @ProvidePresenter
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter(this.getApplicationContext(), MyApplication.getAppHelper(),
                new GithubApiHelper(MyApplication.getGihubApiClient()));
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

