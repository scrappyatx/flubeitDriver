/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import timber.log.Timber;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppUser implements AppUserInterface {
    private static final String TAG = "AppUser";

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile AppUser mInstance = new AppUser();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private AppUser() {
        clear();
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static AppUser getInstance() {
        return AppUser.Loader.mInstance;
    }


    ///
    ///     all class variables are static so there is only one across all instances (and there will only be one instance)
    ///
    private Driver driver;
    private Boolean signedIn;
    private Batch activeBatch;
    private String idToken;


    public Driver getDriver() {
        Timber.tag(TAG).d("getDriver()");
        return driver;
    }

    public void setDriver(@NonNull Driver driver) {
        this.driver = driver;
        this.signedIn = true;
        Timber.tag(TAG).d("setDriver : driver --> " + driver.getClientId() + " name --> " + driver.getNameSettings().getDisplayName());
    }

    public Boolean isSignedIn() {
        Timber.tag(TAG).d("isSignedIn --> " + signedIn);
        return signedIn;
    }

    public void setSignedIn(Boolean signedIn) {
        this.signedIn = signedIn;
        Timber.tag(TAG).d("setSignedIn --> " + signedIn);
    }

    public Boolean hasActiveBatch() {
        Timber.tag(TAG).d("hasActiveBatch --> " + (activeBatch != null));
        return (activeBatch != null);
    }

    public Batch getActiveBatch() {
        Timber.tag(TAG).d("getActiveBatch --> " + activeBatch.getGuid());
        return activeBatch;
    }

    public void setActiveBatch(@NonNull Batch batch) {
        Timber.tag(TAG).d("setActiveBatch --> " + activeBatch.getGuid());
        activeBatch = batch;
    }

    public void clearActiveBatch(){
        Timber.tag(TAG).d("clearActiveBatch()");
        activeBatch = null;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void clear() {
        driver = null;
        signedIn=false;
        activeBatch = null;
        idToken = null;
        Timber.tag(TAG).d("clear()");
    }
}
