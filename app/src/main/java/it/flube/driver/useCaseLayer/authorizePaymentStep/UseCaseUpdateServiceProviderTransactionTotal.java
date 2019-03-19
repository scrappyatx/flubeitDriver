/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import timber.log.Timber;

/**
 * Created on 3/17/2019
 * Project : Driver
 */
public class UseCaseUpdateServiceProviderTransactionTotal implements
        Runnable,
        CloudActiveBatchInterface.ServiceProviderTransactionTotalUpdateResponse {

    private static final String TAG="UseCaseUpdateServiceProviderTransactionId";

    private MobileDeviceInterface device;
    private Response response;

    private ServiceOrderAuthorizePaymentStep orderStep;
    private Driver driver;

    public UseCaseUpdateServiceProviderTransactionTotal(MobileDeviceInterface device, ServiceOrderAuthorizePaymentStep orderStep, Response response){
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
            device.getCloudActiveBatch().updateAuthorizePaymentServiceProviderTransactionTotal(driver, orderStep, this);
        } else {
            // no user
            Timber.tag(TAG).d("...there is no signed in user");
            response.useCaseUpdateServiceProviderTransactionTotalComplete();
            close();
        }
    }

    public void cloudActiveBatchUpdateServiceProviderTransactionTotalComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdateServiceProviderTransactionTotalComplete");
        response.useCaseUpdateServiceProviderTransactionTotalComplete();
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        device = null;
        response = null;
        orderStep = null;
        driver = null;
    }

    public interface Response {
        void useCaseUpdateServiceProviderTransactionTotalComplete();
    }
}

