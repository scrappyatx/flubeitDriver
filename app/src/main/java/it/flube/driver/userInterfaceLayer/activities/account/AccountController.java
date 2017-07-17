/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.GetAccountDetailsResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.SignOutResponseHandler;
import it.flube.driver.useCaseLayer.UseCaseGetAccountDetails;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.UseCaseSignOut;
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
