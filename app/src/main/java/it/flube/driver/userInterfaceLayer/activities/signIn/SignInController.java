/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;


/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class SignInController  {
    private final String TAG = "SignInController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public SignInController() {
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
        Timber.tag(TAG).d("SignInController CREATED");
    }

    public void doSignIn(String userName, String password) {
        Timber.tag(TAG).d("SignIn attempt:");
        Timber.tag(TAG).d("   username = " + userName);
        Timber.tag(TAG).d("   password = " + password);
        //useCaseExecutor.execute(new UseCaseSignInFromUserProfile(userName, password, device, new SignInFromUserProfileResponseHandler()));
    }

    public void close(){
        useCaseExecutor.shutdown();
        device = null;
    }


}
