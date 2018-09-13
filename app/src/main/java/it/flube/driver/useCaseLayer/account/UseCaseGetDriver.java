/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.account;

import android.os.Handler;
import android.os.Looper;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 9/11/2018
 * Project : Driver
 */
public class UseCaseGetDriver implements Runnable {
    private static final String TAG="UseCaseGetDriver";

    private final MobileDeviceInterface device;
    private final Response response;
    private final Driver driver;

    public UseCaseGetDriver(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
        this.driver = device.getCloudAuth().getDriver();
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        //TODO don't understand why i need a looper to return on the main thread - but i do
        Handler handler = new Handler(Looper.getMainLooper());

        if (driver!=null) {
            Timber.tag(TAG).d("   driver signed in");
            handler.post(new Runnable() {
                public void run() {
                    // UI code goes here
                    Timber.tag(TAG).d("   response on main thread");
                    Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
                    response.useCaseGetDriverSuccess(driver);
                }
            });

        } else {
            Timber.tag(TAG).d("   no driver");
            handler.post(new Runnable() {
                public void run() {
                    // UI code goes here
                    Timber.tag(TAG).d("   response on main thread");
                    Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
                    response.useCaseGetDriverFailure();
                }
            });

        }
    }

    public interface Response {
        void useCaseGetDriverSuccess(Driver driver);

        void useCaseGetDriverFailure();
    }
}
