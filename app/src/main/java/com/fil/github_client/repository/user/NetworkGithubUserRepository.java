package com.fil.github_client.repository.user;

import com.fil.github_client.helper.AppHelper;
import com.fil.github_client.model.User;
import com.fil.github_client.network.GithubApiClient;
import com.fil.github_client.repository.NetworkGithubRepository;

import io.reactivex.Observable;

public class NetworkGithubUserRepository extends NetworkGithubRepository implements GithubUserRepository {

    public NetworkGithubUserRepository(GithubApiClient githubApiClient, AppHelper appHelper) {
        super(githubApiClient, appHelper);
    }

    @Override
    public Observable<User> getUser(String login, String password) {
        final String authenticationToken =
                appHelper.getNetworkHelper().generateAuthenticationToken(login, password);

        githubApiClient.setAuthenticationToken(authenticationToken);

        return githubApiInterface
                .login()
                .compose(appHelper.getRxHelper().async());
    }
}
