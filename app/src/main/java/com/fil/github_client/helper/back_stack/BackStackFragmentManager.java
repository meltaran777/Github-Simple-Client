package com.fil.github_client.helper.back_stack;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.io.Serializable;

import timber.log.Timber;

public class BackStackFragmentManager {
    private final String LOG_TAG = "myLogs";

    /**
     * Reference to fragment manager
     */
    private FragmentManager mFragmentManager;

    /**
     * Last added fragment
     */
    private String mLastAdded;


    public BackStackFragmentManager(@NonNull final FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     * Switch root fragment
     */
    public void switchFragment(@NonNull final Fragment fragment) {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(fragment);
        Timber.tag(LOG_TAG).d("transaction.show(fragment) = %s", fragment.getClass().getName());
        //transaction.hide(mLastAdded);
        transaction.hide(mFragmentManager.findFragmentByTag(mLastAdded));
        Timber.tag(LOG_TAG).d("transaction.hide(mLastAdded) = %s", mLastAdded);
        transaction.commit();
        mLastAdded = fragment.getClass().getName();
    }

    /**
     * Adding child fragment to a root
     */
    public void addChildFragment(@NonNull final Fragment fragment,
                                 final int layoutId,
                                 @NonNull final String tag) {

        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // transaction.add(layoutId, fragment, tag);
        // transaction.commit();

        //since we hide/show. This should only happen initially
        if (!fragment.isAdded()) {
            transaction.add(layoutId, fragment, tag);
        } else {
            transaction.show(fragment);
        }
        if (mLastAdded != null) {
            transaction.hide(mFragmentManager.findFragmentByTag(mLastAdded));
            //transaction.hide(mLastAdded);
        }
        transaction.commit();
        mLastAdded = fragment.getClass().getName();
    }


    /**
     * Add a root fragment
     */
    public void addFragment(@NonNull Fragment fragment, int layoutId) {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();

        //since we hide/show. This should only happen initially
        if (!fragment.isAdded()) {
            transaction.add(layoutId, fragment, fragment.getClass().getName());
        } else {
            transaction.show(fragment);
        }
        if (mLastAdded != null) {
            //transaction.hide(mLastAdded);
            transaction.hide(mFragmentManager.findFragmentByTag(mLastAdded));
        }
        transaction.commit();
        mLastAdded = fragment.getClass().getName();
    }

    /**
     * Pop back stack
     * Function is removing childs that is not used!
     */
    public void popBackStack(@NonNull final String tag) {
        final Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }
}
