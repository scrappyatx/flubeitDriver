/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.DemoBatchCreation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.ChatMessage;
import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Earnings;
import it.flube.driver.modelLayer.entities.LatLonPosition;
import it.flube.driver.modelLayer.entities.MapPing;
import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderPhotoStep;
import it.flube.driver.modelLayer.builders.NavStepBuilder;
import it.flube.driver.modelLayer.interfaces.DemoBatchBuilderInterface;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public class DemoBatchBuilder implements DemoBatchBuilderInterface {
    private static final String TAG = "DemoBatchBuilder";

    private Driver driver;

    public DemoBatchBuilder(){

    }

    public Batch getSimpleBatch(Driver driver, BatchCloudDB startingBatch) {
        this.driver = driver;
        return setupSimpleBatch(startingBatch.getOrderOID());
    }

    private Batch setupSimpleBatch(String batchGUID) {
        Batch simpleBatch = new Batch();
        simpleBatch.setBatchGUID(batchGUID);

        simpleBatch.setBatchStatus(Batch.BatchStatus.NOT_STARTED);
        simpleBatch.setBatchType(Batch.BatchType.MOBILE_DEMO);

        simpleBatch.setBatchTitle("Single Order Batch");
        simpleBatch.setBatchDescription("This is a single order batch. You have 20 minutes to complete it.");

        Calendar cal = Calendar.getInstance();

        Timestamp batchStartTime = new Timestamp();
        batchStartTime.setScheduledTime(cal.getTime());

        cal.add(Calendar.MINUTE, 20);

        Timestamp batchEndTime = new Timestamp();
        batchEndTime.setScheduledTime(cal.getTime());

        simpleBatch.setBatchStartTime(batchStartTime);
        simpleBatch.setBatchEndTime(batchEndTime);

        Earnings baseEarnings = new Earnings();
        Earnings extraEarnings = new Earnings();

        baseEarnings.setEarningsDescription("Base Pay");
        baseEarnings.setEarningsDollars(20.00);

        extraEarnings.setEarningsDescription("+ Tip");
        extraEarnings.setEarningsDollars(5.00);

        simpleBatch.setBaseEarnings(baseEarnings);
        simpleBatch.setExtraEarnings(extraEarnings);

        simpleBatch.setServiceOrderList(buildSimpleNavigationServiceOrder());
        buildSimpleNavigationServiceOrder();

        return simpleBatch;
    }

    private ArrayList<ServiceOrder> buildSimpleNavigationServiceOrder(){

        /// build service order
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setOrderGUID(UUID.randomUUID().toString());
        serviceOrder.setOrderTitle("Location Photos");
        serviceOrder.setOrderDescription("Go to the target location and take the specified photos.");

        serviceOrder.setMapPings(new ArrayList<MapPing>());
        serviceOrder.setDriverChatHistory(new ArrayList<ChatMessage>());
        serviceOrder.setServiceProviderChatHistory(new ArrayList<ChatMessage>());
        serviceOrder.setCustomerChatHistory(new ArrayList<ChatMessage>());

        serviceOrder.setOrderStepIndex(0);
        serviceOrder.setOrderSteps(buildSimpleNavigationServiceOrderSteps());

        // create serviceOrderList and add service order to it
        ArrayList<ServiceOrder> serviceOrderList = new ArrayList<ServiceOrder>();
        serviceOrderList.clear();
        serviceOrderList.add(serviceOrder);

        return serviceOrderList;
    }

    private ArrayList<ServiceOrderAbstractStep> buildSimpleNavigationServiceOrderSteps() {
        ArrayList<ServiceOrderAbstractStep> orderSteps = new ArrayList<ServiceOrderAbstractStep>();
        orderSteps.clear();

        //step 1 -> go to a location
        orderSteps.add(buildSimpleNavigationStep());

        //step 2 -> take 2 photos
        orderSteps.add(buildSimplePhotoStep());

        return orderSteps;
    }

    private ServiceOrderAbstractStep buildSimpleNavigationStep(){

        return new NavStepBuilder.Builder()
                .title("nav step test")
                .description("Go down the street from your house")
                .note("brush your teeth")
                .closeEnoughInFeet(300.0)
                .destination(getSimpleBatchDestination())
                .milestoneWhenFinished("ArrivedAtDestination")
                .startTimestamp(getTimestampWithScheduledTime(10))
                .finishTimestamp(getTimestampWithScheduledTime(30))
                .build();

    }

    private Timestamp getTimestampWithScheduledTime(Integer minutesToAdd) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutesToAdd);

        Timestamp t = new Timestamp();
        t.setScheduledTime(cal.getTime());
        return t;
    }

    private Destination getSimpleBatchDestination(){
        Destination destination = new Destination();
        LatLonPosition latLonPosition = new LatLonPosition();
        AddressLocation addressLocation = new AddressLocation();

        switch (driver.getClientId()) {
            case "5904da5fff2f3a2fd19d3cf6":
                // cory kelly
                // 607 Hyde Park Place, Austin TX 78748
                // lat lon = (30.176713, -97.798745)
                //
                // nearby:
                // 617 Hyde Park Place, Austin TX 78747
                // lat lon = (30.176970, -97.799686)
                latLonPosition.setLatLon(30.176970, -97.799686);
                addressLocation.setStreet1("607 Hyde Park Place");
                addressLocation.setCity("Austin");
                addressLocation.setState("TX");
                addressLocation.setZip("78747");


                break;
            case "59409679ff2f3a45ba272dad":
                //bryan godwin
                //2001 summercrest cove, round rock tx 78681
                // lat lon = (30.545792, -97.757828)
                //
                // nearby: 4318 south summercrest loop, round rock tx 78681
                // lat lon =(30.546022, -97.75694)
                latLonPosition.setLatLon(30.546022, -97.75694);
                addressLocation.setStreet1("4318 South Summercrest Loop");
                addressLocation.setCity("Round Rock");
                addressLocation.setState("TX");
                addressLocation.setZip("78681");

                break;
            case "597b2f107729e871ed1775cd":
                // caroline godwin
                // 9359 Lincoln Blvd #3247, Los Angeles CA 90045
                // lat lon (33.954765, -118.414605)
                //
                // nearby:
                // 9253 pacific coast highway, los angeles ca 90045
                // lat lon = (33.956787, -118.416301)
                latLonPosition.setLatLon(33.956787, -118.416301);
                addressLocation.setStreet1("9253 Pacific Coast Highway");
                addressLocation.setCity("Los Angeles");
                addressLocation.setState("CA");
                addressLocation.setZip("90045");


                break;
            case "597b2fa17729e871ed1775ce":
                // sean howell
                // 2020 E 2nd St Unit A, Austin TX 78702
                // lat lon =(30.257000, -97.721278)
                //
                // nearby:
                // 200 Caney Street, Austin TX 78702
                // lat lon = (30.256994, -97.721284)

                latLonPosition.setLatLon(30.256994, -97.721284);
                addressLocation.setStreet1("200 Caney Street");
                addressLocation.setCity("Austin");
                addressLocation.setState("TX");
                addressLocation.setZip("78702");

                break;
            default :
                // 202 East 35th Street, Austin, TX
                // lat lon = (30.298974, -97.733049)
                latLonPosition.setLatLon(30.298974, -97.733049);
                addressLocation.setStreet1("202 East 35th Street");
                addressLocation.setCity("Austin");
                addressLocation.setState("TX");
                addressLocation.setZip("78705");
                break;
        }

        destination.setTargetLatLon(latLonPosition);
        destination.setTargetType(Destination.DestinationType.OTHER);
        destination.setTargetAddress(addressLocation);
        destination.setTargetVerificationMethod(Destination.VerificationMethod.NO_VERIFICATION_PERFORMED);

        return destination;
    }

    private ServiceOrderAbstractStep buildSimplePhotoStep(){
        ServiceOrderPhotoStep photoStep = new ServiceOrderPhotoStep();



        return photoStep;
    }


}
