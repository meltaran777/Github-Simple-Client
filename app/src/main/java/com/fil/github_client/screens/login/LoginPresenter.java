package com.fil.github_client.screens.login;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.base.presenter.BasePresenter;
import com.fil.github_client.helper.NetworkHelper;
import com.fil.github_client.helper.ValidateHelper;
import com.fil.github_client.model.User;
import com.fil.github_client.repository.user.GithubUserInteraction;
import com.fil.github_client.repository.user.GithubUserInteractionListener;

import javax.inject.Inject;

import static com.fil.github_client.base.ScreenView.SNACK_DURATION;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> implements GithubUserInteractionListener {

    @Inject ValidateHelper validateHelper;
    @Inject NetworkHelper  networkHelper;

    @Inject Context context;

    @Inject GithubUserInteraction interaction;

    public LoginPresenter() {
        MyApplication.getAppComponent().inject(this);
        interaction.setListener(this);
        interaction.getErrorResponseHandler().subscribe(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        interaction.getErrorResponseHandler().unsubscribe(this);
        interaction.destroy();
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
