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
import it.flube.driver.R;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import timber.log.Timber;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class FirebaseRemoteConfigWrapper implements
        CloudConfigInterface,
        OnCompleteListener<Void> {

    private static final String TAG = "FirebaseRemoteConfigWrapper";

    private static final String BASE_URL_KEY = "base_url";
    private static final String FIREBASE_AUTH_TOKEN_PATH_KEY="firebase_auth_token_path";
    private static final String DRIVER_PROFILE_PATH_KEY = "driver_profile_path";
    private static final String ABLY_AUTH_TOKEN_PATH_KEY = "ably_auth_token_path";

    private static final String LOGGLY_DEBUG_ACTIVE_KEY = "loggly_debug_active";
    private static final String LOGGLY_RELEASE_ACTIVE_KEY = "loggly_release_active";
    private static final String ROLLBAR_DEBUG_ACTIVE_KEY = "rollbar_debug_active";
    private static final String ROLLBAR_RELEASE_ACTIVE_KEY = "rollbar_release_active";

    private static final String ABLY_CHANNEL_NAME_LOOKING_FOR_OFFERS_KEY = "ably_channel_name_looking_for_offers";
    private static final String ABLY_CHANNEL_NAME_BATCH_ACTIVITY_KEY="ably_channel_name_batch_activity";
    private static final String ABLY_CHANNEL_NAME_LOOKING_FOR_OFFERS_DEMO_KEY="ably_channel_name_looking_for_offers_demo";

    private static final String FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS = "firebase_database_base_node_public_offers";
    private static final String FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS = "firebase_database_base_node_personal_offers";
    private static final String FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS = "firebase_database_base_node_demo_offers";
    private static final String FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES = "firebase_database_base_node_scheduled_batches";
    private static final String FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH = "firebase_database_base_node_active_batch";
    private static final String FIREBASE_DATABASE_BASE_NODE_BATCH_DATA = "firebase_database_base_node_batch_data";
    private static final String FIREBASE_DATABASE_BASE_NODE_USER_DATA = "firebase_database_base_node_user_data";
    private static final String FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA = "firebase_database_base_node_device_data";


    private static final String APP_COLORS_COLOR_PRIMARY_KEY = "app_colors_colorPrimary";
    private static final String APP_COLORS_COLOR_PRIMARY_DARK_KEY = "app_colors_colorPrimaryDark";
    private static final String APP_COLORS_COLOR_ACCENT_KEY = "app_colors_colorAccent";
    private static final String APP_COLORS_COLOR_BUTTON_KEY = "app_colors_colorButton";
    private static final String APP_COLORS_COLOR_ERROR_TEXT_KEY = "app_colors_colorErrorText";
    private static final String APP_COLORS_COLOR_CONTROL_ACTIVATED_KEY = "app_colors_colorControlActivated";
    private static final String APP_COLORS_COLOR_ACTIVITY_BACKGROUND_KEY = "app_colors_colorActivityBackground";
    private static final String APP_COLORS_COLOR_SPLASH_SCEEN_BACKGROUND_KEY = "app_colors_colorSplashScreenBackground";

    private FirebaseRemoteConfig remoteConfig;
    private long cacheExpiry;

    public FirebaseRemoteConfigWrapper(){
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
        remoteConfig.setDefaults(R.xml.remote_config_defaults);
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
        String result = "UNDETERMINED";
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
                Timber.tag(TAG).w("         ...FETCH EXCEPTION -> " + e.getMessage());
                Timber.tag(TAG).e(e);
            }
            catch (Exception e) {
                Timber.tag(TAG).w("         ...FETCH EXCEPTION -> " + e.getMessage());
                Timber.tag(TAG).e(e);
            }
        }
    }


    public String getDriverProfileUrl() {
        return remoteConfig.getString(BASE_URL_KEY) + remoteConfig.getString(DRIVER_PROFILE_PATH_KEY);
    }

    public String getRealtimeMessagingAuthTokenUrl() {
        return remoteConfig.getString(BASE_URL_KEY) + remoteConfig.getString(ABLY_AUTH_TOKEN_PATH_KEY);
    }

    public String getCloudStorageAuthTokenUrl() {
        return FirebaseRemoteConfig.getInstance().getString(BASE_URL_KEY) + remoteConfig.getString(FIREBASE_AUTH_TOKEN_PATH_KEY);
    }

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

    public String getRealtimeMessagingLookingForOffersChannelName() {
        return remoteConfig.getString(ABLY_CHANNEL_NAME_LOOKING_FOR_OFFERS_KEY);
    }

    public String getRealtimeMessagingBatchActivityChannelName() {
        return remoteConfig.getString(ABLY_CHANNEL_NAME_BATCH_ACTIVITY_KEY);
    }

    public String getRealtimeMessagingLookingForOffersDemoChannelName() {
        return remoteConfig.getString(ABLY_CHANNEL_NAME_LOOKING_FOR_OFFERS_DEMO_KEY);
    }

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
