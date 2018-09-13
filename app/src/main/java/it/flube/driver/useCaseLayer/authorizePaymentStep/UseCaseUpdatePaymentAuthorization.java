/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import timber.log.Timber;

/**
 * Created on 9/10/2018
 * Project : Driver
 */
public class UseCaseUpdatePaymentAuthorization implements
        Runnable,
        CloudActiveBatchInterface.PaymentAuthorizationUpdateResponse {
    private static final String TAG="UseCaseUpdatePaymentAuthorization";

    private final MobileDeviceInterface device;
    private final Response response;

    private PaymentAuthorization paymentAuthorization;
    private Driver driver;

    public UseCaseUpdatePaymentAuthorization(MobileDeviceInterface device, PaymentAuthorization paymentAuthorization, Response response){
        this.device = device;
        this.response = response;
        this.paymentAuthorization = paymentAuthorization;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (device.getCloudAuth().hasDriver()) {
            //// get active batch step
            this.driver = device.getCloudAuth().getDriver();
            Timber.tag(TAG).d("...device has signed in user, continue");
            device.getCloudActiveBatch().updatePaymentAuthorizationRequest(driver, paymentAuthorization, this);

        } else {
            // no user
            Timber.tag(TAG).d("...there is no signed in user");
            response.useCaseUpdatePaymentAuthorizationComplete();
        }

    }

    public void cloudActiveBatchUpdatePaymentAuthorizationComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdatePaymentAuthorizationComplete");
        response.useCaseUpdatePaymentAuthorizationComplete();
    }

    public interface Response {
        void useCaseUpdatePaymentAuthorizationComplete();
    }

}
