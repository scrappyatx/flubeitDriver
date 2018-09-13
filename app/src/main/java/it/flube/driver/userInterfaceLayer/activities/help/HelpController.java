/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.help;

import android.util.Log;

import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class HelpController {
    private final String TAG = "HelpController";

    public HelpController() {
        Timber.tag(TAG).d("created");
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }

}
