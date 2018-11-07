package com.fil.github_client.network;

import com.fil.github_client.BuildConfig;
import com.fil.github_client.model.GitRepository;
import com.fil.github_client.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GithubApiInterface {

    String BASE_URL = BuildConfig.API_ENDPOINT;

    String    REPOS_URL                         = "/repos";
    String    USER_URL                          = "/user";
    String    SEPARATOR                         = "/";
    String    PARAMETER_TYPE_ALL                = "?type=all";
    String    AUTHORIZATION_HEADER_NAME         = "Authorization";
    String    AUTHORIZATION_HEADER_PREFIX_BASIC = "Basic ";
    String    MEDIA_TYPE_JSON                   = "application/json; charset=utf-8";
    MediaType JSON                              = MediaType.parse(MEDIA_TYPE_JSON);
    String    A_COLON                           = ":";
    String    NEW_LINE                          = "\n";
    String    EMPTY_STR                         = "";
    String    LB                                = "{";
    String    RB                                = "}";
    String    OWNER                             = "owner";
    String    NAME                              = "name";

    @GET(USER_URL)
    Observable<User> login();

    @GET(BASE_URL + USER_URL + REPOS_URL + PARAMETER_TYPE_ALL)
    Observable<List<GitRepository>> getRepositories();

    @POST(BASE_URL + REPOS_URL + SEPARATOR + LB + OWNER + RB + SEPARATOR + LB + NAME + RB)
    Observable<GitRepository> editRepository(@Path(OWNER) String ownerName,
                                             @Path(NAME) String repositoryName,
                                             @Body GitRepository gitRepository);

    @DELETE(BASE_URL + REPOS_URL + SEPARATOR + LB + OWNER + RB + SEPARATOR + LB + NAME + RB)
    Observable<ResponseBody> deleteRepository(@Path(OWNER) String ownerName,
                                        @Path(NAME) String repositoryName);
}
