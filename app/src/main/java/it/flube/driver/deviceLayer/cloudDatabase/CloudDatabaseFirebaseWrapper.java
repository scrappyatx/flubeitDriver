/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.personalOffers.PersonalOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers.PublicOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public class CloudDatabaseFirebaseWrapper implements CloudDatabaseInterface {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudDatabaseFirebaseWrapper mInstance = new CloudDatabaseFirebaseWrapper();
    }

    private CloudDatabaseFirebaseWrapper() {
        setupFirebaseDatabaseForOfflinePersistence();
    }

    public static CloudDatabaseFirebaseWrapper getInstance() {
        return CloudDatabaseFirebaseWrapper.Loader.mInstance;
    }

    private static final String TAG = "CloudDatabaseFirebaseWrapper";

    private FirebaseDatabase database;
    private DatabaseReference publicOffers;
    private DatabaseReference personalOffers;
    private DatabaseReference demoOffers;
    private DatabaseReference scheduledBatches;

    private CloudDatabaseInterface.SaveActiveBatchResponse saveActiveBatchResponse;

    private void setupFirebaseDatabaseForOfflinePersistence(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        Timber.tag(TAG).d("FirebaseDatabase --> setPersistenceEnabled TRUE for OFFLINE persistence");
    }

    public void saveUserRequest(Driver driver, CloudDatabaseInterface.SaveResponse response) {
        DatabaseReference usersRef = database.getReference("users");

        FirebaseUser firebaseUser = new FirebaseUser();
        firebaseUser.saveUserRequest(usersRef, driver, response);

        Timber.tag(TAG).d("saving DRIVER object --> clientId : " + driver.getClientId() + " name : " + driver.getDisplayName());
    }


    public void saveDeviceInfoRequest(Driver driver, DeviceInfo deviceInfo, SaveDeviceInfoResponse response) {
        DatabaseReference deviceRef = database.getReference("userOwned/devices");

        FirebaseDevice firebaseDevice = new FirebaseDevice();
        firebaseDevice.saveDeviceInfoRequest(deviceRef, driver, deviceInfo, response);

        Timber.tag(TAG).d("saving DEVICE INFO object --> device GUID : " + deviceInfo.getDeviceGUID());
    }


    public void saveActiveBatchRequest(Driver driver, Batch batch, SaveActiveBatchResponse response) {
        DatabaseReference batchRef = database.getReference("userOwned/users/" + driver.getClientId() + "/activeBatch");

        FirebaseActiveBatch firebaseActiveBatch = new FirebaseActiveBatch();
        firebaseActiveBatch.saveActiveBatchRequest(batchRef, driver, batch, response);

        Timber.tag(TAG).d("saving ACTIVE BATCH ---> driver Id -> " + driver.getClientId() + " batchGUID --> " + batch.getGUID());
    }

    public void saveDemoOfferRequest(String baseNode, Driver driver, Offer offer, SaveDemoOfferResponse response) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getDemoOffersNode();

        demoOffers = database.getReference(targetNode);
        FirebaseDemoOffers firebaseDemoOffers = new FirebaseDemoOffers();
        firebaseDemoOffers.saveDemoOfferRequest(demoOffers, offer, response);

        Timber.tag(TAG).d("saving DEMO OFFER ---> driver Id -> " + driver.getClientId() + " offerOID--> " + offer.getGUID());
    }

    public void deleteDemoOfferRequest(String baseNode, Driver driver, Offer offer, DeleteDemoOfferResponse response) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getDemoOffersNode();

        demoOffers = database.getReference(targetNode);
        FirebaseDemoOffers firebaseDemoOffers = new FirebaseDemoOffers();

        firebaseDemoOffers.deleteDemoOfferRequest(demoOffers, offer, response);
    }

    public void deleteAllDemoOffersRequest(String baseNode, Driver driver, DeleteAllDemoOfferResponse response) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getDemoOffersNode();

        demoOffers = database.getReference(targetNode);
        FirebaseDemoOffers firebaseDemoOffers = new FirebaseDemoOffers();
        firebaseDemoOffers.deleteAllDemoOffersRequest(demoOffers, response);

        Timber.tag(TAG).d("deleting all DEMO OFFERS ---> driver Id -> " + driver.getClientId());
    }

    public void saveDemoBatchRequest(String baseNode, Driver driver, Offer offer, SaveDemoBatchResponse response) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getScheduledBatchesNode();

        DatabaseReference demoBatch = database.getReference(targetNode);
        FirebaseDemoBatch firebaseDemoBatch = new FirebaseDemoBatch();
        firebaseDemoBatch.saveDemoBatchRequest(demoBatch, offer, response);

        Timber.tag(TAG).d("saving DEMO BATCH ---> offer GUID -> " + offer.getGUID());
    }

    public void deleteDemoBatchRequest(String baseNode, Driver driver, Offer offer, DeleteDemoBatchResponse response) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getScheduledBatchesNode();

        DatabaseReference demoBatch = database.getReference(targetNode);
        FirebaseDemoBatch firebaseDemoBatch = new FirebaseDemoBatch();
        firebaseDemoBatch.deleteDemoBatchRequest(demoBatch, offer, response);

        Timber.tag(TAG).d("deleting DEMO BATCH ---> offer GUID -> " + offer.getGUID());
    }

    public void loadActiveBatchRequest(Driver driver, CloudDatabaseInterface.LoadActiveBatchResponse response) {
        DatabaseReference activeBatchRef = database.getReference("userOwned/users/" + driver.getClientId() + "/activeBatch");

        FirebaseActiveBatch firebaseActiveBatch = new FirebaseActiveBatch();
        firebaseActiveBatch.loadActiveBatchRequest(activeBatchRef, driver, response);

        Timber.tag(TAG).d("loading ACTIVE BATCH ---> driver Id -> " + driver.getClientId());
    }


    public void listenForPublicOffers(String baseNode, Driver driver) {
        String targetNode = baseNode + "/" + driver.getPublicOffersNode();

        publicOffers = database.getReference(targetNode);
        publicOffers.addValueEventListener(new FirebasePublicOffersEventListener(new PublicOffersAvailableResponseHandler()));

        Timber.tag(TAG).d("listening for public offers at node --> " + targetNode);
    }

    public void listenForPersonalOffers(String baseNode, Driver driver){
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getPersonalOffersNode();

        personalOffers = database.getReference(targetNode);
        personalOffers.addValueEventListener(new FirebasePersonalOffersEventListener(new PersonalOffersAvailableResponseHandler()));

        Timber.tag(TAG).d("listening for personal offers at node --> " + targetNode);
    }

    public void listenForDemoOffers(String baseNode, Driver driver) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getDemoOffersNode();

        demoOffers = database.getReference(targetNode);
        demoOffers.addValueEventListener(new FirebaseDemoOffersEventListener(new DemoOffersAvailableResponseHandler()));

        Timber.tag(TAG).d("listening for demo offers at node --> " + targetNode);
    }


    public void listenForScheduledBatches(String baseNode, Driver driver) {
        String targetNode = baseNode + "/" + driver.getClientId() + "/" + driver.getScheduledBatchesNode();

        scheduledBatches = database.getReference(targetNode);
        scheduledBatches.addValueEventListener(new FirebaseScheduledBatchEventListener(new ScheduledBatchesAvailableResponseHandler()));

        Timber.tag(TAG).d("listening for scheduled batches at node --> " + targetNode);
    }



}
