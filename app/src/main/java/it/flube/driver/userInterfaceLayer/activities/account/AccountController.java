/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.GetAccountDetailsResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut.SignOutResponseHandler;
import it.flube.driver.useCaseLayer.account.UseCaseGetAccountDetails;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.signInAndSignOut.UseCaseSignOut;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class AccountController  {
    private final String TAG = "AccountController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public AccountController() {
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
    }

    public void getAccountDetailRequest() {
        Timber.tag(TAG).d("get account detail request STARTED");
        useCaseExecutor.execute(new UseCaseGetAccountDetails(device, new GetAccountDetailsResponseHandler()));
    }

    public void signOutRequest() {
        Timber.tag(TAG).d("signOut STARTED");
        useCaseExecutor.execute(new UseCaseSignOut(device, new SignOutResponseHandler()));
    }

    public void close(){
        useCaseExecutor.shutdown();
        device = null;
    }

}
