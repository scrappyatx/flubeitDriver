/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.storage;

import android.content.Context;
import android.content.SharedPreferences;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;

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

    public DriverStorage(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(mPrefsName, MODE_PRIVATE);
    }

    private boolean isAvailable() {
       return mPrefs.contains(mFirstNameField) && mPrefs.contains(mLastNameField) && mPrefs.contains(mClientIdField) && mPrefs.contains(mEmailField);
    }

    public void load(DriverSingleton driver, DriverStorageRepositoryCallback callback) {
        if (isAvailable()) {
            driver.setFirstName(mPrefs.getString(mFirstNameField, null));
            driver.setLastName(mPrefs.getString(mLastNameField, null));
            driver.setClientId(mPrefs.getString(mClientIdField, null));
            driver.setEmail(mPrefs.getString(mEmailField, null));
            driver.setLoaded(true);
            callback.loadDriverSuccess(driver);
        } else {
           driver.setLoaded(false);
           callback.loadDriverFailure("No driver information stored in shared preferences");
        }
    }

    public void save(DriverSingleton driver, DriverStorageRepositoryCallback callback) {
            if (driver.isLoaded()) {
                mEditor = mPrefs.edit();
                mEditor.putString(mFirstNameField, driver.getFirstName());
                mEditor.putString(mLastNameField, driver.getLastName());
                mEditor.putString(mClientIdField, driver.getClientId());
                mEditor.putString(mEmailField, driver.getEmail());
                mEditor.apply();
            }
            callback.saveDriverSuccess();
    }

    public void delete(DriverStorageRepositoryCallback callback) {
        mPrefs = mContext.getSharedPreferences(mPrefsName, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.remove(mFirstNameField);
        mEditor.remove(mLastNameField);
        mEditor.remove(mClientIdField);
        mEditor.remove(mEmailField);
        mEditor.apply();
        callback.deleteDriverSuccess();
    }
}
