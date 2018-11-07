package com.fil.github_client.screens.repositories.adapter;

import android.support.v7.util.DiffUtil;

import com.fil.github_client.model.GitRepository;

import java.util.List;

public class RepositoryDiffUtilCallback extends DiffUtil.Callback {
    private final List<GitRepository> oldList;
    private final List<GitRepository> newList;

    public RepositoryDiffUtilCallback(List<GitRepository> oldList, List<GitRepository> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        GitRepository oldRepo = oldList.get(oldItemPosition);
        GitRepository newRepo = newList.get(newItemPosition);
        return String.valueOf(oldRepo.getFullName()).equals(String.valueOf(newRepo.getFullName()));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        GitRepository oldRepo = oldList.get(oldItemPosition);
        GitRepository newRepo = newList.get(newItemPosition);
        return String.valueOf(oldRepo.getName()).equals(String.valueOf(newRepo.getName()))
                && String.valueOf(oldRepo.getDescription()).equals(String.valueOf(newRepo.getDescription()));
    }
}
