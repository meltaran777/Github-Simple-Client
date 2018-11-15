package com.fil.github_client.helper.back_stack;

import android.support.v4.app.Fragment;

import java.io.Serializable;

public interface BackStackListener {
    void onBackStackEmpty();

    void onRootFragmentChangedBack(Fragment fragment);
}
