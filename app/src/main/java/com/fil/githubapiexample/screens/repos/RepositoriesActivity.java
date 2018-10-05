package com.fil.githubapiexample.screens.repos;

import android.content.Intent;
import android.os.Bundle;
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
import com.fil.githubapiexample.MyApplication;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.base.BaseActivity;
import com.fil.githubapiexample.adapter.repository.RepositoryAdapter;

import java.util.List;

public class RepositoriesActivity extends BaseActivity implements RepositoriesView {

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
        return new RepositoriesPresenter(this.getApplicationContext(), MyApplication.getAppHelper(),
                new GithubApiHelper(MyApplication.getGihubApiClient()));
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setLogin(getIntent());
        presenter.loadRepositories();
    }

    @Override
    protected void onDestroy() {
        mAdapter.setListener(null);
        super.onDestroy();
    }

    @Override
    public void onDataChanged(Intent data) {
        presenter.updateItem(data);
    }

    @Override
    public void onRepositoriesLoaded(List<Repository> repositories) {
        mAdapter.setValues(repositories);
    }

    @Override
    public void updateItem(Repository repository, int position) {
        mAdapter.update(position, repository);
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
