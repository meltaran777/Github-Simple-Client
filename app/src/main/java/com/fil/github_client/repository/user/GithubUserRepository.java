package com.fil.github_client.repository.user;

import com.fil.github_client.model.User;

import io.reactivex.Observable;


public interface GithubUserRepository {
    Observable<User> getUser(final String login, final String password);
}
