package com.fil.github_client.screens.login;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.fil.github_client.R;
import com.fil.github_client.base.presenter.BaseInteractionPresenter;
import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.helper.ValidateHelper;
import com.fil.github_client.model.User;
import com.fil.github_client.repository.user.GithubUserInteraction;
import com.fil.github_client.repository.user.GithubUserInteractionListener;

import static com.fil.github_client.base.ScreenView.SNACK_DURATION;

@InjectViewState
public class LoginPresenter
        extends BaseInteractionPresenter<LoginView, GithubUserInteraction, GithubUserInteractionListener>
        implements GithubUserInteractionListener {

    private ValidateHelper validateHelper;

    public LoginPresenter(Context context, GithubUserInteraction githubUserInteraction, AppHelper appHelper) {
        super(context, githubUserInteraction, appHelper);
        githubUserInteraction.setListener(this);
        this.validateHelper = appHelper.getValidateHelper();
    }

    public void login(String username, String password) {
        if (networkHelper.isConnected(context)) {
            if (isValidPassword(password) && isValidLogin(username)) {
                interaction.login(username, password);
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

    @Override
    public void onUserLogin(User user) {
        getViewState().hideProgress();
        getViewState().showRepositoriesView(user.getLogin());
        getViewState().hideView();
    }

    public boolean isValidForm(String login, String password) {
        return isValidPassword(login) && isValidLogin(password);
    }

    private boolean isValidPassword(String password) {
        return validateHelper.isValidPassword(password);
    }

    private boolean isValidLogin(String login) {
        return validateHelper.isValidLogin(login);
    }
}
