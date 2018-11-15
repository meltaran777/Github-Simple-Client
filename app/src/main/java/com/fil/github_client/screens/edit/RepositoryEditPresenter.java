package com.fil.github_client.screens.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;

import com.arellomobile.mvp.InjectViewState;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.helper.NetworkHelper;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.base.presenter.BaseRepositoryPresenter;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.util.Const;

import javax.inject.Inject;

import static com.fil.github_client.base.ScreenView.SNACK_DURATION;

@InjectViewState
public class RepositoryEditPresenter extends BaseRepositoryPresenter<RepositoryEditView> {

    private boolean isViewCreated = false;

    @Inject Context       context;
    @Inject NetworkHelper networkHelper;

    @Inject GithubRepositoriesInteraction repositoriesInteraction;

    public RepositoryEditPresenter(){
        MyApplication.getAppComponent().inject(this);
        repositoriesInteraction.setListener(this);
        repositoriesInteraction.getErrorResponseHandler().subscribe(this);
    }

    public void init(GitRepository gitRepository) {
        if (!isViewCreated){
            setGitRepository(gitRepository);

            getViewState().enableBackButton();
            getViewState().setupTitle(gitRepository.getName());

            initViews(gitRepository);

            isViewCreated = true;
        }
    }

    private void initViews(GitRepository gitRepository) {
        getViewState().showRepositoryName(gitRepository.getName());
        getViewState().showRepositoryDescription(gitRepository.getDescription());
    }

    public void editRepository(String name, String description) {
        getViewState().showProgress();

        String oldName = gitRepository.getName();

        gitRepository.setName(name);
        gitRepository.setDescription(description);

        if (networkHelper.isConnected(context)) {
            repositoriesInteraction.editRepository(oldName, gitRepository);
        } else {
            getViewState().hideKeyboard();
            getViewState().showSnackbar(context.getString(R.string.no_internet_message), SNACK_DURATION);
        }
    }

    @Override
    public void onRepositoryEdited(GitRepository newGitRepository) {
        getViewState().hideProgress();

        getViewState().setViewResult(
                Const.REPOSITORY_EDITED_RESULT_CODE,
                new Intent().putExtra(Const.REPOSITORY_EXTRA_KEY, (Parcelable) newGitRepository));

        getViewState().showSnackbar(
                context.getString(R.string.save_success_message_text),
                Snackbar.LENGTH_INDEFINITE,
                context.getString(R.string.snackbar_save_action_text),
                (view) -> getViewState().hideView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repositoriesInteraction.getErrorResponseHandler().unsubscribe(this);
        repositoriesInteraction.destroy();
    }
}
