/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchMonitor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchCountUpdateEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchListUpdateEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 7/25/2017
 * Project : Driver
 */

public class ScheduledBatchesAvailableResponseHandler implements CloudScheduledBatchInterface.ScheduledBatchesUpdated {
    private static final String TAG = "ScheduledBatchesAvailableResponseHandler";

    private OffersInterface offersList;

    public ScheduledBatchesAvailableResponseHandler(OffersInterface offersList){
        this.offersList = offersList;
    }

    public void cloudScheduledBatchesUpdated(ArrayList<Batch> batchList) {
        Timber.tag(TAG).d("scheduled batches available from cloud database");

        //AndroidDevice.getInstance().getOfferLists().setScheduledBatches(batchList);
        offersList.setScheduledBatches(batchList);
        EventBus.getDefault().post(new ScheduledBatchCountUpdateEvent());
        EventBus.getDefault().post(new ScheduledBatchListUpdateEvent());

        //EventBus.getDefault().postSticky(new ScheduledBatchesAvailableResponseHandler.ScheduledBatchUpdateEvent(batchList));
    }


}
