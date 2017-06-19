/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.content.Context;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.R;
import it.flube.driver.dataLayer.messaging.RemoteServerMessaging;
import it.flube.driver.dataLayer.network.HttpDriverProfile;
import it.flube.driver.dataLayer.storage.DriverStorage;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.signIn.UseCaseSignInFromNetwork;
import it.flube.driver.modelLayer.useCases.driver.signIn.UseCaseSignInFromNetworkCallback;
import it.flube.driver.userInterfaceLayer.activities.signIn.LoginActivityInterface;
import it.flube.driver.userInterfaceLayer.activityNavigation.ActivityNavigatorSingleton;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.loginActivity.LoginResultWasUpdatedEvent;
import timber.log.Timber;


/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class LoginController implements UseCaseSignInFromNetworkCallback {
    private final String TAG = "LoginController";
    private static String mSIGNIN_URL_KEY;

    private Context mContext;
    private LoginActivityInterface mCallback;

    public LoginController(Context context, LoginActivityInterface callback) {
        mSIGNIN_URL_KEY = context.getResources().getString(R.string.login_url_key);
        mContext = context;
        mCallback = callback;
        Timber.tag(TAG).d("LoginController CREATED");
    }

    public void signIn(String username, String password) {
        String url = FirebaseRemoteConfig.getInstance().getString(mSIGNIN_URL_KEY);

        Timber.tag(TAG).d("url = " + url);
        Timber.tag(TAG).d("username = " + username);
        Timber.tag(TAG).d("password = " + password);

        ExecutorService useCaseExec = Executors.newSingleThreadExecutor();
        useCaseExec.execute(new UseCaseSignInFromNetwork(new HttpDriverProfile(), url , username, password, this, new DriverStorage(mContext) ));
        useCaseExec.shutdown();

        Timber.tag(TAG).d("*** UseCaseSignInFromNetwork STARTED");
    }

    ///
    /// Use Case Callbacks --> UseCaseSignInFromNetworkCallback
    ///
    public void signInFromNetworkSuccess() {
        Timber.tag(TAG).d(TAG, "*** UseCaseSignInFromNetwork COMPLETED -> SUCCESS");
        DriverSingleton driver = DriverSingleton.getInstance();
        Timber.tag(TAG).i(TAG, "*** Successfully signed in as user -> " + driver.getFirstName() + " " + driver.getLastName());

        String serverUrl = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
        String clientId = DriverSingleton.getInstance().getClientId();
        String lookingForOffersChannelName = "LookingForOffers";
        String batchActivityChannelName = "BatchActivity";

        //RemoteServerMessaging.getInstance().setConnectionValues(serverUrl,clientId,lookingForOffersChannelName,batchActivityChannelName);

        ActivityNavigatorSingleton.getInstance().gotoActivityHome(mContext);
    }

    public void signInFromNetworkFailure(String failureMessage) {
        Timber.tag(TAG).d("*** UseCaseSignInFromNetwork COMPLETED -> FAILURE");
        Timber.tag(TAG).i("*** Login failure -> " + failureMessage);
        mCallback.LoginResultUpdate(failureMessage);
    }

    public void signInFromNetworkAuthenticationFailure(String failureMessage) {
        Timber.tag(TAG).d("*** UseCaseSignInFromNetwork COMPLETED -> AUTHENTICATION FAILURE");
        Timber.tag(TAG).i("*** Login authentication failure -> " + failureMessage);
        mCallback.LoginResultUpdate("The email address or password is incorrect");
    }

}
