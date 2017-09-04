/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseBatchSelected implements Runnable {

    private final UseCaseBatchSelected.Response response;
    private final BatchCloudDB batch;

    public UseCaseBatchSelected(BatchCloudDB batch, UseCaseBatchSelected.Response response) {
        this.response = response;
        this.batch = batch;
    }

    public void run(){
        response.batchSelected(batch);
    }

    public interface Response {
        void batchSelected(BatchCloudDB batch);
    }
}
