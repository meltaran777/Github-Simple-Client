package com.fil.github_client.helper.back_stack;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.Deque;
import java.util.LinkedList;

public class BackStack {

    public final String mRootFragment;

    final Deque<String> mStackItems;

    public BackStack(@NonNull final Fragment rootFragment) {
        mRootFragment = rootFragment.getClass().getName();
        mStackItems = new LinkedList<>();
    }

    public String pop() {
        if (isEmpty()) return null;
        return mStackItems.pop();
    }

    public void push(@NonNull final String id) {
        mStackItems.push(id);
    }


    public boolean isEmpty() {
        return mStackItems.isEmpty();
    }
}
