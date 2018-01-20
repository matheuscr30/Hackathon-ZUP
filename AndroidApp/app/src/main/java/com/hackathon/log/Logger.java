package com.hackathon.log;

import android.util.Log;

/**
 * Created by Marcus on 02-Dec-17.
 */

public class Logger {

    private final static String TAG = "ZUP";

    private Logger() {
        throw new UnsupportedOperationException();
    }

    public static void debug(String message) {
        Log.d(TAG, message);
    }
}
