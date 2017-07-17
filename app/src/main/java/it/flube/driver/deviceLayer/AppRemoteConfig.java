/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.useCaseLayer.interfaces.AppRemoteConfigInterface;
import timber.log.Timber;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppRemoteConfig implements AppRemoteConfigInterface {

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile AppRemoteConfig mInstance = new AppRemoteConfig();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private AppRemoteConfig() {
        setRemoteConfigDefaultValues();
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static AppRemoteConfig getInstance() {
        return AppRemoteConfig.Loader.mInstance;
    }


    private static final String TAG = "AppRemoteConfig";

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

    private static final String APP_COLORS_COLOR_PRIMARY_KEY = "app_colors_colorPrimary";
    private static final String APP_COLORS_COLOR_PRIMARY_DARK_KEY = "app_colors_colorPrimaryDark";
    private static final String APP_COLORS_COLOR_ACCENT_KEY = "app_colors_colorAccent";
    private static final String APP_COLORS_COLOR_BUTTON_KEY = "app_colors_colorButton";
    private static final String APP_COLORS_COLOR_ERROR_TEXT_KEY = "app_colors_colorErrorText";
    private static final String APP_COLORS_COLOR_CONTROL_ACTIVATED_KEY = "app_colors_colorControlActivated";
    private static final String APP_COLORS_COLOR_ACTIVITY_BACKGROUND_KEY = "app_colors_colorActivityBackground";
    private static final String APP_COLORS_COLOR_SPLASH_SCEEN_BACKGROUND_KEY = "app_colors_colorSplashScreenBackground";

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private AppRemoteConfigInterface.Response mResponse;


    private void setRemoteConfigDefaultValues() {
        //get instance of firebase remote config
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        Timber.tag(TAG).d("FirebaseRemoteConfig --> got instance");
        // set developer mode if this is a debug build
        // this lets us do more fetches available per hour before throttling occurs
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        // set the default parameter values
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        Timber.tag(TAG).d("FirebaseRemoteConfig --> SET default values");

        if (mFirebaseRemoteConfig.activateFetched()) {
            Timber.tag(TAG).d("FirebaseRemoteConfig --> SET most recent fetched remote values");
        } else {
            Timber.tag(TAG).d("FirebaseRemoteConfig --> DID NOT SET most recent fetched remote values, last fetch status = " + getLastFetchStatusDescription());
        }
    }

    public void getUpdatedValuesFromRemoteServerRequest(@NonNull AppRemoteConfigInterface.Response response) {
        mResponse = response;

        // fetch values -> default expiry is 1 hour
        long cacheExpiration = 3600; // 1 hour in seconds

        // if we are in developer mode, expiry is 0 seconds, so each fetch will retrieve values from the service immediately
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
            Timber.tag(TAG).d("FirebaseRemoteConfig --> developer mode ENABLED, set cacheExpiration to ZERO");
        }

        // fetch parameter values.
        // If cached parameter values are less than cacheExpiration seconds old, then fetch will return cached parameter values.
        // If cached parameter values are MORE than cacheExpiration seconds old, then fetch willl return data from remote config server.
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new FirebaseRemoteConfigCompletionListener());
        Timber.tag(TAG).d("FirebaseRemoteConfig --> fetching updated values from remote server...");
    }

    private String getLastFetchStatusDescription() {
        String result = "UNDETERMINED";
        switch (mFirebaseRemoteConfig.getInfo().getLastFetchStatus()) {
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

    private class FirebaseRemoteConfigCompletionListener implements OnCompleteListener<Void> {

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                mFirebaseRemoteConfig.activateFetched();
                Timber.tag(TAG).d("FirebaseRemoteConfig --> Fetch SUCCESS, new values activated");
                mResponse.getUpdatedValuesFromRemoteServerComplete();
            } else {
                Timber.tag(TAG).d("FirebaseRemoteConfig --> Fetch FAIL, last fetch status = " + getLastFetchStatusDescription());
                try {
                    throw task.getException();
                } catch (FirebaseRemoteConfigFetchException e) {
                    Timber.tag(TAG).d("FirebaseRemoteConfig FETCH EXCEPTION -> " + e.getMessage());
                    Timber.tag(TAG).e(e);
                }
                catch (Exception e) {
                    Timber.tag(TAG).d("FirebaseRemoteConfig unrecognized exception -> " + e.getMessage());
                    Timber.tag(TAG).e(e);
                }
                mResponse.getUpdatedValuesFromRemoteServerComplete();
            }
        }
    }

    public String getDriverProfileUrl() {
        return FirebaseRemoteConfig.getInstance().getString(BASE_URL_KEY) + FirebaseRemoteConfig.getInstance().getString(DRIVER_PROFILE_PATH_KEY);
    }

    public String getRealtimeMessagingAuthTokenUrl() {
        return FirebaseRemoteConfig.getInstance().getString(BASE_URL_KEY) + FirebaseRemoteConfig.getInstance().getString(ABLY_AUTH_TOKEN_PATH_KEY);
    }

    public String getCloudStorageAuthTokenUrl() {
        return FirebaseRemoteConfig.getInstance().getString(BASE_URL_KEY) + FirebaseRemoteConfig.getInstance().getString(FIREBASE_AUTH_TOKEN_PATH_KEY);
    }

    public Boolean getLoggingDebugActive() {
        return FirebaseRemoteConfig.getInstance().getBoolean(LOGGLY_DEBUG_ACTIVE_KEY);
    }

    public Boolean getLoggingReleaseActive() {
        return FirebaseRemoteConfig.getInstance().getBoolean(LOGGLY_RELEASE_ACTIVE_KEY);
    }

    public Boolean getErrorReportingDebugActive() {
        return FirebaseRemoteConfig.getInstance().getBoolean(ROLLBAR_DEBUG_ACTIVE_KEY);
    }

    public Boolean getErrorReportingReleaseActive() {
        return FirebaseRemoteConfig.getInstance().getBoolean(ROLLBAR_RELEASE_ACTIVE_KEY);
    }

    public String getRealtimeMessagingLookingForOffersChannelName() {
        return FirebaseRemoteConfig.getInstance().getString(ABLY_CHANNEL_NAME_LOOKING_FOR_OFFERS_KEY);
    }

    public String getRealtimeMessagingBatchActivityChannelName() {
        return FirebaseRemoteConfig.getInstance().getString(ABLY_CHANNEL_NAME_BATCH_ACTIVITY_KEY);
    }

    public String getRealtimeMessagingLookingForOffersDemoChannelName() {
        return FirebaseRemoteConfig.getInstance().getString(ABLY_CHANNEL_NAME_LOOKING_FOR_OFFERS_DEMO_KEY);
    }

}
