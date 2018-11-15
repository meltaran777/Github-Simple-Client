package com.fil.github_client.screens.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.arellomobile.mvp.InjectViewState;
import com.fil.github_client.R;
import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.helper.image_loader.ImageLoader;
import com.fil.github_client.helper.image_loader.Target;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.base.presenter.BaseRepositoryPresenter;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;


@InjectViewState
public class RepositoryDetailsPresenter extends BaseRepositoryPresenter<RepositoryDetailsView> {

    ImageLoader imageLoader;

    public RepositoryDetailsPresenter(Context context,
                                      GithubRepositoriesInteraction githubRepositoriesInteraction,
                                      AppHelper appHelper) {
        super(context, githubRepositoriesInteraction, appHelper);
        this.imageLoader = appHelper.getImageLoader();
    }

    public void initUi(Intent data) {
        boolean isInited = gitRepository != null;
        if (!isInited) {
            setGitRepository(data);
        }
        initToolbar(gitRepository);
        initViews(gitRepository);
        loadAvatarImage(gitRepository);
    }

    public void changeData(Intent data) {
        setGitRepository(data);
        updateUi(gitRepository);
    }

    public void editRepository() {
        getViewState().showRepositoryEditView(getGitRepository());
    }

    public void initToolbar(GitRepository gitRepository) {
        getViewState().enableBackButton();
        getViewState().setupTitle(gitRepository.getName());
    }

    private void updateUi(GitRepository gitRepository) {
        initToolbar(gitRepository);
        initViews(gitRepository);
    }

    private void initViews(GitRepository gitRepository) {
        getViewState().showOwnerName(gitRepository.getOwner().getLogin());
        getViewState().showRepositoryName(gitRepository.getName());

        String description = gitRepository.getDescription();
        if (!appHelper.getStringHelper().isValidString(description))
            description = context.getString(R.string.no_desc_text);
        getViewState().showRepositoryDescription(description);

        getViewState().showRating(gitRepository.getStargazersCount());
    }

    private void loadAvatarImage(GitRepository gitRepository) {
        imageLoader.loadImage(gitRepository.getOwner().getAvatarUrl(), new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                getViewState().hideAvatarProgress();
                getViewState().showOwnerAvatar(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                getViewState().hideAvatarProgress();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                getViewState().showAvatarProgress();
            }
        });
    }
}
