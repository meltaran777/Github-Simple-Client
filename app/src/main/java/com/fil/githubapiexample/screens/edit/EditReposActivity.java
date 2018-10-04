package com.fil.githubapiexample.screens.edit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.githubapiexample.MyApplication;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.BaseActivity;
import com.fil.githubapiexample.base.Router;
import com.fil.githubapiexample.util.Const;


public class EditReposActivity extends BaseActivity implements RepositoryEditView {

    EditText repoNameEditText;
    EditText repoDescEditText;

    @InjectPresenter
    RepositoryEditPresenter presenter;

    @ProvidePresenter
    RepositoryEditPresenter providePresenter() {
        return new RepositoryEditPresenter(this.getApplicationContext(),MyApplication.getAppHelper(),
                new GithubApiHelper(MyApplication.getGihubApiClient()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_repos);

        repoNameEditText = findViewById(R.id.repo_edit_text);
        repoDescEditText = findViewById(R.id.desc_full_edit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setRepository(getIntent());
        presenter.initUi();
    }

    @Override
    public void onRepositorySaved(Repository newRepository) {
        setResult(Router.DATA_CHANGED_RESULT_CODE,
                new Intent().putExtra(Const.REPOSITORY_EXTRA_KEY, (Parcelable) newRepository));
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
