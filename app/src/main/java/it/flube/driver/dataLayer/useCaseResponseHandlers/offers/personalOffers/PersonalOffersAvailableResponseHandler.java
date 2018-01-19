/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.personalOffers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferListUpdatedEvent;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PersonalOffersAvailableResponseHandler implements CloudDatabaseInterface.PersonalOffersUpdated {
    private static final String TAG = "PersonalOffersAvailableResponseHandler";

    public void cloudDatabasePersonalOffersUpdated(ArrayList<Batch> offerList) {
        Timber.tag(TAG).d("personal offers available from cloud database");

        AndroidDevice.getInstance().getOfferLists().setPersonalOffers(offerList);
        EventBus.getDefault().post(new PersonalOfferCountUpdatedEvent());
        EventBus.getDefault().post(new PersonalOfferListUpdatedEvent());
        //EventBus.getDefault().postSticky(new PersonalOffersUpdatedEvent(offerList));
    }

}
