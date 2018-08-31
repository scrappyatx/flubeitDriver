/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.firebaseInitialization;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.DRIVER_DB_DEMO;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.DRIVER_DB_DEV;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.DRIVER_DB_PROD;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.DRIVER_DB_STAGING;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.USER_PROFILE_DB_DEMO;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.USER_PROFILE_DB_DEV;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.USER_PROFILE_DB_PROD;
import static it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseEnvironmentConstants.USER_PROFILE_DB_STAGING;

/**
 * Created on 4/15/2018
 * Project : Driver
 */
public class FirebaseDbInitialization {

    private static final String TAG = "FirebaseDbInitialization";

    public static void initializeDriverDb(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
       FirebaseDatabase.getInstance(getFirebaseDriverDb(targetEnvironment)).setPersistenceEnabled(false);
       FirebaseDatabase.getInstance(getFirebaseDriverDb(targetEnvironment)).goOnline();
       Timber.tag(TAG).d("persistence enabled & online");
    }

    public static String getFirebaseDriverDb(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        Timber.tag(TAG).d("targetEnvironment -> " + targetEnvironment.toString());
        String driverDb;
        switch (targetEnvironment)   {
            case PRODUCTION:
                driverDb = DRIVER_DB_PROD;
                break;
            case DEMO:
                driverDb =  DRIVER_DB_DEMO;
                break;
            case STAGING:
                driverDb =  DRIVER_DB_STAGING;
                break;
            case DEVELOPMENT:
                driverDb =  DRIVER_DB_DEV;
                break;
            default:
                driverDb =  DRIVER_DB_DEV;
                Timber.tag(TAG).w("should never get here, targetEnvironment -> " + targetEnvironment.toString());
                break;
        }
        return driverDb;
    }

    public static String getFirebaseUserProfileDb(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        Timber.tag(TAG).d("targetEnvironment -> " + targetEnvironment.toString());
        String profileDb;
        switch (targetEnvironment)   {
            case PRODUCTION:
                profileDb = USER_PROFILE_DB_PROD;
                break;
            case DEMO:
                profileDb =  USER_PROFILE_DB_DEMO;
                break;
            case STAGING:
                profileDb =  USER_PROFILE_DB_STAGING;
                break;
            case DEVELOPMENT:
                profileDb =  USER_PROFILE_DB_DEV;
                break;
            default:
                profileDb =  USER_PROFILE_DB_DEV;
                Timber.tag(TAG).w("should never get here, targetEnvironment -> " + targetEnvironment.toString());
                break;
        }
        return profileDb;
    }

}
