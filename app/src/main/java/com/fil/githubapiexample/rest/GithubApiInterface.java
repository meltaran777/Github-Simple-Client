package com.fil.githubapiexample.rest;

import com.fil.githubapiexample.model.Repository;
import com.fil.githubapiexample.model.User;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GithubApiInterface {

    String BASE_URL = "https://api.github.com";

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
    Call<User> login();

    @GET(BASE_URL + USER_URL + REPOS_URL + PARAMETER_TYPE_ALL)
    Call<List<Repository>> getRepositories();

    @POST(BASE_URL + REPOS_URL + SEPARATOR + LB + OWNER + RB + SEPARATOR + LB + NAME + RB)
    Call<Repository> editRepository(@Path(OWNER) String ownerName,
                                    @Path(NAME) String repositoryName,
                                    @Body Repository repository);

    @DELETE(BASE_URL + REPOS_URL + SEPARATOR + LB + OWNER + RB + SEPARATOR + LB + NAME + RB)
    Call<ResponseBody> deleteRepository(@Path(OWNER) String ownerName,
                                        @Path(NAME) String repositoryName);
}
