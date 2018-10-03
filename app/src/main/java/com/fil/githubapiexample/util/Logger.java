package com.fil.githubapiexample.util;

import android.util.Log;

public class Logger {

    public static void log(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void log(String msg) {
        Log.d("DebugTag", msg);
    }
}
