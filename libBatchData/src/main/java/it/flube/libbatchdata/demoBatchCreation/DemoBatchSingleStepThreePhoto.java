/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.PhotoRequestBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.PhotoStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

/**
 * Created on 6/25/2018
 * Project : Driver
 */
public class DemoBatchSingleStepThreePhoto implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Single Step -> Three Photo";
    private static final String BATCH_DESCRIPTION = "Single Step Three Photo";
    private static final String SERVICE_ORDER_TITLE = "Demo Service Order";
    private static final String SERVICE_ORDER_DESCRIPTION = "Take 3 Photos";
    private static final String STEP_TITLE = "Take 3 Photos";
    private static final String STEP_DESCRIPTION = "Take 3 photos of anything";
    private static final String MILESTONE_WHEN_FINISHED = "Photos Taken";


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

                        .addStep(new PhotoStepBuilder.Builder()
                                .title(STEP_TITLE)
                                .description(STEP_DESCRIPTION)
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                                .addPhotoRequest(new PhotoRequestBuilder.Builder()
                                        .title("First Photo")
                                        .description("This is the first photo to take")
                                        .build())
                                .addPhotoRequest(new PhotoRequestBuilder.Builder()
                                        .title("Second Photo")
                                        .description("This is the second photo to take")
                                        .build())
                                .addPhotoRequest(new PhotoRequestBuilder.Builder()
                                        .title("Third Photo")
                                        .description("This is the third photo to take")
                                        .build())
                                .build())
                        .build())
                .build();
    }
}
