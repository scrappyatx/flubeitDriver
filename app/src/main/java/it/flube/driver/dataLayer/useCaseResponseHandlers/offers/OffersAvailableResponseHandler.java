/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 7/25/2017
 * Project : Driver
 */

public class OffersAvailableResponseHandler implements RealtimeMessagingInterface.Notifications.OffersReceived {
    private static final String TAG = "OffersAvailableResponseHandler";


    public void receiveMsgCurrentOffers(ArrayList<Offer> offerList) {
        Timber.tag(TAG).d("offers available from realtime messaging");
        //EventBus.getDefault().postSticky(new OffersAvailableResponseHandler.AvailableOffersEvent(offerList));
    }

    public static class AvailableOffersEvent {
        private ArrayList<Offer> offerList;
        private int offerCount;

        public AvailableOffersEvent(ArrayList<Offer> offerList){
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
