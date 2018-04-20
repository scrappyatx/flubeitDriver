/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.appLogging;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;


import com.rollbar.android.Rollbar;

import it.flube.driver.BuildConfig;
import it.flube.driver.deviceLayer.deviceInfo.DeviceDetails;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import timber.log.Timber;

/**
 * Created on 6/14/2017
 * Project : Driver
 */

class RollbarCrashReporting extends Timber.Tree {
    private static final String ROLLBAR_AUTH = "6489dbbc16e943beaebf5c0028ee588a";


    RollbarCrashReporting(Context context, DeviceInfo deviceInfo) {
        //initialize rollbar
        if (!Rollbar.isInit()) {
            Rollbar.init(context, ROLLBAR_AUTH, BuildConfig.BUILD_TYPE+"_"+BuildConfig.VERSION_NAME);
            Rollbar.setPersonData(deviceInfo.getDeviceGUID(), deviceInfo.getMarketName(), null);
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
