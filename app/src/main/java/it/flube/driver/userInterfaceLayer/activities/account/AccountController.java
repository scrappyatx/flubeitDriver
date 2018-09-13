/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.account.UseCaseGetAccountDetails;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class AccountController implements
    UseCaseGetAccountDetails.Response {
    private final String TAG = "AccountController";

    private Response response;

    public AccountController() {

    }

    public void getAccountDetailRequest(Response response) {
        Timber.tag(TAG).d("getAccountDetailRequest");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetAccountDetails(AndroidDevice.getInstance(), this));
    }

    public void signOutRequest(AppCompatActivity activity) {
        Timber.tag(TAG).d("signOut STARTED");
        AuthUI.getInstance().signOut(activity);

    }

    public void close(){

    }

    /// UseCaseGetAccountDetails interface
    public void useCaseGetAccountDetailSuccess(Driver driver) {
        Timber.tag(TAG).d("accountDetailSuccess");
        response.getAccountDetailSuccess(driver);

    }

    public void useCaseGetAccountDetailFailure(){
        Timber.tag(TAG).d("accountDetailFailure");
        response.getAccountDetailFailure();
    }

    /// response interface
    public interface Response {
        void getAccountDetailSuccess(Driver driver);

        void getAccountDetailFailure();
    }

}
