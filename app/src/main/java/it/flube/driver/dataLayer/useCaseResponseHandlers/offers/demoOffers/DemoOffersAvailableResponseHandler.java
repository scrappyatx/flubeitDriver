/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferOfferListUpdatedEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class DemoOffersAvailableResponseHandler implements CloudDatabaseInterface.DemoOffersUpdated {
    private static final String TAG = "DemoOffersAvailableReponseHandler";

    public void cloudDatabaseDemoOffersUpdated(ArrayList<Batch> offerList) {

        Timber.tag(TAG).d("demo offers available from cloud database");
        AndroidDevice.getInstance().getOfferLists().setDemoOffers(offerList);
        EventBus.getDefault().post(new DemoOfferCountUpdatedEvent());
        EventBus.getDefault().post(new DemoOfferOfferListUpdatedEvent());

        //EventBus.getDefault().post(new DemoOffersUpdatedEvent(offerList));

    }
}
