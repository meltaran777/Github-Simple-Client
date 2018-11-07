package com.fil.github_client.screens.repositories.adapter;

import com.fil.github_client.model.GitRepository;

public interface RepositoryListItemInteractionListener {
    void onRepositoryItemClicked(GitRepository gitRepository, int position);

    void onRepositoryMenuDeleteItemClicked(GitRepository gitRepository, int position);
}
