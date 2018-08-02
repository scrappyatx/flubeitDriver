/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.appDataStructures;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.net.URL;

import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppUser implements AppUserInterface {
    private static final String TAG = "AppUser";

    private static final String PREFS_NAME = "AppUserPersistence";

    private static final String DRIVER_FIELD = "driverJson";
    private static final String SIGNED_IN_FIELD = "signedIn";
    private static final String IDTOKEN_FIELD = "idToken";

    /// data stored in this global
    //private  Driver driver;
    //private   Boolean signedIn;
    //private   String idToken;

    /// we persist in shared preferences
    private SharedPreferences prefs;

    public AppUser(Context applicationContext) {
        prefs = applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Timber.tag(TAG).d("   ...created");
    }

    ////
    ////  Interface methods to get/set data
    ////

    public Driver getDriver() {
        Timber.tag(TAG).d("getDriver()");
        //if (driver != null) {
        //    Timber.tag(TAG).d("...driver is : " + driver.getNameSettings().getDisplayName());
        //} else {
        //    Timber.tag(TAG).d("...driver is NULL!");
        //}
        //return driver;
        if (getSignedInFromSharedPrefs()) {
            Timber.tag(TAG).d("  ...driver info is stored in shared Prefs");
            return getDriverFromSharedPrefs();
        } else {
            Timber.tag(TAG).w("  ...NO driver info is stored in shared Prefs, this should never happen");
            return null;
        }
    }

    public void setDriver(@NonNull Driver driver) {
        //this.driver = driver;
        //this.signedIn = true;
        saveDriverToSharedPrefs(driver, true);
        Timber.tag(TAG).d("setDriver : driver --> " + driver.getClientId() + " name --> " + driver.getNameSettings().getDisplayName());
    }


    public Boolean isSignedIn() {
        Boolean signedIn = getSignedInFromSharedPrefs();
        Timber.tag(TAG).d("isSignedIn --> " + signedIn);
        return signedIn;
    }


    public String getIdToken() {
        return prefs.getString(IDTOKEN_FIELD,null);
    }

    public void setIdToken(String idToken) {
        saveIdTokenToSharedPrefs(idToken);
    }

    public void clear() {
        Timber.tag(TAG).d("clear()");
        //driver = null;
        //signedIn=false;
        //idToken = null;
        deleteDriverFromSharedPrefs();
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
    private void saveDriverToSharedPrefs(Driver driver, Boolean signedIn){
        /// convert driver object to json string
        Timber.tag(TAG).d("   ...saveDriverToSharedPrefs");
        Gson gson = new Gson();
        String driverJson = gson.toJson(driver);

        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putString(DRIVER_FIELD, driverJson);
        editor.putBoolean(SIGNED_IN_FIELD, signedIn);
        editor.apply();
    }

    private Boolean getSignedInFromSharedPrefs(){
        Timber.tag(TAG).d("   ...getSignedInFromSharedPrefs");
        return prefs.getBoolean(SIGNED_IN_FIELD, false);
    }

    private Driver getDriverFromSharedPrefs(){
        Timber.tag(TAG).d("   ...getDriverFromSharedPrefs");
        String driverJson = prefs.getString(DRIVER_FIELD,null);
        Gson gson = new Gson();
        return gson.fromJson(driverJson, Driver.class);
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

    // AsyncTask<Params, Progress, Result>.
    //    Params – the type (Object/primitive) you pass to the AsyncTask from .execute()
    //    Progress – the type that gets passed to onProgressUpdate()
    //    Result – the type returns from doInBackground()
    // Any of them can be String, Integer, Void, etc.

    private class GetDriverDataTask extends AsyncTask<Void, Void, Driver> {
        protected Driver doInBackground(Void... voids) {
            // code that will run in the background
            //get driver from shared preferences
            String driverJson = prefs.getString(DRIVER_FIELD,null);
            Gson gson = new Gson();
            return gson.fromJson(driverJson, Driver.class);
        }
    }
}
