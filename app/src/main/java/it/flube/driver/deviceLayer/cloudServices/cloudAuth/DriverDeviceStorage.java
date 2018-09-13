/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudAuth;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import it.flube.driver.modelLayer.entities.driver.Driver;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 9/12/2018
 * Project : Driver
 */
public class DriverDeviceStorage {
    private static final String TAG="DriverDeviceStorage";

    private static final String PREFS_NAME = "DriverDeviceStorage";

    private static final String DRIVER_FIELD = "driverJson";
    private static final String SIGNED_IN_FIELD = "signedIn";
    private static final String IDTOKEN_FIELD = "idToken";

    /// we persist in shared preferences
    private SharedPreferences prefs;

    public DriverDeviceStorage(Context appContext){
        prefs = appContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Timber.tag(TAG).d("   ...created");
    }

    ////
    //// public Driver methods
    ////
    public Driver getDriver() {
        Timber.tag(TAG).d("getDriver()");
        if (getSignedInFromSharedPrefs()) {
            Timber.tag(TAG).d("  ...driver info is stored in shared Prefs");
            return getDriverFromSharedPrefs();
        } else {
            Timber.tag(TAG).d("  ...NO driver info is stored in shared Prefs");
            return null;
        }
    }

    public void setDriver(@NonNull Driver driver, String idToken) {
        saveDriverToSharedPrefs(driver);
        saveIdTokenToSharedPrefs(idToken);
        Timber.tag(TAG).d("setDriver : driver --> " + driver.getClientId() + " name --> " + driver.getNameSettings().getDisplayName());
    }

    public void clearDriver(){
        Timber.tag(TAG).d("clearDriver");
        deleteDriverFromSharedPrefs();
    }

    public Boolean isDriverSaved() {
        Boolean signedIn = getSignedInFromSharedPrefs();
        Timber.tag(TAG).d("isSignedIn --> " + signedIn);
        return signedIn;
    }

    ///
    /// public Id Token methods
    ///

    public String getIdToken() {
        Timber.tag(TAG).d("getIdToken");
        return prefs.getString(IDTOKEN_FIELD,null);
    }

    ////
    ////    Private methods to store/retrieve data
    ////

    private void saveIdTokenToSharedPrefs(String idToken){
        Timber.tag(TAG).d("   ...saveIdTokenToSharedPrefs");
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putString(IDTOKEN_FIELD, idToken);
        editor.apply();
    }

    private void saveDriverToSharedPrefs(Driver driver){
        /// convert driver object to json string
        Timber.tag(TAG).d("   ...saveDriverToSharedPrefs");
        Gson gson = new Gson();
        String driverJson = gson.toJson(driver);

        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putString(DRIVER_FIELD, driverJson);
        editor.putBoolean(SIGNED_IN_FIELD, true);
        editor.apply();
    }

    private Boolean getSignedInFromSharedPrefs(){
        Timber.tag(TAG).d("   ...getSignedInFromSharedPrefs");
        return prefs.getBoolean(SIGNED_IN_FIELD, false);
    }

    private Driver getDriverFromSharedPrefs(){
        Timber.tag(TAG).d("   ...getDriverFromSharedPrefs");
        String driverJson = prefs.getString(DRIVER_FIELD,null);
        Timber.tag(TAG).d("      ...driverJson -> " + driverJson);

        Gson gson = new Gson();

        Driver driver = gson.fromJson(driverJson, Driver.class);
        Timber.tag(TAG).d("      loaded driver from json string");
        Timber.tag(TAG).d("         clientId -> " + driver.getClientId());

        return driver;
    }

    private void deleteDriverFromSharedPrefs(){
        Timber.tag(TAG).d("   ...deleteDriverFromSharedPrefs");
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.remove(DRIVER_FIELD);
        editor.remove(SIGNED_IN_FIELD);
        editor.remove(IDTOKEN_FIELD);
        editor.apply();
    }



}
