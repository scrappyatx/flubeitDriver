/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferListUpdatedEvent;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PublicOffersAvailableResponseHandler implements CloudDatabaseInterface.PublicOffersUpdated {
    private static final String TAG = "PublicOffersAvailableResponseHandler";

    public void cloudDatabasePublicOffersUpdated(ArrayList<Batch> offerList) {
        Timber.tag(TAG).d("public offers available from cloud database");

        Timber.tag(TAG).d("   ...offerList size -> " + offerList.size());
        AndroidDevice.getInstance().getOfferLists().setPublicOffers(offerList);
        EventBus.getDefault().post(new PublicOfferCountUpdatedEvent());
        EventBus.getDefault().post(new PublicOfferListUpdatedEvent());

        //EventBus.getDefault().postSticky(new PublicOffersUpdatedEvent(offerList));
    }

}
