/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.DemoBatchCreation;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.builders.AddressLocationBuilder;
import it.flube.driver.modelLayer.builders.BatchBuilder;
import it.flube.driver.modelLayer.builders.DestinationBuilder;
import it.flube.driver.modelLayer.builders.LatLonLocationBuilder;
import it.flube.driver.modelLayer.builders.OfferBuilder;
import it.flube.driver.modelLayer.builders.PhotoBuilder;
import it.flube.driver.modelLayer.builders.PhotoStepBuilder;
import it.flube.driver.modelLayer.builders.PotentialEarningsBuilder;
import it.flube.driver.modelLayer.builders.RoutingStopBuilder;
import it.flube.driver.modelLayer.builders.ServiceOrderBuilder;
import it.flube.driver.modelLayer.builders.ServiceProviderBuilder;
import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.PotentialEarnings;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.builders.NavigationStepBuilder;
import it.flube.driver.modelLayer.interfaces.DemoInterface;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public class DemoNearbyPhotos implements DemoInterface {
    private static final String TAG = "DemoNearbyPhotos";

    public DemoNearbyPhotos(){

    }

    public Offer createDemoOffer(@NonNull Driver driver){
        //if user doesn't suuply an offerGUID, we create one
        return createOffer(driver,UUID.randomUUID().toString());
    }

    public Offer createDemoOffer(@NonNull Driver driver, @NonNull String offerGUID){
        //use the offerGUID the user supplied
        return createOffer(driver, offerGUID);
    }

    public Batch createDemoBatch(@NonNull Driver driver) {
        // if user doesn't supply a batchGUID, we create one
        return createBatch(driver, UUID.randomUUID().toString());
    }


    public Batch createDemoBatch(@NonNull Driver driver, @NonNull String batchGUID) {
        //use the batchGUID the user supplied
        return createBatch(driver, batchGUID);
    }

    private Offer createOffer(@NonNull Driver driver, @NonNull String offerGUID) {

        return new OfferBuilder.Builder()
                .offerOID(offerGUID)
                .offerType(Offer.OfferType.MOBILE_DEMO)
                .serviceProvider(new ServiceProviderBuilder.Builder()
                        .name("Jiffy Lube")
                        .iconURL("JiffyLubeURL")
                        .contactName("Jeezy Willikers")
                        .contactPhone("512-297-9032")
                        .contactText("512-297-9032")
                        .build())
                .serviceDescription("Pickup Flickup")
                .offerDate("Today")
                .offerTime("2pm-4pm")
                .offerDuration("2 hours")
                .estimatedEarnings("$25")
                .estimatedEarningsExtra("+ Tips")
                .addStopToRoute(0, new RoutingStopBuilder.Builder()
                        .description("Destination")
                        .iconURL("DestinationURL")
                        .addressLocation(getAddressByClientId(driver.getClientId()))
                        .latLonLocation(getLatLonLocationByClientID(driver.getClientId()))
                        .build())
                .build();
    }

    private Batch createBatch(@NonNull Driver driver, @NonNull String batchGUID) {

        Date startTime = Calendar.getInstance().getTime();

        return new BatchBuilder.Builder()
                .title("Single Order Batch")
                .description("This is a single order batch. You have 20 minutes to complete it.")
                .guid(batchGUID)
                .type(Batch.BatchType.MOBILE_DEMO)
                .startTime(startTime)
                .finishTime(startTime, 20)
                .potentialEarnings(new PotentialEarningsBuilder.Builder()
                        .earningsType(PotentialEarnings.EarningsType.FIXED_FEE)
                        .payRateInCents(2500)
                        .plusTips(true)
                        .build())
                .addServiceOrder(0, new ServiceOrderBuilder.Builder()
                        .title("DEMO ORDER")
                        .description("Walk to the destination and take a photo")
                        .startTime(startTime)
                        .finishTime(startTime, 20)
                        .addStep(0, new NavigationStepBuilder.Builder()
                                .title("Go to end of street")
                                .description("Navigate to the end of the street")
                                .startTime(startTime)
                                .finishTime(startTime,10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(getAddressByClientId(driver.getClientId()))
                                        .targetLatLon(getLatLonLocationByClientID(driver.getClientId()))
                                        .targetType(Destination.DestinationType.OTHER)
                                        .build())
                                .milestoneWhenFinished("Arrived At Destination")
                                .build())
                        .addStep(1, new PhotoStepBuilder.Builder()
                                .title("Take three photos")
                                .description("Take three photos of things around you")
                                .startTime(startTime, 10)
                                .startTime(startTime, 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addPhoto(0, new PhotoBuilder.Builder()
                                        .title("First Photo")
                                        .description("This is the first photo to take")
                                        .build())
                                .addPhoto(1, new PhotoBuilder.Builder()
                                        .title("Second Photo")
                                        .description("This is the second photo to take")
                                        .build())
                                .addPhoto(2, new PhotoBuilder.Builder()
                                        .title("Third Photo")
                                        .description("This is the third photo to take")
                                        .build())
                                .build())
                        .build())
                .build();

    }



    private AddressLocation getAddressByClientId(@NonNull String clientId){
        String street;
        String city;
        String state;
        String zip;

        switch (clientId) {
            case "5904da5fff2f3a2fd19d3cf6":
                // cory kelly
                // 607 Hyde Park Place, Austin TX 78748
                // lat lon = (30.176713, -97.798745)
                //
                // nearby:
                // 617 Hyde Park Place, Austin TX 78747
                // lat lon = (30.176970, -97.799686)

                street = "617 Hyde Park Place";
                city = "Austin";
                state = "TX";
                zip = "78747";
                break;
            case "59409679ff2f3a45ba272dad":
                //bryan godwin
                //2001 summercrest cove, round rock tx 78681
                // lat lon = (30.545792, -97.757828)
                //
                // nearby: 4318 south summercrest loop, round rock tx 78681
                // lat lon =(30.546022, -97.75694)

                street = "4318 South Summercrest Loop";
                city = "Round Rock";
                state = "TX";
                zip = "78681";
                break;
            case "597b2f107729e871ed1775cd":
                // caroline godwin
                // 9359 Lincoln Blvd #3247, Los Angeles CA 90045
                // lat lon (33.954765, -118.414605)
                //
                // nearby:
                // 9253 pacific coast highway, los angeles ca 90045
                // lat lon = (33.956787, -118.416301)
                street = "9253 Pacific Coast Highway";
                city = "Los Angeles";
                state = "CA";
                zip = "90045";


                break;
            case "597b2fa17729e871ed1775ce":
                // sean howell
                // 2020 E 2nd St Unit A, Austin TX 78702
                // lat lon =(30.257000, -97.721278)
                //
                // nearby:
                // 200 Caney Street, Austin TX 78702
                // lat lon = (30.256994, -97.721284)

                street = "200 Caney Street";
                city = "Austin";
                state = "TX";
                zip = "78702";

                break;
            default :
                // 202 East 35th Street, Austin, TX
                // lat lon = (30.298974, -97.733049)

                street = "202 East 35th Street";
                city = "Austin";
                state = "TX";
                zip = "78705";
                break;
        }

        return new AddressLocationBuilder.Builder()
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .build();

    }



    private LatLonLocation getLatLonLocationByClientID(@NonNull String clientId){
        Double latitude;
        Double longitude;

        switch (clientId) {
            case "5904da5fff2f3a2fd19d3cf6":
                // cory kelly
                // 607 Hyde Park Place, Austin TX 78748
                // lat lon = (30.176713, -97.798745)
                //
                // nearby:
                // 617 Hyde Park Place, Austin TX 78747
                // lat lon = (30.176970, -97.799686)

                latitude = 30.176970;
                longitude = -97.799686;
                break;
            case "59409679ff2f3a45ba272dad":
                //bryan godwin
                //2001 summercrest cove, round rock tx 78681
                // lat lon = (30.545792, -97.757828)
                //
                // nearby: 4318 south summercrest loop, round rock tx 78681
                // lat lon =(30.546022, -97.75694)
                latitude = 30.546022;
                longitude = -97.75694;

                break;
            case "597b2f107729e871ed1775cd":
                // caroline godwin
                // 9359 Lincoln Blvd #3247, Los Angeles CA 90045
                // lat lon (33.954765, -118.414605)
                //
                // nearby:
                // 9253 pacific coast highway, los angeles ca 90045
                // lat lon = (33.956787, -118.416301)
                latitude = 33.956787;
                longitude = -118.416301;


                break;
            case "597b2fa17729e871ed1775ce":
                // sean howell
                // 2020 E 2nd St Unit A, Austin TX 78702
                // lat lon =(30.257000, -97.721278)
                //
                // nearby:
                // 200 Caney Street, Austin TX 78702
                // lat lon = (30.256994, -97.721284)


                latitude = 30.256994;
                longitude = -97.721284;

                break;
            default :
                // 202 East 35th Street, Austin, TX
                // lat lon = (30.298974, -97.733049)
                latitude = 30.298974;
                longitude = -97.733049;
                break;
        }


        return new LatLonLocationBuilder.Builder()
                .location(latitude, longitude)
                .build();
    }


}
