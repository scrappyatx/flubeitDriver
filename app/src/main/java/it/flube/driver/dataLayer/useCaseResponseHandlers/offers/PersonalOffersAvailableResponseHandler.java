/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/15/2017
 * Project : Driver
 */

public class PersonalOffersAvailableResponseHandler implements CloudDatabaseInterface.OffersUpdated {
    private static final String TAG = "PersonalOffersAvailableResponseHandler";

    public void cloudDatabaseAvailableOffersUpdated(ArrayList<Offer> offerList) {
        Timber.tag(TAG).d("personal offers available from cloud database");
        EventBus.getDefault().postSticky(new PersonalOffersAvailableResponseHandler.AvailablePersonalOffersEvent(offerList));
    }


    public static class AvailablePersonalOffersEvent {
        private ArrayList<Offer> offerList;
        private int offerCount;

        public AvailablePersonalOffersEvent(ArrayList<Offer> offerList){
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

    public void cloudDatabaseNoAvailableOffers() {
        Timber.tag(TAG).d("no personal offers available from cloud database");
        EventBus.getDefault().postSticky(new PersonalOffersAvailableResponseHandler.NoPersonalOffersEvent());
    }

    public static class NoPersonalOffersEvent {
        public NoPersonalOffersEvent(){

        }
    }


}