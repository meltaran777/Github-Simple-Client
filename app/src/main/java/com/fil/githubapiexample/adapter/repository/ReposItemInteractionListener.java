package com.fil.githubapiexample.adapter.repository;

import com.fil.githubapiexample.model.Repository;

public interface ReposItemInteractionListener {
    void onReposItemClicked(Repository repository, int position);

    void onReposMenuDeleteItemClicked(Repository repository, int position);
}
