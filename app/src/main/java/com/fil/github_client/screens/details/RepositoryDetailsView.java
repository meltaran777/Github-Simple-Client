package com.fil.github_client.screens.details;

import android.graphics.Bitmap;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fil.github_client.base.ScreenView;
import com.fil.github_client.model.GitRepository;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface RepositoryDetailsView extends ScreenView {
    void showOwnerAvatar(Bitmap avatarBitmap);

    void showAvatarProgress();

    void hideAvatarProgress();

    void showOwnerName(String ownerName);

    void showRepositoryName(String repositoryName);

    void showRepositoryDescription(String description);

    void showRating(int starsCount);

    void showRepositoryEditView(GitRepository gitRepository);
}

