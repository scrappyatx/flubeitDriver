/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PersonalOfferListUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchCountUpdateEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchListUpdateEvent;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 7/25/2017
 * Project : Driver
 */

public class ScheduledBatchesAvailableResponseHandler implements CloudDatabaseInterface.ScheduledBatchesUpdated {
    private static final String TAG = "ScheduledBatchesAvailableResponseHandler";

    public void cloudDatabaseScheduledBatchesUpdated(ArrayList<Batch> batchList) {
        Timber.tag(TAG).d("scheduled batches available from cloud database");

        AndroidDevice.getInstance().getOfferLists().setScheduledBatches(batchList);
        EventBus.getDefault().post(new ScheduledBatchCountUpdateEvent());
        EventBus.getDefault().post(new ScheduledBatchListUpdateEvent());

        //EventBus.getDefault().postSticky(new ScheduledBatchesAvailableResponseHandler.ScheduledBatchUpdateEvent(batchList));
    }


}
