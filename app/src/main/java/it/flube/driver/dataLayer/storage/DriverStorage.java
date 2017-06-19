/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.storage;

import android.content.Context;
import android.content.SharedPreferences;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryDeleteCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryLoadCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositorySaveCallback;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bryan on 5/2/2017.
 */

public class DriverStorage implements DriverStorageRepository {
    private final String TAG = "DriverStorageRepository";
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    private static final String mPrefsName = "DriverPrefs";
    private static final String mFirstNameField = "firstName";
    private static final String mLastNameField = "lastName";
    private static final String mClientIdField = "clientId";
    private static final String mEmailField = "email";
    private static final String mRoleField = "role";

    public DriverStorage(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(mPrefsName, MODE_PRIVATE);
    }

    private boolean isPrefsDataAvailable() {
       return mPrefs.contains(mFirstNameField) && mPrefs.contains(mLastNameField)
               && mPrefs.contains(mClientIdField) && mPrefs.contains(mEmailField) && mPrefs.contains(mRoleField);
    }

    public void load(DriverStorageRepositoryLoadCallback callback) {
        DriverSingleton driver = DriverSingleton.getInstance();
        if (isPrefsDataAvailable()) {
            driver.setFirstName(mPrefs.getString(mFirstNameField, null));
            driver.setLastName(mPrefs.getString(mLastNameField, null));
            driver.setClientId(mPrefs.getString(mClientIdField, null));
            driver.setEmail(mPrefs.getString(mEmailField, null));
            driver.setRole(mPrefs.getString(mRoleField, null));
            driver.setSignedIn(true);
            callback.loadDriverSuccess();
        } else {
           callback.loadDriverFailure("No driver information stored in shared preferences");
        }
    }

    public void save(DriverStorageRepositorySaveCallback callback) {
        DriverSingleton driver = DriverSingleton.getInstance();
        if (driver.isSignedIn()) {
            mEditor = mPrefs.edit();
            mEditor.putString(mFirstNameField, driver.getFirstName());
            mEditor.putString(mLastNameField, driver.getLastName());
            mEditor.putString(mClientIdField, driver.getClientId());
            mEditor.putString(mEmailField, driver.getEmail());
            mEditor.putString(mRoleField, driver.getRole());
            mEditor.apply();
            callback.saveDriverSuccess();
        } else {
            callback.saveDriverFailure("No driver information available to save");
        }

    }

    public void delete(DriverStorageRepositoryDeleteCallback callback) {
        mPrefs = mContext.getSharedPreferences(mPrefsName, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.remove(mFirstNameField);
        mEditor.remove(mLastNameField);
        mEditor.remove(mClientIdField);
        mEditor.remove(mEmailField);
        mEditor.remove(mRoleField);
        mEditor.apply();
        callback.deleteDriverSuccess();
    }
}
