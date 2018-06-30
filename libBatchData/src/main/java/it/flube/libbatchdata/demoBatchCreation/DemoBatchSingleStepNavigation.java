/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AssetTransferBuilder;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.DestinationBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.GiveAssetStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.NavigationStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER;

/**
 * Created on 6/25/2018
 * Project : Driver
 */
public class DemoBatchSingleStepNavigation implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Single Step -> Navigation";
    private static final String BATCH_DESCRIPTION = "Single Step Navigation";
    private static final String SERVICE_ORDER_TITLE = "Demo Service Order";
    private static final String SERVICE_ORDER_DESCRIPTION = "Go to Location";
    private static final String STEP_TITLE = "Go to Location";
    private static final String STEP_DESCRIPTION = "Navigate to Customer Location";
    private static final String MILESTONE_WHEN_FINISHED = "Arrived at customer's location";

    public BatchHolder createDemoBatch(String clientId){
        return getDemoBatch(clientId, BuilderUtilities.generateGuid());
    }

    public BatchHolder createDemoBatch(String clientId, String batchGuid){
        return getDemoBatch(clientId, batchGuid);
    }

    public BatchHolder getDemoBatch(String clientId, String batchGuid) {

        return new BatchHolderBuilder.Builder()
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                .title(BATCH_TITLE)
                .description(BATCH_DESCRIPTION)
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
                        .title(SERVICE_ORDER_TITLE)
                        .description(SERVICE_ORDER_DESCRIPTION)
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))

                        .addStep(new NavigationStepBuilder.Builder()
                                .title(STEP_TITLE)
                                .description(STEP_DESCRIPTION)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(DemoBatchUtilities.getCustomerAddress())
                                        .targetLatLon(DemoBatchUtilities.getCustomerLatLon())
                                        .targetType(Destination.DestinationType.CUSTOMER_HOME)
                                        .build())
                                .build())

                        .build())
                .build();
    }
}
