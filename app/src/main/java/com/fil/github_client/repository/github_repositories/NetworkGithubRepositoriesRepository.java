package com.fil.github_client.repository.github_repositories;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.network.GithubApiClient;
import com.fil.github_client.repository.NetworkGithubRepository;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class NetworkGithubRepositoriesRepository extends NetworkGithubRepository implements GithubRepositoriesRepository {

    public NetworkGithubRepositoriesRepository(GithubApiClient githubApiClient, AppHelper appHelper) {
        super(githubApiClient, appHelper);
    }

    @Override
    public Observable<List<GitRepository>> getRepositories() {
        return githubApiInterface
                .getRepositories()
                .compose(appHelper.getRxHelper().async());
    }

    @Override
    public Observable<GitRepository> editRepository(String owner, String oldRepositoryName, GitRepository newGitRepository) {
        return githubApiInterface
                .editRepository(owner, oldRepositoryName, newGitRepository)
                .compose(appHelper.getRxHelper().async());
    }

    @Override
    public Observable<Response<Void>> deleteRepository(String owner, String repositoryName) {
        return githubApiInterface
                .deleteRepository(owner, repositoryName)
                .compose(appHelper.getRxHelper().async());
    }
}
