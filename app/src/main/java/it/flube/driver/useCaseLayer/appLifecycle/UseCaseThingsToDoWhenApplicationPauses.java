/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.appLifecycle;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 11/19/2017
 * Project : Driver
 */

public class UseCaseThingsToDoWhenApplicationPauses implements
    Runnable,
    CloudDemoOfferInterface.StopMonitoringResponse,
    CloudPersonalOfferInterface.StopMonitoringResponse,
    CloudPublicOfferInterface.StopMonitoringResponse,
    CloudScheduledBatchInterface.StopMonitoringResponse,
    CloudActiveBatchInterface.StopMonitoringResponse {

    private final static String TAG = "UseCaseThingsToDoWhenApplicationPauses";

    private final MobileDeviceInterface device;

    public UseCaseThingsToDoWhenApplicationPauses(MobileDeviceInterface device){
        this.device = device;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (device.getCloudAuth().hasDriver()) {
            //// stop monitoring demo offers, personal offers, public offers, and scheduled batches
            Timber.tag(TAG).d("...device has signed in user, stop monitoring demo offers, public offers, personal offers and scheduled batches");
            device.getCloudDemoOffer().stopMonitoringRequest(this);
            device.getCloudPersonalOffer().stopMonitoringRequest(this);
            device.getCloudPublicOffer().stopMonitoringRequest(this);
            device.getCloudScheduledBatch().stopMonitoringRequest(this);

            if (device.getActiveBatch().hasActiveBatch()) {
                Timber.tag(TAG).d("...device has active batch, DO NOT stop monitoring active batch");
            } else {
                Timber.tag(TAG).d("...device has NO active batch, stop monitoring active batch");
                device.getCloudActiveBatch().stopMonitoringRequest(this);
            }

        } else {
            // do nothing
            Timber.tag(TAG).d("...there is no signed in user, do nothing");
        }
    }

    public void cloudDemoOffersStopMonitoringComplete(){
        Timber.tag(TAG).d("...cloudDemoOffersStopMonitoringComplete");
    }

    public void cloudPersonalOffersStopMonitoringComplete(){
        Timber.tag(TAG).d("...cloudPersonalOffersStopMonitoringComplete");
    }

    public void cloudPublicOffersStopMonitoringComplete(){
        Timber.tag(TAG).d("...cloudPublicOffersStopMonitoringComplete");
    }

    public void cloudScheduledBatchStopMonitoringComplete(){
        Timber.tag(TAG).d("...cloudScheduledBatchStopMonitoringComplete");
    }

    public void cloudActiveBatchStopMonitoringComplete(){
        Timber.tag(TAG).d("...cloudActiveBatchStopMonitoringComplete");
    }


}
