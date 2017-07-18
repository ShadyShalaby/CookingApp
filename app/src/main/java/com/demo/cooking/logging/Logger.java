package com.demo.cooking.logging;

import android.util.Log;

import com.demo.cooking.BuildConfig;


/**
 * Created by hamdy on 11/21/2016.
 */
public final class Logger {
    /**
     * Prints An Error Log Message with a Tag
     * Shows The log only if the product BUILD_TYPE is debug
     *
     * @param tag     To identify Your message ( Class Simple Name is fine)
     * @param message Message to be shown as error message
     */
    public static void error(String tag, String message) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.e(tag, message);
        }
    }

    /**
     * Prints An Information Log Message with a Tag
     * Shows The log only if the product BUILD_TYPE is debug
     *
     * @param tag     To identify Your message ( Class Simple Name is fine)
     * @param message Information Message to be shown as error message
     */
    public static void info(String tag, String message) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.i(tag, message);
        }

    }

    /**
     * Prints A WTF Log Message with a Tag
     * Shows The log only if the product BUILD_TYPE is debug
     *
     * @param tag     To identify Your message ( Class Simple Name is fine)
     * @param message WTF Message to be shown
     */
    public static void wtf(String tag, String message) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.wtf(tag, message);
        }

    }

    /**
     * Prints A Debug Message with a Tag
     * Shows The log only if the product BUILD_TYPE is debug
     *
     * @param tag     To identify Your message ( Class Simple Name is fine)
     * @param message Debug Message to be shown
     */
    public static void debug(String tag, String message) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.d(tag, message);
        }

    }
}
