package com.fil.githubapiexample.rest.helper;

import android.util.Base64;

import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.model.User;
import com.fil.githubapiexample.rest.GithubApiClient;
import com.fil.githubapiexample.rest.GithubApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fil.githubapiexample.rest.GithubApiInterface.A_COLON;
import static com.fil.githubapiexample.rest.GithubApiInterface.EMPTY_STR;
import static com.fil.githubapiexample.rest.GithubApiInterface.NEW_LINE;
import static com.fil.githubapiexample.rest.helper.GitHubHelperErrorCallback.LOGIN_ERROR;
import static com.fil.githubapiexample.rest.helper.GitHubHelperErrorCallback.SAVE_REPOSITORY_ERROR;
import static com.fil.githubapiexample.rest.helper.GitHubHelperErrorCallback.USER_REPOS_LOAD_ERROR;

public class GithubApiHelper {

    private GithubApiClient    githubApiClient;
    private GithubApiInterface githubApiInterface;

    private GitHubHelperCallback      gitHubHelperCallback;
    private GitHubHelperErrorCallback gitHubHelperErrorCallback;

    public GithubApiHelper(final GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
        this.githubApiInterface = githubApiClient.getGithubApiInterface();
    }

    public void login(final String login, final String password) {
        final String authenticationToken = getAuthenticationToken(login, password);

        githubApiClient.setAuthenticationToken(authenticationToken);

        githubApiInterface.login().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    final User user = response.body();
                    if (gitHubHelperCallback != null)
                        gitHubHelperCallback.onLogin(user, authenticationToken);
                } else {
                    if (gitHubHelperErrorCallback != null)
                        gitHubHelperErrorCallback.onError(LOGIN_ERROR, String.valueOf(response.message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String getAuthenticationToken(final String username, final String password) {
        return Base64.encodeToString((username + A_COLON + password).getBytes(), Base64.DEFAULT).replace(NEW_LINE, EMPTY_STR);
    }


    public void loadRepositories() {
        githubApiInterface.getRepositories().enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()) {
                    final List<Repository> repositories = response.body();
                    if (gitHubHelperCallback != null)
                        gitHubHelperCallback.onRepositoriesLoaded(repositories);
                } else {
                    if (gitHubHelperErrorCallback != null)
                        gitHubHelperErrorCallback.onError(USER_REPOS_LOAD_ERROR, String.valueOf(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void editRepository(String owner, String oldRepositoryName, Repository newRepository) {
        githubApiInterface.editRepository(owner, oldRepositoryName, newRepository).enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                if (response.isSuccessful()) {
                    final Repository repository = response.body();
                    if (gitHubHelperCallback != null)
                        gitHubHelperCallback.onRepositoryEdited(repository);
                } else {
                    if (gitHubHelperErrorCallback != null)
                        gitHubHelperErrorCallback.onError(SAVE_REPOSITORY_ERROR, String.valueOf(response.message()));
                }
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public GitHubHelperErrorCallback getGitHubHelperErrorCallback() {
        return gitHubHelperErrorCallback;
    }

    public void setGitHubHelperErrorCallback(GitHubHelperErrorCallback gitHubHelperErrorCallback) {
        this.gitHubHelperErrorCallback = gitHubHelperErrorCallback;
    }

    public GitHubHelperCallback getGitHubHelperCallback() {
        return gitHubHelperCallback;
    }

    public void setGitHubHelperCallback(final GitHubHelperCallback gitHubHelperCallback) {
        this.gitHubHelperCallback = gitHubHelperCallback;
    }
}
