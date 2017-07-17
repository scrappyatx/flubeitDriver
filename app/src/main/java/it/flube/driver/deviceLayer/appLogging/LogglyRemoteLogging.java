/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.appLogging;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.inrista.loggliest.Loggly;

import java.util.UUID;

import it.flube.driver.BuildConfig;
import timber.log.Timber;

/**
 * Created on 6/17/2017
 * Project : Driver
 */

class LogglyRemoteLogging extends Timber.Tree {
    private static final String LOGGLY_AUTH = "46832b1c-b4cc-4440-b06a-382e4a958bc4";

    LogglyRemoteLogging(Context context) {
        String uniqueID = UUID.randomUUID().toString();
        int uploadIntervalCount = 1000;     // 1000 log entries
        int uploadIntervalSecs = 1800;      // 30 minutes

        // if this is a debug build, then we want much more frequent updating
        if (BuildConfig.DEBUG) {
            uploadIntervalCount = 10;
            uploadIntervalSecs = 3;
        }

        Loggly.with(context, LOGGLY_AUTH)
                .appendDefaultInfo(true)
                .appendStickyInfo("deviceSessionId",uniqueID)
                .uploadIntervalLogCount(uploadIntervalCount)
                .uploadIntervalSecs(uploadIntervalSecs)
                .maxSizeOnDisk(500000)
                .init();
    }

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        ///
        ///     Send everything to loggly
        ///
        String key = tag;
        if (key == null) {
          key = "no key";
        }

        String msg = message;
        if (msg == null) {
            msg = "no message";
        }

        switch (priority) {
            case Log.INFO:
                Loggly.i(key, msg, t);
                return;
            case Log.DEBUG:
                Loggly.d(key, msg, t);
                return;
            case Log.VERBOSE:
                Loggly.v(key, msg, t);
                return;
            case Log.ERROR:
                Loggly.e(key, msg, t);
                break;
            case Log.WARN:
                Loggly.w(key, msg, t);
                break;
            default:
                break;
        }

    }

}