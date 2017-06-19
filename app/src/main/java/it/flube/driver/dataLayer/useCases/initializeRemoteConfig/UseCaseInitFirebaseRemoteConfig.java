/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCases.initializeRemoteConfig;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import timber.log.Timber;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS;
import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED;

/**
 * Created on 6/16/2017
 * Project : Driver
 */

public class UseCaseInitFirebaseRemoteConfig implements Runnable {
    private final String TAG = "UseCaseInitFirebaseRemoteConfig";

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private UseCaseInitFirebaseRemoteConfigCallback mCallback;
    private AppCompatActivity mActivity;

    public UseCaseInitFirebaseRemoteConfig(UseCaseInitFirebaseRemoteConfigCallback callback) {
        mCallback = callback;
    }


    public void run() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set developer mode if this is a debug build
        // this lets us do more fetches available per hour before throttling occurs
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        // set the default parameter values
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        Timber.tag(TAG).d("FirebaseRemoteConfig --> set default values");
        //fetch values

        // default expiry is 1 hour
        long cacheExpiration = 3600; // 1 hour in seconds

        // if we are in developer mode, expiry is 0 seconds, so each fetch will retrieve values from the service
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
            Timber.tag(TAG).d("FirebaseRemoteConfig --> developer mode ENABLED, set cacheExpiration to ZERO");
        }

        // fetch parameter values.
        // If cached parameter values are less than cacheExpiration seconds old, then fetch will return cached parameter values.
        // If cached parameter values are MORE than cacheExpiration seconds old, then fetch willl return data from remote config server.
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new RemoteConfigCompletionListener());
    }

    private class RemoteConfigCompletionListener implements OnCompleteListener<Void> {

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                mFirebaseRemoteConfig.activateFetched();
                Timber.tag(TAG).d("FirebaseRemoteConfig --> Fetch SUCCESS, new values activated");
                mCallback.UseCaseInitFirebaseRemoteConfigComplete(true,"Success");
            } else {

                Timber.tag(TAG).d("FirebaseRemoteConfig --> Fetch FAIL");
                int status = mFirebaseRemoteConfig.getInfo().getLastFetchStatus();
                String lastFetchResult = "unknown";
                switch (status) {
                    case LAST_FETCH_STATUS_SUCCESS:
                        lastFetchResult = "Success!";
                        break;
                    case LAST_FETCH_STATUS_FAILURE:
                        lastFetchResult = "Failure";
                        break;
                    case LAST_FETCH_STATUS_NO_FETCH_YET:
                        lastFetchResult = "No Fetch Yet";
                        break;
                    case LAST_FETCH_STATUS_THROTTLED:
                        lastFetchResult = "Thorttled";
                        break;
                }
                Timber.tag(TAG).d("FirebaseRemoteConfig --> LastFetchStatus --> " + lastFetchResult);
                String msg = "unknown error";
                try {
                    throw task.getException();
                } catch (FirebaseRemoteConfigFetchException e) {
                    msg = "Fetch Exception --> " + e.getMessage();
                }
                catch (Exception e) {
                    msg = e.getClass().toString();
                }
                Timber.tag(TAG).e("FirebaseRemoteConfig Fetch Failure --> " + msg);
                mCallback.UseCaseInitFirebaseRemoteConfigComplete(false, msg);
            }
        }
    }
}
