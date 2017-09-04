/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class ActiveBatchPresentResponseHandler implements CloudDatabaseInterface.LoadActiveBatchResponse {
    private static final String TAG = "ActiveBatchPresentResponseHandler";

    public void cloudDatabaseActiveBatchLoaded(Batch activeBatch) {
        Timber.tag(TAG).d("active batch available in cloud database");
        EventBus.getDefault().postSticky(new ActiveBatchAvailableEvent(activeBatch));
    }

    public static class ActiveBatchAvailableEvent {
        private Batch activeBatch;

        public ActiveBatchAvailableEvent(Batch activeBatch){
            this.activeBatch = activeBatch;
        }

        public Batch getActiveBatch() {
            return activeBatch;
        }

    }

    public void cloudDatabaseNoActiveBatchAvailable() {
        Timber.tag(TAG).d("no active batch in cloud database");
        EventBus.getDefault().postSticky(new NoActiveBatchEvent());
    }

    public static class NoActiveBatchEvent {
        public NoActiveBatchEvent(){

        }
    }

}
