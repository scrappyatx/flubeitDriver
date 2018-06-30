/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.DestinationBuilder;
import it.flube.libbatchdata.builders.PhotoRequestListForVehicleBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.NavigationStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.PhotoStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.DisplayTimingBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class DemoBatchTwoStepWithVehiclePhotos implements DemoBatchInterface {

    public BatchHolder createDemoBatch(String clientId){
        return getDemoBatch(clientId, BuilderUtilities.generateGuid());
    }

    public BatchHolder createDemoBatch(String clientId, String batchGuid){
        return getDemoBatch(clientId, batchGuid);
    }

    public BatchHolder getDemoBatch(String clientId, String batchGuid){

        return new BatchHolderBuilder.Builder()
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                .title(BatchRandomTitleGenerator.getRandomTitle())
                .description("pick up a vehicle")
                .iconUrl(BatchIconGenerator.getRandomIconUrl())
                .displayDistance(new DisplayDistanceBuilder.Builder()
                        .distanceToTravel("18 miles")
                        .distanceIndicatorUrl(BatchIconGenerator.getRandomDistanceIndicatorUrl())
                        .build())
                .potentialEarnings(new PotentialEarningsBuilder.Builder()
                        .payRateInCents(2800)
                        .earningsType(PotentialEarnings.EarningsType.FIXED_FEE)
                        .plusTips(true)
                        .build())
                .expectedStartTime(BuilderUtilities.getNowDate())
                .expectedFinishTime(BuilderUtilities.getFutureDate(150))
                .offerExpiryTime(BuilderUtilities.getFutureDate(150))
                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder()
                        .title("DEMO ORDER")
                        .description("Go to destination and take photos of a car")
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to end of street")
                                .description("Navigate to the end of the street")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(BatchLocationAndAddressGenerator.getAddressByClientId(clientId))
                                        .targetLatLon(BatchLocationAndAddressGenerator.getLatLonLocationByClientID(clientId))
                                        .targetType(Destination.DestinationType.OTHER)
                                        .build())
                                .milestoneWhenFinished("Arrived At Destination")
                                .build())
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take three photos")
                                .description("Take three photos of things around you")
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addVehiclePhotoRequests(new PhotoRequestListForVehicleBuilder.Builder()
                                        .build())
                                .build())
                        .build())
                .build();
    }
}
