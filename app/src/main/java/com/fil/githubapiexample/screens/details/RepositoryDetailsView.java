package com.fil.githubapiexample.screens.details;

import android.graphics.Bitmap;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.githubapiexample.base.BaseView;

public interface RepositoryDetailsView extends BaseView {
    void showOwnerAvatar(Bitmap avatarBitmap);

    void showAvatarProgress();

    void hideAvatarProgress();

    void showOwnerName(String ownerName);

    void showRepositoryName(String repositoryName);

    void showRepositoryDescription(String description);

    void showRating(int starsCount);
}

