package com.fil.github_client.repository;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.network.GithubApiClient;
import com.fil.github_client.network.GithubApiInterface;

public abstract class NetworkGithubRepository {

    protected final GithubApiClient    githubApiClient;
    protected final GithubApiInterface githubApiInterface;

    protected final AppHelper appHelper;

    public NetworkGithubRepository(final GithubApiClient githubApiClient, AppHelper appHelper) {
        this.githubApiClient = githubApiClient;
        this.githubApiInterface = githubApiClient.getGithubApiInterface();
        this.appHelper = appHelper;
    }
}
