package com.fil.github_client.repository.github_repositories;

import com.fil.github_client.model.GitRepository;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public interface GithubRepositoriesRepository {
    Observable<List<GitRepository>> getRepositories();

    Observable<GitRepository> editRepository(String owner, String oldRepositoryName, GitRepository newGitRepository);

    Observable<ResponseBody> deleteRepository(String owner, String oldRepositoryName);
}
