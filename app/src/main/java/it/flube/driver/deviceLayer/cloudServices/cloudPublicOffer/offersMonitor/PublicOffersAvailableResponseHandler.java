/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offersMonitor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferListUpdatedEvent;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PublicOffersAvailableResponseHandler implements
        CloudPublicOfferInterface.PublicOffersUpdated {

    private static final String TAG = "PublicOffersAvailableResponseHandler";

    private final OffersInterface globalOffersList;

    public PublicOffersAvailableResponseHandler(OffersInterface globalOffersList){
        this.globalOffersList = globalOffersList;
    }

    public void cloudPublicOffersUpdated(ArrayList<Batch> offerList) {
        Timber.tag(TAG).d("public offers available from cloud database");

        Timber.tag(TAG).d("   ...offerList size -> " + offerList.size());
        globalOffersList.setPublicOffers(offerList);
        EventBus.getDefault().post(new PublicOfferCountUpdatedEvent());
        EventBus.getDefault().post(new PublicOfferListUpdatedEvent());

        //EventBus.getDefault().postSticky(new PublicOffersUpdatedEvent(offerList));
    }

}
