package com.fil.githubapiexample.screens.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.fil.githubapiexample.MyApplication;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.screens.base.BaseActivity;
import com.fil.githubapiexample.screens.base.Router;

public class RepositoryDetailsActivity extends BaseActivity implements RepositoryDetailsView {

    ImageView   avatarImageView;
    TextView    ownerNameTextView;
    TextView    repoNameTextView;
    TextView    repoDescTextView;
    TextView    starsCountTextView;
    ProgressBar progressBar;

    @InjectPresenter
    public RepositoryDetailsPresenter presenter;

    @ProvidePresenter
    RepositoryDetailsPresenter providePresenter() {
        return new RepositoryDetailsPresenter(this.getApplicationContext(),MyApplication.getAppHelper(),
                new GithubApiHelper(MyApplication.getGihubApiClient()));
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

        presenter.setRepository(getIntent());
        presenter.initUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.initToolbar();
    }

    @Override
    public void onDataChanged(Intent data) {
        setResult(Router.DATA_CHANGED_RESULT_CODE, data);
        presenter.setRepository(data);
        presenter.updateUi();
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
                startRepositoryEditActivity(presenter.getRepository());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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
