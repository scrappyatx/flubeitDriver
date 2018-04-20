/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.appDataStructures;

import android.support.annotation.NonNull;

import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import timber.log.Timber;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppUser implements AppUserInterface {
    private static final String TAG = "AppUser";

    private  Driver driver;
    private   Boolean signedIn;
    private   Batch activeBatch;
    private   String idToken;

    public AppUser() {
        clear();
    }

    public Driver getDriver() {
        Timber.tag(TAG).d("getDriver()");
        if (driver != null) {
            Timber.tag(TAG).d("...driver is : " + driver.getNameSettings().getDisplayName());
        } else {
            Timber.tag(TAG).d("...driver is NULL!");
        }
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
