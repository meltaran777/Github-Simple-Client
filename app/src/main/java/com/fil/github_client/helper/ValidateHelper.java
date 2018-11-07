package com.fil.github_client.helper;

public class ValidateHelper {

    public boolean isValidPassword(String password) {
        return password != null && (password.length() >= 5);
    }

    public boolean isValidLogin(String login) {
        return login != null && (login.length() >= 3);
    }
}
