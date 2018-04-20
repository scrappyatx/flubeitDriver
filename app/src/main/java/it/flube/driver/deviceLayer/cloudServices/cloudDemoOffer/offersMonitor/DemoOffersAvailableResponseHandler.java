/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offersMonitor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferOfferListUpdatedEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class DemoOffersAvailableResponseHandler implements
        CloudDemoOfferInterface.DemoOffersUpdated {

    private static final String TAG = "DemoOffersAvailableReponseHandler";

    private final OffersInterface globalOffersList;

    public DemoOffersAvailableResponseHandler(OffersInterface globalOffersList){
        this.globalOffersList = globalOffersList;
    }

    public void cloudDemoOffersUpdated(ArrayList<Batch> offerList) {
        Timber.tag(TAG).d("demo offers available from cloud database");
        globalOffersList.setDemoOffers(offerList);
        EventBus.getDefault().post(new DemoOfferCountUpdatedEvent());
        EventBus.getDefault().post(new DemoOfferOfferListUpdatedEvent());
    }
}
