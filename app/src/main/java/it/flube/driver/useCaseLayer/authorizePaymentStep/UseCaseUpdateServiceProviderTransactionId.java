/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import timber.log.Timber;

/**
 * Created on 2/21/2019
 * Project : Driver
 */
public class UseCaseUpdateServiceProviderTransactionId implements Runnable {

    private static final String TAG="UseCaseUpdateServiceProviderTransactionId";

    private MobileDeviceInterface device;
    private UseCaseUpdatePaymentAuthorization.Response response;

    private ServiceOrderAuthorizePaymentStep orderStep;
    private Driver driver;

    public UseCaseUpdateServiceProviderTransactionId(MobileDeviceInterface device, ServiceOrderAuthorizePaymentStep orderStep, UseCaseUpdatePaymentAuthorization.Response response){
        this.device = device;
        this.response = response;
        this.orderStep = orderStep;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (device.getCloudAuth().hasDriver()) {
            //// get active batch step
            this.driver = device.getCloudAuth().getDriver();
            Timber.tag(TAG).d("...device has signed in user, continue");
            device.getCloudActiveBatch().updateAuthorizePaymentServiceProviderTransactionId(driver, orderStep);
            response.useCaseUpdatePaymentAuthorizationComplete();
            close();
        } else {
            // no user
            Timber.tag(TAG).d("...there is no signed in user");
            response.useCaseUpdatePaymentAuthorizationComplete();
            close();
        }

    }


    private void close(){
        Timber.tag(TAG).d("close");
        device = null;
        response = null;
        orderStep = null;
        driver = null;
    }

    public interface Response {
        void useCaseUpdateServiceProviderTransactionIdComplete();
    }
}
