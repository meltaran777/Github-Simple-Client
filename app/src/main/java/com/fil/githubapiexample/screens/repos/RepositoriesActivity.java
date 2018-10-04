package com.fil.githubapiexample.screens.repos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.fil.githubapiexample.MyApplication;
import com.fil.githubapiexample.R;
import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.rest.helper.GithubApiHelper;
import com.fil.githubapiexample.screens.base.BaseActivity;
import com.fil.githubapiexample.adapter.repository.RepositoryAdapterImpl;
import com.fil.githubapiexample.adapter.repository.ReposItemViewHolder;
import com.fil.githubapiexample.adapter.repository.RepositoryAdapter;

import java.util.List;

public class RepositoriesActivity extends BaseActivity implements RepositoriesView {

    private RecyclerView                              recyclerView;
    private RecyclerView.Adapter<ReposItemViewHolder> mAdapter;
    private RecyclerView.LayoutManager                layoutManager;

    private ProgressBar progressBar;

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

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setLogin(getIntent());
        presenter.loadRepositories();
    }

    @Override
    public void onDataChanged(Intent data) {
        presenter.updateUi(data);
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        mAdapter = new RepositoryAdapterImpl(repositories, presenter);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateItem(Repository repository, int position) {
        if (mAdapter instanceof RepositoryAdapter) {
            RepositoryAdapter adapter = (RepositoryAdapter) mAdapter;
            adapter.update(position, repository);
        }
    }

    @Override
    public void deleteItem(int position) {
        if (mAdapter instanceof RepositoryAdapter) {
            RepositoryAdapter adapter = (RepositoryAdapter) mAdapter;
            adapter.remove(position);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    protected View provideView() {
        return recyclerView;
    }
}
