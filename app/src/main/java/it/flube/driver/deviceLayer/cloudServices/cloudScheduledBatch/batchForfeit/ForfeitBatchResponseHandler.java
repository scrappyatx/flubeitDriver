/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit;

import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;

/**
 * Created on 3/31/2018
 * Project : Driver
 */
public class ForfeitBatchResponseHandler implements
        CloudScheduledBatchInterface.BatchForfeitResponse {

    public ForfeitBatchResponseHandler(){

    }

    public void cloudScheduledBatchForfeitSuccess(String batchGuid){

    }

    public void cloudScheduledBatchForfeitFailure(String batchGuid){

    }

    public void cloudScheduledBatchForfeitTimeout(String batchGuid){

    }

    public void cloudScheduledBatchForfeitDenied(String batchGuid, String reason){

    }

}
