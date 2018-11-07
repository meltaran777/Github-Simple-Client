package com.fil.github_client.repository.user;

import com.fil.github_client.model.User;
import com.fil.github_client.repository.InteractionListener;

public interface GithubUserInteractionListener extends InteractionListener {
    void onUserLogin(User user);
}
