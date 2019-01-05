/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudConfig;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import it.flube.driver.BuildConfig;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.libbatchdata.builders.ContactPersonBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.ContactPerson;
import timber.log.Timber;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED;

import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_PRIVACY_URL_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_TERMS_URL_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_BATCH_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_USER_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CAN_SMS_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CAN_VOICE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CONTACT_NUMBER_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_NAME_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.LOGGLY_DEBUG_ACTIVE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.LOGGLY_RELEASE_ACTIVE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.ROLLBAR_DEBUG_ACTIVE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.ROLLBAR_RELEASE_ACTIVE_KEY;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class FirebaseRemoteConfigWrapper implements
        CloudConfigInterface,
        OnCompleteListener<Void> {

    private static final String TAG = "FirebaseRemoteConfigWrapper";

    private FirebaseRemoteConfig remoteConfig;
    private TargetEnvironmentConstants.TargetEnvironment targetEnvironment;
    private long cacheExpiry;

    public FirebaseRemoteConfigWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        this.targetEnvironment = targetEnvironment;
        getFirebaseRemoteConfigInstance();
        setDefaultValues();
        activateLastFetchedValues();
        getUpdatedValuesFromCloud();
    }

    private void getFirebaseRemoteConfigInstance(){
        //get instance of firebase remote config
        remoteConfig = FirebaseRemoteConfig.getInstance();
        Timber.tag(TAG).d("got instance");

        // set developer mode if this is a debug build
        // this lets us do more fetches available per hour before throttling occurs
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        remoteConfig.setConfigSettings(configSettings);
        Timber.tag(TAG).d("set config settings");
    }

    private void setDefaultValues(){
        // set the default parameter values
        //remoteConfig.setDefaults(R.xml.remote_config_defaults);
        remoteConfig.setDefaults(FirebaseRemoteConfigDefaults.getDefaults(targetEnvironment));
        Timber.tag(TAG).d("set default values");

        //set value for cache expiry
        // if we are in developer mode, expiry is 0 seconds, so each fetch will retrieve values from the service immediately
        if (remoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiry = 60 * 1;   //1 minutes
            Timber.tag(TAG).d("developer mode TRUE, cacheExpiry (seconds) = " + cacheExpiry);
        } else {
            cacheExpiry = 60 * 60 * 12; //12 hours
            Timber.tag(TAG).d("developer mode FALSE, cacheExpiry (seconds) = " + cacheExpiry);
        }
    }

    private void activateLastFetchedValues() {
        if (remoteConfig.activateFetched()) {
            Timber.tag(TAG).d("most recent fetched remote values were successfully set");
        } else {
            Timber.tag(TAG).d("most recent fetched remote values were NOT successfully set...");
            Timber.tag(TAG).d("   ...last fetch status = " + getLastFetchStatusDescription());

            Long fetchTimeMillis = remoteConfig.getInfo().getFetchTimeMillis();
            if (fetchTimeMillis == -1){
                Timber.tag(TAG).d("   ...no fetch attempt has been made yet");
            } else {
                Date timestamp = new Date(fetchTimeMillis);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                String formatted = format.format(timestamp);
                Timber.tag(TAG).d("   ...last fetch time = " + formatted);
            }
        }
    }

    private String getLastFetchStatusDescription() {
        String result;
        switch (remoteConfig.getInfo().getLastFetchStatus()) {
            case LAST_FETCH_STATUS_SUCCESS:
                result = "SUCCESS";
                break;
            case LAST_FETCH_STATUS_FAILURE:
                result =  "FAILURE";
                break;
            case LAST_FETCH_STATUS_THROTTLED:
                result =  "THROTTLED";
                break;
            case LAST_FETCH_STATUS_NO_FETCH_YET:
                result =  "NO FETCH YET";
                break;
            default:
                result = "UNDETERMINED";
                break;
        }
        return result;
    }

    private void getUpdatedValuesFromCloud() {
        // fetch parameter values.
        // If cached parameter values are less than cacheExpiry seconds old, then fetch will return cached parameter values.
        // If cached parameter values are MORE than cacheExpiry seconds old, then fetch will return data from remote config server.
        remoteConfig.fetch(cacheExpiry).addOnCompleteListener(this);
        Timber.tag(TAG).d("fetching updated values from cloud server...");
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   ...onComplete");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
            //remoteConfig.activateFetched();
            //Timber.tag(TAG).d("         ...new values activated");
        } else {
            Timber.tag(TAG).d("      ...FAILURE, last fetch status = " + getLastFetchStatusDescription());
            try {
                throw task.getException();
            } catch (FirebaseRemoteConfigFetchException e) {
                Timber.tag(TAG).w("         ...FirebaseRemoteConfigException -> " + e.getMessage());
                Timber.tag(TAG).e(e);
            }
            catch (Exception e) {
                Timber.tag(TAG).w("         ...FETCH EXCEPTION -> " + e.getMessage());
                Timber.tag(TAG).e(e);
            }
        }
    }

    /// privacy & terms url
    public String getDriverPrivacyUrl(){
        return remoteConfig.getString(DRIVER_PRIVACY_URL_KEY);
    }

    public String getDriverTermsUrl(){
        return remoteConfig.getString(DRIVER_TERMS_URL_KEY);
    }

    //// flube it support
    public ContactPerson getFlubeItSupportContactPerson(){
        return new ContactPersonBuilder.Builder(targetEnvironment)
                .contactRole(ContactPerson.ContactRole.FLUBEIT_SUPPORT)
                .displayIconUrl(remoteConfig.getString(FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY))
                .displayName(remoteConfig.getString(FLUBE_IT_SUPPORT_DISPLAY_NAME_KEY))
                .dialPhoneNumber(remoteConfig.getString(FLUBE_IT_SUPPORT_CONTACT_NUMBER_KEY))
                .canSMS(remoteConfig.getBoolean(FLUBE_IT_SUPPORT_CAN_SMS_KEY))
                .canVoice(remoteConfig.getBoolean(FLUBE_IT_SUPPORT_CAN_VOICE_KEY))
                .build();
    }


    //// logging settings

    public Boolean getLoggingDebugActive() {
        return remoteConfig.getBoolean(LOGGLY_DEBUG_ACTIVE_KEY);
    }

    public Boolean getLoggingReleaseActive() {
        return remoteConfig.getBoolean(LOGGLY_RELEASE_ACTIVE_KEY);
    }

    public Boolean getErrorReportingDebugActive() {
        return remoteConfig.getBoolean(ROLLBAR_DEBUG_ACTIVE_KEY);
    }

    public Boolean getErrorReportingReleaseActive() {
        return remoteConfig.getBoolean(ROLLBAR_RELEASE_ACTIVE_KEY);
    }

    //// database settings

    public String getCloudDatabaseBaseNodePublicOffers() {
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS);
    }

    public String getCloudDatabaseBaseNodePersonalOffers(){
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS);
    }

    public String getCloudDatabaseBaseNodeDemoOffers(){
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS);
    }

    public String getCloudDatabaseBaseNodeScheduledBatches(){
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES);
    }

    public String getCloudDatabaseBaseNodeActiveBatch(){
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH);
    }

    public String getCloudDatabaseBaseNodeBatchData() {
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_BATCH_DATA);
    }

    public String getCloudDatabaseBaseNodeUserData() {
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_USER_DATA);
    }

    public String getCloudDatabaseBaseNodeDeviceData() {
        return remoteConfig.getString(FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA);
    }

}
