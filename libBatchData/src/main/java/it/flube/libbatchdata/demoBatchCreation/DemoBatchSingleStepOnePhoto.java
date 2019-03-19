/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.ImageAnalysisBuilder;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.builders.PhotoRequestBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.ProductListBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.PhotoStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;

/**
 * Created on 6/25/2018
 * Project : Driver
 */
public class DemoBatchSingleStepOnePhoto implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Single Step -> One Photo";
    private static final String BATCH_DESCRIPTION = "Single Step One Photo";
    private static final String SERVICE_ORDER_TITLE = "Demo Service Order";
    private static final String SERVICE_ORDER_DESCRIPTION = "Take a Photo";
    private static final String STEP_TITLE = "Take a Photo";
    private static final String STEP_DESCRIPTION = "Take a photo of anything";
    private static final String MILESTONE_WHEN_FINISHED = "Photo Taken";


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

    //// batch generation

    private BatchHolder createBatch(String clientId, String batchGuid, TargetEnvironmentConstants.TargetEnvironment targetEnvironment) {

        return new BatchHolderBuilder.Builder(targetEnvironment)
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                .title(BATCH_TITLE)
                .description(BATCH_DESCRIPTION)
                .customer(DemoBatchUtilities.getCustomer())
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
                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder(targetEnvironment)
                        .title(SERVICE_ORDER_TITLE)
                        .description(SERVICE_ORDER_DESCRIPTION)
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))
                        .productList(new ProductListBuilder.Builder()
                                .addCartItem(DemoBatchUtilities.getCustomerCartItem())
                                .build())

                        .addStep(new PhotoStepBuilder.Builder()
                                .title(STEP_TITLE)
                                .description(STEP_DESCRIPTION)
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                                .addPhotoRequest(new PhotoRequestBuilder.Builder(targetEnvironment)
                                        .title("First Photo")
                                        .description("This is the first photo to take")
                                        .imageAnalysis(new ImageAnalysisBuilder.Builder()
                                                .doDeviceImageLabelDetection(true)
                                                .doDeviceTextDetection(true)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
    }
}
