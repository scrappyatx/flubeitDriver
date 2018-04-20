/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offersMonitor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferListUpdatedEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PersonalOffersAvailableResponseHandler implements
        CloudPersonalOfferInterface.PersonalOffersUpdated {

    private static final String TAG = "PersonalOffersAvailableResponseHandler";

    private final OffersInterface globalOffersList;

    public PersonalOffersAvailableResponseHandler(OffersInterface globalOffersList){
        this.globalOffersList = globalOffersList;
    }

    public void cloudPersonalOffersUpdated(ArrayList<Batch> offerList) {
        Timber.tag(TAG).d("personal offers available from cloud database");

        Timber.tag(TAG).d("   ...offerList size -> " + offerList.size());

        globalOffersList.setPersonalOffers(offerList);
        EventBus.getDefault().post(new PersonalOfferCountUpdatedEvent());
        EventBus.getDefault().post(new PersonalOfferListUpdatedEvent());
        //EventBus.getDefault().postSticky(new PersonalOffersUpdatedEvent(offerList));
    }

}
