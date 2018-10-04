package com.fil.githubapiexample.adapter.repository;

import android.support.v7.util.DiffUtil;

import com.fil.githubapiexample.model.Repository;

import java.util.List;

public class RepositoryDiffUtilCallback extends DiffUtil.Callback {
    private final List<Repository> oldList;
    private final List<Repository> newList;

    public RepositoryDiffUtilCallback(List<Repository> oldList, List<Repository> newList) {
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
        Repository oldRepo = oldList.get(oldItemPosition);
        Repository newRepo = newList.get(newItemPosition);
        return String.valueOf(oldRepo.getFullName()).equals(String.valueOf(newRepo.getFullName()));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Repository oldRepo = oldList.get(oldItemPosition);
        Repository newRepo = newList.get(newItemPosition);
        return String.valueOf(oldRepo.getName()).equals(String.valueOf(newRepo.getName()))
                && String.valueOf(oldRepo.getDescription()).equals(String.valueOf(newRepo.getDescription()));
    }
}
