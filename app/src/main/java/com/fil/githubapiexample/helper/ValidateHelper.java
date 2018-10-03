package com.fil.githubapiexample.helper;

public class ValidateHelper {

    public boolean isValidPassword(String password) {
        return password != null && (password.length() >= 5);
    }

    public boolean isValidLogin(String login) {
        return login != null && (login.length() >= 3);
    }
}
