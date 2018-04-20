/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.firebaseRealtimeDatabase;

import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;

/**
 * Created on 4/15/2018
 * Project : Driver
 */
public class FirebaseRealtimeDatabaseWrapper {

    private static final String TAG = "FirebaseRealtimeDatabaseWrapper";

    public static void initializeDb(){
       FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       FirebaseDatabase.getInstance().goOnline();
       Timber.tag(TAG).d("persistence enabled & online");
    }

}
