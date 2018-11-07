package com.fil.github_client.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import static com.fil.github_client.network.GithubApiInterface.A_COLON;
import static com.fil.github_client.network.GithubApiInterface.EMPTY_STR;
import static com.fil.github_client.network.GithubApiInterface.NEW_LINE;

public class NetworkHelper {

    public boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public String getAuthenticationToken(final String username, final String password) {
        return Base64.encodeToString((username + A_COLON + password).getBytes(), Base64.DEFAULT).replace(NEW_LINE, EMPTY_STR);
    }
}
