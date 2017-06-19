/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.R;
import it.flube.driver.dataLayer.network.HttpDriverProfile;
import it.flube.driver.dataLayer.storage.DriverStorage;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.signIn.UseCaseSignInFromNetwork;
import it.flube.driver.modelLayer.useCases.driver.signOut.UseCaseSignOut;
import it.flube.driver.modelLayer.useCases.driver.signOut.UseCaseSignOutCallback;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivityInterface;
import it.flube.driver.userInterfaceLayer.activityNavigation.ActivityNavigatorSingleton;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoLoginActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.accountActivity.ProfileDetailNotAvailableEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.accountActivity.ProfileDetailWasUpdatedEvent;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class AccountController implements UseCaseSignOutCallback {
    private final String TAG = "AccountController";
    private Context mContext;
    private AccountActivityInterface mCallback;


    public AccountController(Context context, AccountActivityInterface callback) {

        mContext = context;
        mCallback = callback;

        Timber.tag(TAG).d("AccountController CREATED");
        //get current user profile and send a client update event
        if (DriverSingleton.getInstance().isSignedIn()) {
            String firstName = DriverSingleton.getInstance().getFirstName();
            String lastName = DriverSingleton.getInstance().getLastName();
            String email = DriverSingleton.getInstance().getEmail();
            String clientId = DriverSingleton.getInstance().getClientId();
            String role = DriverSingleton.getInstance().getRole();

            mCallback.ProfileDetailUpdate(firstName,lastName,email,role,clientId);
            Timber.tag(TAG).d(TAG, "*** posted ProfileDetailWasUpdated on EventBus");
        } else {
            mCallback.ProfileDetailNotAvailable(mContext.getResources().getString(R.string.account_profile_details_default));
            Timber.tag(TAG).d(TAG, "*** posted ProfileDetailNotAvailable on EventBus");
        }

    }

    public void signOut() {
        //user wants to sign out
        ExecutorService useCaseExec = Executors.newSingleThreadExecutor();
        useCaseExec.execute(new UseCaseSignOut(new DriverStorage(mContext), this ));
        useCaseExec.shutdown();
        Timber.tag(TAG).d("signOut STARTED");
    }

    ///
    /// Use Case Callbacks --> void signOutSuccess();
    ///

    public void signOutSuccess() {
        Timber.tag(TAG).d("signOut COMPLETE --> SUCCESS");
        ActivityNavigatorSingleton.getInstance().gotoActivityLogin(mContext);
    }
}
