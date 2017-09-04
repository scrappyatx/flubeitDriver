/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.content.Context;
import android.content.SharedPreferences;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import timber.log.Timber;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bryan on 5/2/2017.
 */

public class DeviceStorageSharedPrefs implements DeviceStorageInterface {
    private final String TAG = "DeviceStorageSharedPrefs";

    private static final String PREFS_NAME = "DriverPrefs";

    private static final String FIELD_FIRST_NAME = "firstName";
    private static final String FIELD_LAST_NAME = "lastName";
    private static final String FIELD_DISPLAY_NAME = "displayName";
    private static final String FIELD_CLIENT_ID = "clientId";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PHOTO_URL = "photoUrl";
    private static final String FIELD_IS_DEV = "isDev";
    private static final String FIELD_IS_QA = "isQA";
    private static final String FIELD_PUBLIC_OFFERS_NODE = "publicOffersNode";
    private static final String FIELD_PERSONAL_OFFERS_NODE = "personalOffersNode";
    private static final String FIELD_DEMO_OFFERS_NODE = "demoOffersNode";
    private static final String FIELD_SCHEDULED_BATCHES_NODE = "scheduledBatchesNode";
    private static final String FIELD_ACTIVE_BATCH_NODE = "activeBatchNode";

    private SharedPreferences prefs;

    public DeviceStorageSharedPrefs(Context applicationContext) {
        prefs = applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    private boolean isPrefsDataAvailable() {
       return prefs.contains(FIELD_FIRST_NAME) && prefs.contains(FIELD_LAST_NAME)
               && prefs.contains(FIELD_CLIENT_ID) && prefs.contains(FIELD_EMAIL)
               && prefs.contains(FIELD_DISPLAY_NAME) && prefs.contains(FIELD_PHOTO_URL)
               && prefs.contains(FIELD_IS_DEV) && prefs.contains(FIELD_IS_QA)
               && prefs.contains(FIELD_PUBLIC_OFFERS_NODE) && prefs.contains(FIELD_PERSONAL_OFFERS_NODE)
               && prefs.contains(FIELD_DEMO_OFFERS_NODE) && prefs.contains(FIELD_SCHEDULED_BATCHES_NODE)
               && prefs.contains(FIELD_ACTIVE_BATCH_NODE);
    }

    public void loadRequest(DeviceStorageInterface.LoadResponse response) {
        if (isPrefsDataAvailable()) {
            Driver driver = new Driver();
            driver.setFirstName(prefs.getString(FIELD_FIRST_NAME, null));
            driver.setLastName(prefs.getString(FIELD_LAST_NAME, null));
            driver.setDisplayName(prefs.getString(FIELD_DISPLAY_NAME,null));
            driver.setClientId(prefs.getString(FIELD_CLIENT_ID, null));
            driver.setEmail(prefs.getString(FIELD_EMAIL, null));
            driver.setPhotoUrl(prefs.getString(FIELD_PHOTO_URL,null));
            driver.setIsDev(prefs.getBoolean(FIELD_IS_DEV, false));
            driver.setIsQA(prefs.getBoolean(FIELD_IS_QA, false));
            driver.setPublicOffersNode(prefs.getString(FIELD_PUBLIC_OFFERS_NODE,null));
            driver.setPersonalOffersNode(prefs.getString(FIELD_PERSONAL_OFFERS_NODE,null));
            driver.setDemoOffersNode(prefs.getString(FIELD_DEMO_OFFERS_NODE,null));
            driver.setScheduledBatchesNode(prefs.getString(FIELD_SCHEDULED_BATCHES_NODE,null));
            driver.setActiveBatchNode(prefs.getString(FIELD_ACTIVE_BATCH_NODE,null));

            Timber.tag(TAG).d("loadRequest -> Success");
            Timber.tag(TAG).d("     displayName -> " + driver.getDisplayName());
            Timber.tag(TAG).d("     clientId    -> " + driver.getClientId());
            Timber.tag(TAG).d("     email       -> " + driver.getDisplayName());
            Timber.tag(TAG).d("     photo URL   -> " + driver.getPhotoUrl());

            Timber.tag(TAG).d("     is Dev      -> " + driver.isDev());
            Timber.tag(TAG).d("     is QA       -> " + driver.isQA());
            Timber.tag(TAG).d("     publicOffersNode     -> " + driver.getPublicOffersNode());
            Timber.tag(TAG).d("     personalOffersNode   -> " + driver.getPersonalOffersNode());
            Timber.tag(TAG).d("     demoOffersNode       -> " + driver.getDemoOffersNode());
            Timber.tag(TAG).d("     scheduledBatchesNode -> " + driver.getScheduledBatchesNode());
            Timber.tag(TAG).d("     activeBatchNode      -> " + driver.getActiveBatchNode());

            response.deviceStorageLoadSuccess(driver);
        } else {
            Timber.tag(TAG).d("loadRequest -> Failure ");
            response.deviceStorageLoadFailure("No driver information stored in shared preferences");
        }
    }

    public void saveRequest(Driver driver, DeviceStorageInterface.SaveResponse response) {
        SharedPreferences.Editor editor;
        editor = prefs.edit();

        editor.putString(FIELD_FIRST_NAME, driver.getFirstName());
        editor.putString(FIELD_LAST_NAME, driver.getLastName());
        editor.putString(FIELD_DISPLAY_NAME, driver.getDisplayName());
        editor.putString(FIELD_CLIENT_ID, driver.getClientId());
        editor.putString(FIELD_EMAIL, driver.getEmail());
        editor.putString(FIELD_PHOTO_URL, driver.getPhotoUrl());

        editor.putBoolean(FIELD_IS_DEV, driver.isDev());
        editor.putBoolean(FIELD_IS_QA, driver.isQA());

        editor.putString(FIELD_PUBLIC_OFFERS_NODE, driver.getPublicOffersNode());
        editor.putString(FIELD_PERSONAL_OFFERS_NODE, driver.getPersonalOffersNode());
        editor.putString(FIELD_DEMO_OFFERS_NODE, driver.getDemoOffersNode());
        editor.putString(FIELD_SCHEDULED_BATCHES_NODE, driver.getScheduledBatchesNode());
        editor.putString(FIELD_ACTIVE_BATCH_NODE, driver.getActiveBatchNode());

        editor.apply();

        Timber.tag(TAG).d("saveRequest -> Complete");
        Timber.tag(TAG).d("     displayName -> " + driver.getDisplayName());
        Timber.tag(TAG).d("     clientId    -> " + driver.getClientId());
        Timber.tag(TAG).d("     email       -> " + driver.getDisplayName());
        Timber.tag(TAG).d("     photo URL   -> " + driver.getPhotoUrl());

        Timber.tag(TAG).d("     is Dev      -> " + driver.isDev());
        Timber.tag(TAG).d("     is QA       -> " + driver.isQA());
        Timber.tag(TAG).d("     publicOffersNode     -> " + driver.getPublicOffersNode());
        Timber.tag(TAG).d("     personalOffersNode   -> " + driver.getPersonalOffersNode());
        Timber.tag(TAG).d("     demoOffersNode       -> " + driver.getDemoOffersNode());
        Timber.tag(TAG).d("     scheduledBatchesNode -> " + driver.getScheduledBatchesNode());
        Timber.tag(TAG).d("     activeBatchNode      -> " + driver.getActiveBatchNode());

        response.deviceStorageSaveComplete();
    }

    public void deleteRequest(DeviceStorageInterface.DeleteResponse response) {
        SharedPreferences.Editor editor;

        editor = prefs.edit();
        editor.remove(FIELD_FIRST_NAME);
        editor.remove(FIELD_LAST_NAME);
        editor.remove(FIELD_DISPLAY_NAME);
        editor.remove(FIELD_CLIENT_ID);
        editor.remove(FIELD_EMAIL);
        editor.remove(FIELD_PHOTO_URL);
        editor.remove(FIELD_IS_DEV);
        editor.remove(FIELD_IS_QA);
        editor.remove(FIELD_PUBLIC_OFFERS_NODE);
        editor.remove(FIELD_PERSONAL_OFFERS_NODE);
        editor.remove(FIELD_DEMO_OFFERS_NODE);
        editor.remove(FIELD_SCHEDULED_BATCHES_NODE);
        editor.remove(FIELD_ACTIVE_BATCH_NODE);

        editor.apply();

        Timber.tag(TAG).d("deleteRequest -> Complete");
        response.deviceStorageDeleteComplete();
    }
}
