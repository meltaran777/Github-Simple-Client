package com.fil.github_client.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fil.github_client.network.GithubApiInterface.AUTHORIZATION_HEADER_NAME;
import static com.fil.github_client.network.GithubApiInterface.AUTHORIZATION_HEADER_PREFIX_BASIC;
import static com.fil.github_client.network.GithubApiInterface.BASE_URL;

public class GithubApiClient {

    private String authenticationToken;

    private GithubApiInterface mGithubApiInterface;

    public GithubApiClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient
                .addInterceptor(this::addHeader)
                .addInterceptor(logging)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mGithubApiInterface = retrofit.create(GithubApiInterface.class);
    }

    private okhttp3.Response addHeader(Interceptor.Chain chain) throws IOException {
        if (isValidToken(authenticationToken)) {
            try {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_PREFIX_BASIC + authenticationToken)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();

                return chain.proceed(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chain.proceed(chain.request());
    }

    private boolean isValidToken(String authenticationToken) {
        return authenticationToken != null && !authenticationToken.isEmpty();
    }

    public GithubApiInterface getGithubApiInterface() {
        return mGithubApiInterface;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}

