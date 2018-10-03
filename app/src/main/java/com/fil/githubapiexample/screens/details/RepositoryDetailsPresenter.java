package com.fil.githubapiexample.screens.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.arellomobile.mvp.InjectViewState;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.helper.AppHelper;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.screens.base.presenter.BaseReposPresenter;
import com.fil.githubapiexample.util.Utilities;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

@InjectViewState
public class RepositoryDetailsPresenter extends BaseReposPresenter<RepositoryDetailsView> {

    public RepositoryDetailsPresenter(Context context, AppHelper appHelper, GithubApiHelper githubApiHelper) {
        super(context, appHelper, githubApiHelper);
    }

    public void initToolbar() {
        getViewState().enableBackButton();
        getViewState().setupTitle(repository.getName());
    }

    public void initUi() {
        initViews(repository);
        loadAvatarImage(repository);
    }

    public void updateUi() {
        initViews(repository);
    }

    private void initViews(Repository repository) {
        getViewState().showOwnerName(repository.getOwner().getLogin());
        getViewState().showRepositoryName(repository.getName());

        String description = repository.getDescription();

        if (!appHelper.getStringHelper().isValidString(description))
            description = context.getString(R.string.no_desc_text);

        getViewState().showRepositoryDescription(description);

        getViewState().showRating(repository.getStargazersCount());
    }

    private void loadAvatarImage(Repository repository) {
        Picasso.get()
                .load(repository.getOwner().getAvatarUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
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
