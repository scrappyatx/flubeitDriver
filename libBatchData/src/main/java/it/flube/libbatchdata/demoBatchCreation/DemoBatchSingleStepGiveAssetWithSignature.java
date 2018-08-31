/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AssetTransferBuilder;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.ContactPersonBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.VehicleBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.GiveAssetStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.ReceiveAssetStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.asset.Vehicle;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSER_TO_CUSTOMER;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER;

/**
 * Created on 6/25/2018
 * Project : Driver
 */
public class DemoBatchSingleStepGiveAssetWithSignature implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Single Step -> Give Asset + Signature";
    private static final String BATCH_DESCRIPTION = "Single Step Give Asset";
    private static final String SERVICE_ORDER_TITLE = "Demo Service Order";
    private static final String SERVICE_ORDER_DESCRIPTION = "Return Asset";
    private static final String STEP_TITLE = "Return Asset";
    private static final String STEP_DESCRIPTION = "Deliver customer's vehicle";
    private static final String MILESTONE_WHEN_FINISHED = "Delivered customer's vehicle";

    /// DemoBatchInterface methods
    public BatchHolder createDemoBatch(String clientId) {
        // if user doesn't supply a batchGUID, we create one
        return createBatch(clientId, BuilderUtilities.generateGuid(),DEFAULT_TARGET_ENVIRONMENT);
    }

    public BatchHolder createDemoBatch(String clientId, TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return createBatch(clientId, BuilderUtilities.generateGuid(), targetEnvironment);
    }

    public BatchHolder createDemoBatch(String clientId, String batchGuid){
        //use the batchGuid the user supplied
        return createBatch(clientId, batchGuid, DEFAULT_TARGET_ENVIRONMENT);
    }

    public BatchHolder createDemoBatch(String clientId, String batchGuid, TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return createBatch(clientId, batchGuid, targetEnvironment);
    }

    //// create batch
    private BatchHolder createBatch(String clientId, String batchGuid, TargetEnvironmentConstants.TargetEnvironment targetEnvironment) {

        return new BatchHolderBuilder.Builder()
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                .title(BATCH_TITLE)
                .description(BATCH_DESCRIPTION)
                .iconUrl(BatchIconGenerator.getRandomIconUrl(targetEnvironment))
                .displayDistance(new DisplayDistanceBuilder.Builder()
                        .distanceToTravel("18 miles")
                        .distanceIndicatorUrl(BatchIconGenerator.getRandomDistanceIndicatorUrl(targetEnvironment))
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

                        .addStep(new GiveAssetStepBuilder.Builder()
                                .title(STEP_TITLE)
                                .description(STEP_DESCRIPTION)
                                //.startTime(BuilderUtilities.getNowDate(), 10)
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                                .transferType(TRANSER_TO_CUSTOMER)
                                .contactPerson(DemoBatchUtilities.getCustomerContactPerson(targetEnvironment))
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(DemoBatchUtilities.getCustomerVehicle(targetEnvironment))
                                        .build())
                                .requireSignature(true)
                                .build())
                        .build())
                .build();
    }
}
