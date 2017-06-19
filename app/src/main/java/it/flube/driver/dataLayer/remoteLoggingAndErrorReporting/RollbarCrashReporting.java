/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.remoteLoggingAndErrorReporting;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.rollbar.android.Rollbar;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 6/14/2017
 * Project : Driver
 */

public class RollbarCrashReporting extends Timber.Tree {

    public RollbarCrashReporting(Context context) {
        //initialize rollbar
        if (!Rollbar.isInit()) {
            String authToken = context.getResources().getString(R.string.ROLLBAR_AUTH);
            Rollbar.init(context, authToken, BuildConfig.BUILD_TYPE+"_"+BuildConfig.VERSION_NAME);
        }
    }

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        ///
        ///     Only send WARN and ERROR levels to Rollbar, ignore INFO, DEBUG, and VERBOSE
        ///

        String level;
        switch (priority) {
            case Log.INFO:
                return;
            case Log.DEBUG:
                return;
            case Log.VERBOSE:
                return;
            case Log.ERROR:
                level = "error";
                break;
            case Log.WARN:
                level = "warning";
                break;
            default:
                return;
        }

        if (t == null) {
            //report a message
            if (message != null) {
                if (tag == null) {
                    Rollbar.reportMessage(message, level);
                } else {
                    Rollbar.reportMessage(tag + " --> " + message, level);
                }
            }
        } else {
            //report an exception
            if (message != null) {
                if (tag == null) {
                    //we have a message, but no tag
                    Rollbar.reportException(t,level,message);
                } else {
                    //we have a message and a tag
                    Rollbar.reportException(t,level,tag + " --> " + message);
                }
            } else {
                if (tag == null) {
                    //we have no message, and no tag
                    Rollbar.reportException(t,level);
                } else {
                    //we have no message, but we do have a tag
                    Rollbar.reportException(t, level, tag);
                }
            }
        }
    }

}
