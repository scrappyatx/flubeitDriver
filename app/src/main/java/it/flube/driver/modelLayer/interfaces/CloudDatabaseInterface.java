/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Batch;
import it.flube.driver.modelLayer.entities.BatchCloudDB;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public interface CloudDatabaseInterface {

    void saveUserRequest(Driver driver, SaveResponse response);

    interface SaveResponse {
        void cloudDatabaseUserSaveComplete();
    }


    void listenForCurrentOffers();

    interface OffersUpdated {
        void cloudDatabaseAvailableOffersUpdated(ArrayList<Offer> offerList);

        void cloudDatabaseNoAvailableOffers();
    }

    void listenForScheduledBatches(Driver driver);

    interface BatchesUpdated {
        void cloudDatabaseReceivedScheduledBatches(ArrayList<BatchCloudDB> batchList);

        void cloudDatabaseNoScheduledBatches();
    }


}