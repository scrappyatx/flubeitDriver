/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.DeviceInfo;
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

    void saveDeviceInfoRequest(Driver driver, DeviceInfo deviceInfo, SaveDeviceInfoResponse response);

    interface SaveDeviceInfoResponse {
        void cloudDatabaseDeviceInfoSaveComplete();
    }

    void saveActiveBatchRequest(Driver driver, Batch batch, SaveActiveBatchResponse response);

    interface SaveActiveBatchResponse {
        void cloudDatabaseActiveBatchSaveComplete();
    }

    void loadActiveBatchRequest(Driver driver, LoadActiveBatchResponse response);

    interface LoadActiveBatchResponse {
        void cloudDatabaseActiveBatchLoaded(Batch activeBatch);

        void cloudDatabaseNoActiveBatchAvailable();
    }


    void listenForPublicOffers(String baseNode, Driver driver);

    void listenForPersonalOffers(String baseNode, Driver driver);

    void listenForDemoOffers(String baseNode, Driver driver);

    interface OffersUpdated {
        void cloudDatabaseAvailableOffersUpdated(ArrayList<Offer> offerList);

        void cloudDatabaseNoAvailableOffers();
    }

    void listenForScheduledBatches(String baseNode, Driver driver);

    interface BatchesUpdated {
        void cloudDatabaseReceivedScheduledBatches(ArrayList<BatchCloudDB> batchList);

        void cloudDatabaseNoScheduledBatches();
    }


}