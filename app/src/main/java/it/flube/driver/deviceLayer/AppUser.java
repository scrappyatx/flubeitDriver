/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.Batch;
import it.flube.driver.modelLayer.Driver;
import it.flube.driver.useCaseLayer.interfaces.AppUserInterface;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppUser implements AppUserInterface {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile AppUser mInstance = new AppUser();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private AppUser() {}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static AppUser getInstance() {
        return AppUser.Loader.mInstance;
    }


    ///
    ///     all class variables are static so there is only one across all instances (and there will only be one instance)
    ///
    private static Driver mDriver;
    private static Batch mActiveBatch;
    private static Boolean mSignedIn;
    private static Boolean mHasActiveBatch;
    private static Boolean mActiveBatchOID;
    private static Boolean developerToolsMenuEnabled;


    public Driver getDriver() {
        return mDriver;
    }

    public void setDriver(@NonNull Driver driver) {
        mDriver = driver;
        mSignedIn = true;
        developerToolsMenuEnabled = true;
        mHasActiveBatch=false;
        mActiveBatchOID=false;
    }

    public Boolean isSignedIn() {
        return mSignedIn;
    }

    public void setSignedIn(Boolean signedIn) {
        mSignedIn = signedIn;
    }

    public Boolean hasActiveBatch() {
        return mHasActiveBatch;
    }

    public void setHasActiveBatch(Boolean hasActiveBatch) {
        mHasActiveBatch = hasActiveBatch;
    }

    public Boolean getActiveBatchOID() {
        return mActiveBatchOID;
    }

    public void setActiveBatchOID(Boolean activeBatchOID) {
        mActiveBatchOID = activeBatchOID;
    }

    public Batch getActiveBatch() { return mActiveBatch; }

    public void setActiveBatch(Batch batch) { mActiveBatch = batch;}

    public void setDeveloperToolsMenuEnabled(Boolean developerToolsMenuEnabled ) {
        this.developerToolsMenuEnabled = developerToolsMenuEnabled;
        //// TODO: 7/14/2017 Need to add logic to this method to set this value based on role returned from profile management server
    }

    public Boolean isDeveloperToolsMenuEnabled(){
        return developerToolsMenuEnabled;
    }

    public void clear() {
        mDriver = null;
        mSignedIn=false;
        mHasActiveBatch=false;
        mActiveBatchOID=false;
        developerToolsMenuEnabled = false;
    }
}
