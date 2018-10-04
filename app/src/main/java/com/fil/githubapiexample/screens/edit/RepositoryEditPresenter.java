package com.fil.githubapiexample.screens.edit;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.arellomobile.mvp.InjectViewState;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.presenter.BaseReposPresenter;

import static com.fil.githubapiexample.base.BaseView.SNACK_DURATION;

@InjectViewState
public class RepositoryEditPresenter extends BaseReposPresenter<RepositoryEditView> {

    public RepositoryEditPresenter(Context context, AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context, appHelper, githubApiHelper);
    }

    public void initUi() {
        getViewState().enableBackButton();
        getViewState().setupTitle(repository.getName());
        initUi(repository);
    }

    private void initUi(Repository repository) {
        getViewState().showRepositoryName(repository.getName());
        getViewState().showRepositoryDescription(repository.getDescription());
    }

    public void editRepository(String name, String description) {
        getViewState().showProgress();

        String oldName = repository.getName();

        repository.setName(name);
        repository.setDescription(description);

        if (networkHelper.isConnected(context)) {
            githubApiHelper.editRepository(repository.getOwner().getLogin(), oldName, repository);
        } else {
            getViewState().hideKeyboard();
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    @Override
    public void onRepositoryEdited(Repository newRepository) {
        getViewState().hideProgress();
        getViewState().onRepositorySaved(newRepository);
        getViewState().showSnackbar(
                context.getString(R.string.save_success_message_text),
                Snackbar.LENGTH_INDEFINITE,
                context.getString(R.string.snackbar_save_action_text),
                (view) -> getViewState().finishActivity());
    }
}
