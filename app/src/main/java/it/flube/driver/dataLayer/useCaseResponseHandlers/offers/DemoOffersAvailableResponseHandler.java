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

public class DemoOffersAvailableResponseHandler implements CloudDatabaseInterface.OffersUpdated {
    private static final String TAG = "DemoOffersAvailableReponseHandler";

    public void cloudDatabaseAvailableOffersUpdated(ArrayList<Offer> offerList) {
        Timber.tag(TAG).d("demo offers available from cloud database");
        EventBus.getDefault().postSticky(new DemoOffersAvailableResponseHandler.AvailableDemoOffersEvent(offerList));
    }


    public static class AvailableDemoOffersEvent {
        private ArrayList<Offer> offerList;
        private int offerCount;

        public AvailableDemoOffersEvent(ArrayList<Offer> offerList){
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
        Timber.tag(TAG).d("no demo offers available from cloud database");
        EventBus.getDefault().postSticky(new DemoOffersAvailableResponseHandler.NoDemoOffersEvent());
    }

    public static class NoDemoOffersEvent {
        public NoDemoOffersEvent(){

        }
    }


}
