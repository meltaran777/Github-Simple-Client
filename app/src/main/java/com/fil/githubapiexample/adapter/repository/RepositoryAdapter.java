package com.fil.githubapiexample.adapter.repository;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fil.githubapiexample.R;
import com.fil.githubapiexample.model.Repository;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<ReposItemViewHolder> {

    private List<Repository> values;

    private ReposItemInteractionListener listener;

    public RepositoryAdapter(List<Repository> data, ReposItemInteractionListener listener) {
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
        final Repository repository = values.get(position);
        holder.bindViews(repository, listener);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public void add(int position, Repository item) {
        values.add(position, item);
        notifyItemInserted(position);
    }


    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    public void update(int position, Repository repository) {
        values.set(position, repository);
        notifyItemChanged(position);
    }

    public void setValues(List<Repository> newValues){
        RepositoryDiffUtilCallback repositoryDiffUtilCallback = new RepositoryDiffUtilCallback(values, newValues);
        DiffUtil.DiffResult repositoryDiffResult = DiffUtil.calculateDiff(repositoryDiffUtilCallback);
        values = newValues;
        repositoryDiffResult.dispatchUpdatesTo(this);
    }

    public ReposItemInteractionListener getListener() {
        return listener;
    }

    public void setListener(ReposItemInteractionListener listener) {
        this.listener = listener;
    }
}

