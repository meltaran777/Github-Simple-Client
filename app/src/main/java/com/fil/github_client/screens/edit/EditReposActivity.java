package com.fil.github_client.screens.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.base.ActivityScreenView;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;


public class EditReposActivity extends ActivityScreenView implements RepositoryEditView {

    EditText repoNameEditText;
    EditText repoDescEditText;

    @InjectPresenter
    RepositoryEditPresenter presenter;

    @ProvidePresenter
    RepositoryEditPresenter providePresenter() {
        GithubRepositoriesInteraction repositoriesInteractor = new GithubRepositoriesInteraction(
                        MyApplication.getGithubRepositoriesRepository(),
                        new ErrorResponseHandler());

        return new RepositoryEditPresenter(
                this.getApplicationContext(),
                repositoriesInteractor,
                MyApplication.getAppHelper());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_repos);

        repoNameEditText = findViewById(R.id.repo_edit_text);
        repoDescEditText = findViewById(R.id.desc_full_edit);

        presenter.setGitRepository(getIntent());
        presenter.initUi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_action:
                presenter.editRepository(getName(), getDescription());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showRepositoryName(String repositoryName) {
        repoNameEditText.setText(repositoryName);
    }

    @Override
    public void showRepositoryDescription(String description) {
        repoDescEditText.setText(description);
    }

    private String getName() {
        return String.valueOf(repoNameEditText.getText());
    }

    private String getDescription() {
        return String.valueOf(repoDescEditText.getText());
    }

    @NonNull
    @Override
    protected View provideView() {
        return repoNameEditText;
    }
}
