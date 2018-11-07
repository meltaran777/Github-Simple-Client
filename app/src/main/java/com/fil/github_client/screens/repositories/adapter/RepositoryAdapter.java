package com.fil.github_client.screens.repositories.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fil.github_client.R;
import com.fil.github_client.model.GitRepository;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<ReposItemViewHolder> {

    private List<GitRepository> values;

    private RepositoryListItemInteractionListener listener;

    public RepositoryAdapter(List<GitRepository> data, RepositoryListItemInteractionListener listener) {
        this.listener = listener;
        values = data;
    }

    @Override
    public ReposItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.repo_recycler_item, parent, false);
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


    public void add(int position, GitRepository item) {
        values.add(position, item);
        notifyItemInserted(position);
    }


    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    public void update(int position, GitRepository gitRepository) {
        values.set(position, gitRepository);
        notifyItemChanged(position);
    }

    public void setValues(List<GitRepository> newValues){
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

