/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PersonalOfferListUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PublicOffersUpdatedEvent;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PublicOffersAvailableResponseHandler implements CloudDatabaseInterface.PublicOffersUpdated {
    private static final String TAG = "PublicOffersAvailableResponseHandler";

    public void cloudDatabasePublicOffersUpdated(ArrayList<Batch> offerList) {
        Timber.tag(TAG).d("public offers available from cloud database");

        AndroidDevice.getInstance().getOfferLists().setPersonalOffers(offerList);
        EventBus.getDefault().post(new PersonalOfferCountUpdatedEvent());
        EventBus.getDefault().post(new PersonalOfferListUpdatedEvent());

        //EventBus.getDefault().postSticky(new PublicOffersUpdatedEvent(offerList));
    }

}
