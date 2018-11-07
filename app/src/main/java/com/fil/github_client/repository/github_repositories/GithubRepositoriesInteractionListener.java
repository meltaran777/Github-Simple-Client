package com.fil.github_client.repository.github_repositories;

import com.fil.github_client.model.GitRepository;
import com.fil.github_client.repository.InteractionListener;

import java.util.List;

public interface GithubRepositoriesInteractionListener extends InteractionListener {
    void onRepositoriesLoaded(List<GitRepository> repositories);

    void onRepositoryDeleted(GitRepository gitRepository);

    void onRepositoryEdited(GitRepository newGitRepository);
}
