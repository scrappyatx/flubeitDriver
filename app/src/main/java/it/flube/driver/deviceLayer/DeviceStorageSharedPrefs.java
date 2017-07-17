/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.content.Context;
import android.content.SharedPreferences;

import it.flube.driver.modelLayer.Driver;
import it.flube.driver.useCaseLayer.interfaces.DeviceStorageInterface;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bryan on 5/2/2017.
 */

public class DeviceStorageSharedPrefs implements DeviceStorageInterface {
    private final String TAG = "DeviceStorageSharedPrefs";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    private static final String mPrefsName = "DriverPrefs";
    private static final String mFirstNameField = "firstName";
    private static final String mLastNameField = "lastName";
    private static final String mDisplayNameField = "displayName";
    private static final String mClientIdField = "clientId";
    private static final String mEmailField = "email";
    private static final String mRoleField = "role";
    private static final String mPhotoUrlField = "photoUrl";

    public DeviceStorageSharedPrefs(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(mPrefsName, MODE_PRIVATE);
    }

    private boolean isPrefsDataAvailable() {
       return mPrefs.contains(mFirstNameField) && mPrefs.contains(mLastNameField)
               && mPrefs.contains(mClientIdField) && mPrefs.contains(mEmailField) && mPrefs.contains(mRoleField);
    }

    public void loadRequest(DeviceStorageInterface.LoadResponse response) {
        if (isPrefsDataAvailable()) {
            Driver driver = new Driver();
            driver.setFirstName(mPrefs.getString(mFirstNameField, null));
            driver.setLastName(mPrefs.getString(mLastNameField, null));
            driver.setDisplayName(mPrefs.getString(mDisplayNameField,null));
            driver.setClientId(mPrefs.getString(mClientIdField, null));
            driver.setEmail(mPrefs.getString(mEmailField, null));
            driver.setRole(mPrefs.getString(mRoleField, null));
            driver.setPhotoUrl(mPrefs.getString(mPhotoUrlField,null));
            response.deviceStorageLoadSuccess(driver);
        } else {
            response.deviceStorageLoadFailure("No driver information stored in shared preferences");
        }
    }

    public void saveRequest(Driver driver, DeviceStorageInterface.SaveResponse response) {
        mEditor = mPrefs.edit();
        mEditor.putString(mFirstNameField, driver.getFirstName());
        mEditor.putString(mLastNameField, driver.getLastName());
        mEditor.putString(mDisplayNameField, driver.getDisplayName());
        mEditor.putString(mClientIdField, driver.getClientId());
        mEditor.putString(mEmailField, driver.getEmail());
        mEditor.putString(mRoleField, driver.getRole());
        mEditor.putString(mPhotoUrlField, driver.getPhotoUrl());
        mEditor.apply();
        response.deviceStorageSaveComplete();
    }

    public void deleteRequest(DeviceStorageInterface.DeleteResponse response) {
        mPrefs = mContext.getSharedPreferences(mPrefsName, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.remove(mFirstNameField);
        mEditor.remove(mLastNameField);
        mEditor.remove(mDisplayNameField);
        mEditor.remove(mClientIdField);
        mEditor.remove(mEmailField);
        mEditor.remove(mRoleField);
        mEditor.remove(mPhotoUrlField);
        mEditor.apply();
        response.deviceStorageDeleteComplete();
    }
}
