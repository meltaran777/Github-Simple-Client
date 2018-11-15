package com.fil.github_client.screens.repositories.adapter;

import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fil.github_client.R;
import com.fil.github_client.base.adapter.RecyclerAdapter;
import com.fil.github_client.model.GitRepository;

import java.util.List;

public class RepositoryAdapter extends RecyclerAdapter<GitRepository, ReposItemViewHolder> {

    private RepositoryListItemInteractionListener listener;

    public RepositoryAdapter(List<GitRepository> values, RepositoryListItemInteractionListener listener) {
        super(values);
        this.listener = listener;
    }

    @Override
    public ReposItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.repository_recycler_item, parent, false);
        ReposItemViewHolder vh = new ReposItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReposItemViewHolder holder, final int position) {
        final GitRepository gitRepository = values.get(position);
        holder.bindViews(gitRepository, listener);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public void setValues(List<GitRepository> newValues) {
        RepositoryDiffUtilCallback repositoryDiffUtilCallback = new RepositoryDiffUtilCallback(values, newValues);
        DiffUtil.DiffResult repositoryDiffResult = DiffUtil.calculateDiff(repositoryDiffUtilCallback);
        values = newValues;
        repositoryDiffResult.dispatchUpdatesTo(this);
    }

    public RepositoryListItemInteractionListener getListener() {
        return listener;
    }

    public void setListener(RepositoryListItemInteractionListener listener) {
        this.listener = listener;
    }
}

