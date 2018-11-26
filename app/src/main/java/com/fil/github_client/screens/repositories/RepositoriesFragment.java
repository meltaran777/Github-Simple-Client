package com.fil.github_client.screens.repositories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.fil.github_client.R;
import com.fil.github_client.base.FragmentScreenView;
import com.fil.github_client.base.RepositoryViewController;
import com.fil.github_client.base.adapter.RecyclerAdapter;
import com.fil.github_client.base.back_stack.BackStackProvider;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.screens.details.RepositoryDetailsFragment;
import com.fil.github_client.screens.repositories.adapter.ReposItemViewHolder;
import com.fil.github_client.screens.repositories.adapter.RepositoryAdapter;
import com.fil.github_client.util.Const;

import java.util.List;

public class RepositoriesFragment extends FragmentScreenView implements RepositoriesView, RepositoryViewController {

    private RecyclerView                                        recyclerView;
    private RecyclerAdapter<GitRepository, ReposItemViewHolder> mAdapter;
    private RecyclerView.LayoutManager                          layoutManager;

    private TextView noRepositoryTextView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar        progressBar;

    Fragment repositoryDetailsFragment;

    private String login;

    @InjectPresenter
    RepositoriesPresenter presenter;

    public static RepositoriesFragment newInstance(String login) {
        RepositoriesFragment fragment = new RepositoriesFragment();
        Bundle args = new Bundle();
        args.putString(Const.LOGIN_EXTRA_KEY, login);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.view_repositoies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(Const.LOGIN_EXTRA_KEY)) {
            login = getArguments().getString(Const.LOGIN_EXTRA_KEY);
        } else {
            throw new IllegalArgumentException("No Login Extra!");
        }

        if (savedInstanceState != null)
            if (getActivity() instanceof BackStackProvider) {
                repositoryDetailsFragment = ((BackStackProvider) getActivity())
                        .getBackStackManager().findFragment(RepositoryDetailsFragment.class);
            }


        progressBar = view.findViewById(R.id.recycler_progress_bar);
        recyclerView = view.findViewById(R.id.repo_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        noRepositoryTextView = view.findViewById(R.id.no_repository_text_view);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RepositoryAdapter(presenter.getData(), presenter);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());

        presenter.loadRepositories();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((RepositoryAdapter) mAdapter).setListener(null);
    }

    @Override
    public void onRepositoryEdited(Intent data) {
        presenter.updateItem(data);
        if (repositoryDetailsFragment instanceof RepositoryViewController) {
            ((RepositoryViewController) repositoryDetailsFragment).onRepositoryEdited(data);
        }
    }

    @Override
    public void showRepositoryDetailsView(GitRepository gitRepository) {
        repositoryDetailsFragment = RepositoryDetailsFragment.newInstance(gitRepository);
        if (getActivity() instanceof BackStackProvider) {
            ((BackStackProvider) getActivity()).getBackStackManager().addChildFragment(repositoryDetailsFragment, R.id.main_activity_content);
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.repository_list_menu, menu);
        presenter.setLogin(wrapLoginIntoIntent(login));
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

    private Intent wrapLoginIntoIntent(String login) {
        Intent data = new Intent();
        data.putExtra(Const.LOGIN_EXTRA_KEY, login);
        return data;
    }
}
