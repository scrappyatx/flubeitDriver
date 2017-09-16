/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PublicOffersAvailableResponseHandler implements CloudDatabaseInterface.PublicOffersUpdated {
    private static final String TAG = "PublicOffersAvailableResponseHandler";

    public void cloudDatabasePublicOffersUpdated(ArrayList<Offer> offerList) {
        Timber.tag(TAG).d("public offers available from cloud database");
        EventBus.getDefault().postSticky(new PublicOffersAvailableResponseHandler.AvailablePublicOffersEvent(offerList));
    }

    public static class AvailablePublicOffersEvent {
        private ArrayList<Offer> offerList;
        private int offerCount;

        public AvailablePublicOffersEvent(ArrayList<Offer> offerList){
            this.offerList = offerList;
            this.offerCount = offerList.size();
        }

        public ArrayList<Offer> getOfferList() {
            return offerList;
        }

        public int getOfferCount() {
            return offerCount;
        }

    }

}
