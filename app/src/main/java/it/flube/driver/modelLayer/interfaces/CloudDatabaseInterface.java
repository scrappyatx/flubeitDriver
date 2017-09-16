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

    void saveDemoOfferRequest(String baseNode, Driver driver, Offer offer, SaveDemoOfferResponse response);

    interface SaveDemoOfferResponse {
        void cloudDatabaseDemoOfferSaveComplete();
    }

    void deleteAllDemoOffersRequest(String baseNode, Driver driver, DeleteAllDemoOfferResponse response);

    interface DeleteAllDemoOfferResponse {
        void cloudDatabaseDemoOfferDeleteAllComplete();
    }

    void deleteDemoOfferRequest(String baseNode, Driver driver, Offer offer, DeleteDemoOfferResponse response);

    interface DeleteDemoOfferResponse {
        void cloudDatabaseDemoOfferDeleteComplete();
    }

    void saveDemoBatchRequest(String baseNode, Driver driver, Offer offer, SaveDemoBatchResponse response);

    interface SaveDemoBatchResponse {
        void cloudDatabaseDemoBatchSaveComplete();
    }

    void deleteDemoBatchRequest(String baseNode, Driver driver, Offer offer, DeleteDemoBatchResponse response);

    interface DeleteDemoBatchResponse {
        void cloudDatabaseDemoBatchDeleteComplete();
    }

    void loadActiveBatchRequest(Driver driver, LoadActiveBatchResponse response);

    interface LoadActiveBatchResponse {
        void cloudDatabaseActiveBatchLoaded(Batch activeBatch);

        void cloudDatabaseNoActiveBatchAvailable();
    }


    void listenForPublicOffers(String baseNode, Driver driver);

    interface PublicOffersUpdated {
        void cloudDatabasePublicOffersUpdated(ArrayList<Offer> offerList);
    }

    void listenForPersonalOffers(String baseNode, Driver driver);

    interface PersonalOffersUpdated {
        void cloudDatabasePersonalOffersUpdated(ArrayList<Offer> offerList);
    }

    void listenForDemoOffers(String baseNode, Driver driver);

    interface DemoOffersUpdated {
        void cloudDatabaseDemoOffersUpdated(ArrayList<Offer> offerList);
    }

    void listenForScheduledBatches(String baseNode, Driver driver);

    interface ScheduledBatchesUpdated {
        void cloudDatabaseScheduledBatchesUpdated(ArrayList<BatchCloudDB> batchList);
    }


}