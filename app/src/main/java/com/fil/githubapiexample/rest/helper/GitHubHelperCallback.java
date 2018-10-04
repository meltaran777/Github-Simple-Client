package com.fil.githubapiexample.rest.helper;

import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.model.User;

import java.util.ArrayList;
import java.util.List;

public interface GitHubHelperCallback {
    void onLogin(User user, String authEncode);

    void onRepositoriesLoaded(List<Repository> repositories);

    void onRepoDeleted();

    void onRepositoryEdited(Repository newRepository);
}


