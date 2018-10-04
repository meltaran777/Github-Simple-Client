package com.fil.githubapiexample.screens.login;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.helper.ValidateHelper;
import com.fil.githubapiexample.model.User;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.presenter.BaseApiPresenter;

import static com.fil.githubapiexample.base.BaseView.SNACK_DURATION;

@InjectViewState
public class LoginPresenter extends BaseApiPresenter<LoginView> {

    private ValidateHelper validateHelper;

    public LoginPresenter(Context context,AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context,appHelper, githubApiHelper);
        this.validateHelper = appHelper.getValidateHelper();
    }

    public void login(String username, String password) {
        if (networkHelper.isConnected(context)) {
            if (isValidPassword(password) && isValidLogin(username)) {
                githubApiHelper.login(username, password);
                getViewState().showProgress();
            } else if (!isValidLogin(username)) {
                getViewState().hideKeyboard();
                getViewState().showSnackbar(context.getString(R.string.incorrect_login), SNACK_DURATION);
            } else {
                getViewState().hideKeyboard();
                getViewState().showSnackbar(context.getString(R.string.incorrect_password), SNACK_DURATION);
            }
        } else {
            getViewState().hideKeyboard();
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    public boolean isValidForm(String login, String password) {
        return isValidPassword(login) && isValidLogin(password);
    }

    @Override
    public void onLogin(User user, String authEncode) {
        getViewState().hideProgress();
        getViewState().startRepositoriesActivity(user.getLogin());
        getViewState().finishActivity();
    }

    private boolean isValidPassword(String password) {
        return validateHelper.isValidPassword(password);
    }

    private boolean isValidLogin(String login) {
        return validateHelper.isValidLogin(login);
    }
}
