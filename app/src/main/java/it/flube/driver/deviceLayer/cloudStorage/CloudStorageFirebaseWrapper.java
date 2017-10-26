/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudStorage;

import com.google.firebase.storage.StorageReference;

import it.flube.driver.deviceLayer.cloudDatabase.CloudDatabaseFirebaseWrapper;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class CloudStorageFirebaseWrapper {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudStorageFirebaseWrapper instance = new CloudStorageFirebaseWrapper();
    }

    private CloudStorageFirebaseWrapper() {

    }

    public static CloudStorageFirebaseWrapper getInstance() {
        return CloudStorageFirebaseWrapper.Loader.instance;
    }

    private static final String TAG = "CloudStorageFirebaseWrapper";
    private StorageReference storageRef;


}
