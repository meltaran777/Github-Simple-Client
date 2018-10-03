package com.fil.githubapiexample.screens.details;

import android.graphics.Bitmap;

import com.fil.githubapiexample.screens.base.BaseView;

public interface RepositoryDetailsView extends BaseView {
    void showOwnerAvatar(Bitmap avatarBitmap);

    void showAvatarProgress();

    void hideAvatarProgress();

    void showOwnerName(String ownerName);

    void showRepositoryName(String repositoryName);

    void showRepositoryDescription(String description);

    void showRating(int starsCount);
}

