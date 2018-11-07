package com.fil.github_client.screens.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.base.ActivityScreenView;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.screens.edit.EditReposActivity;
import com.fil.github_client.util.Const;

public class RepositoryDetailsActivity extends ActivityScreenView implements RepositoryDetailsView {

    private ImageView   avatarImageView;
    private TextView    ownerNameTextView;
    private TextView    repoNameTextView;
    private TextView    repoDescTextView;
    private TextView    starsCountTextView;
    private ProgressBar progressBar;

    @InjectPresenter
    public RepositoryDetailsPresenter presenter;

    @ProvidePresenter
    RepositoryDetailsPresenter providePresenter() {
        GithubRepositoriesInteraction githubRepositoriesInteraction = new GithubRepositoriesInteraction(
                MyApplication.getGithubRepositoriesRepository(),
                new ErrorResponseHandler());

        return new RepositoryDetailsPresenter(
                this.getApplicationContext(),
                githubRepositoriesInteraction,
                MyApplication.getAppHelper());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);

        progressBar = findViewById(R.id.avatar_progress_bar);
        avatarImageView = findViewById(R.id.user_avatar_image_view);
        ownerNameTextView = findViewById(R.id.user_name_text_view);
        starsCountTextView = findViewById(R.id.stars_count_text_view);
        repoNameTextView = findViewById(R.id.repo_name_text_view);
        repoDescTextView = findViewById(R.id.desc_full_text_view);

        presenter.setGitRepository(getIntent());
        presenter.initUi();
    }

    @Override
    protected void onDataChanged(Intent data) {
        presenter.changeData(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.repos_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_action:
                presenter.editRepository();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showRepositoryEditView(GitRepository gitRepository) {
        Intent intent = new Intent(this, EditReposActivity.class);
        intent.putExtra(Const.REPOSITORY_EXTRA_KEY, (Parcelable) gitRepository);
        this.startActivityForResult(intent, Const.EDIT_REPOSITORY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void showOwnerAvatar(Bitmap avatarBitmap) {
        avatarImageView.setImageBitmap(avatarBitmap);
    }

    @Override
    public void showAvatarProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAvatarProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showOwnerName(String ownerName) {
        ownerNameTextView.setText(ownerName);
    }

    @Override
    public void showRating(int starsCount) {
        starsCountTextView.setText(String.valueOf(starsCount));
    }

    @Override
    public void showRepositoryName(String repositoryName) {
        repoNameTextView.setText(repositoryName);
    }

    @Override
    public void showRepositoryDescription(String description) {
        repoDescTextView.setText(description);
    }

    @NonNull
    @Override
    protected View provideView() {
        return ownerNameTextView;
    }
}
