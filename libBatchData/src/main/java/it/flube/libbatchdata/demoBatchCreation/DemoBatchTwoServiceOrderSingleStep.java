/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.UserTriggerStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class DemoBatchTwoServiceOrderSingleStep implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Two Service Orders -> Single Step Each";
    private static final String BATCH_DESCRIPTION = "Two Service Order Single Step";

    private static final String SERVICE_ORDER_ONE_TITLE = "Service Order 1";
    private static final String SERVICE_ORDER_ONE_DESCRIPTION = "Wait for User Trigger";


    private static final String SERVICE_ORDER_TWO_TITLE = "Service Order 2";
    private static final String SERVICE_ORDER_TWO_DESCRIPTION = "Wait for User Trigger";

    private static final String STEP_TITLE = "User Trigger";
    private static final String STEP_DESCRIPTION = "Wait for User Trigger";
    private static final String MILESTONE_WHEN_FINISHED = "User Completed Step";

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
                        .title(SERVICE_ORDER_ONE_TITLE)
                        .description(SERVICE_ORDER_ONE_DESCRIPTION)
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))

                        .addStep(new UserTriggerStepBuilder.Builder()
                                .title(STEP_TITLE)
                                .description(STEP_DESCRIPTION)
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                                .build())

                        .build())

                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder()
                        .title(SERVICE_ORDER_TWO_TITLE)
                        .description(SERVICE_ORDER_TWO_DESCRIPTION)
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))

                        .addStep(new UserTriggerStepBuilder.Builder()
                                .title(STEP_TITLE)
                                .description(STEP_DESCRIPTION)
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                                .build())
                        .build())


                .build();
    }
}
