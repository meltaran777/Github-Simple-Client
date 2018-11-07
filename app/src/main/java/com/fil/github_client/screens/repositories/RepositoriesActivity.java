package com.fil.github_client.screens.repositories;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.github_client.MyApplication;
import com.fil.github_client.R;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.base.ActivityScreenView;
import com.fil.github_client.screens.details.RepositoryDetailsActivity;
import com.fil.github_client.screens.repositories.adapter.RepositoryAdapter;
import com.fil.github_client.network.ErrorResponseHandler;
import com.fil.github_client.repository.github_repositories.GithubRepositoriesInteraction;
import com.fil.github_client.util.Const;

import java.util.List;

public class RepositoriesActivity extends ActivityScreenView implements RepositoriesView {

    private RecyclerView               recyclerView;
    private RepositoryAdapter          mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView noRepositoryTextView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar        progressBar;

    @InjectPresenter
    RepositoriesPresenter presenter;

    @ProvidePresenter
    RepositoriesPresenter provideRepositoriesPresenter() {
        GithubRepositoriesInteraction repositoriesInteraction = new GithubRepositoriesInteraction(
                MyApplication.getGithubRepositoriesRepository(),
                new ErrorResponseHandler());

        return new RepositoriesPresenter(
                this.getApplicationContext(),
                repositoriesInteraction,
                MyApplication.getAppHelper());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositoies);

        progressBar = findViewById(R.id.recycler_progress_bar);
        recyclerView = findViewById(R.id.repo_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        noRepositoryTextView = findViewById(R.id.no_repository_text_view);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RepositoryAdapter(presenter.getData(), presenter);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());

        presenter.setLogin(getIntent());
        presenter.loadRepositories();
    }

    @Override
    protected void onDataChanged(Intent data) {
        presenter.updateItem(data);
    }

    @Override
    protected void onDestroy() {
        mAdapter.setListener(null);
        super.onDestroy();
    }

    @Override
    public void showRepositoryDetailsView(GitRepository gitRepository) {
        Intent intent = new Intent(this, RepositoryDetailsActivity.class);
        intent.putExtra(Const.REPOSITORY_EXTRA_KEY, (Parcelable) gitRepository);
        this.startActivityForResult(intent, Const.DETAILS_REPOSITORY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void showRepositories(List<GitRepository> repositories) {
        mAdapter.setValues(repositories);
    }

    @Override
    public void updateItem(GitRepository gitRepository, int position) {
        mAdapter.update(position, gitRepository);
    }

    @Override
    public void deleteItem(int position) {
        mAdapter.remove(position);
    }

    @Override
    public void setNoRepositoryTextViewVisibility(int visibility) {
        noRepositoryTextView.setVisibility(visibility);
    }

    @Override
    public void showProgress() {
        //progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        //progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.repository_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_action:
                presenter.refresh();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @NonNull
    @Override
    protected View provideView() {
        return recyclerView;
    }
}
