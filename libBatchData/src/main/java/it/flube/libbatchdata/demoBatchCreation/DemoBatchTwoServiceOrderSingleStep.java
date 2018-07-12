/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AssetTransferBuilder;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.GiveAssetStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.ReceiveAssetStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.UserTriggerStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSER_TO_CUSTOMER;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_SERVICE_PROVIDER;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class DemoBatchTwoServiceOrderSingleStep implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Two Service Orders -> Single Step Each";
    private static final String BATCH_DESCRIPTION = "Two Service Order Single Step";

    private static final String SERVICE_ORDER_ONE_TITLE = "Service Order 1";
    private static final String SERVICE_ORDER_ONE_DESCRIPTION = "Give Asset";


    private static final String SERVICE_ORDER_TWO_TITLE = "Service Order 2";
    private static final String SERVICE_ORDER_TWO_DESCRIPTION = "Receive Asset";

    private static final String STEP_1_TITLE = "Give Asset";
    private static final String STEP_1_DESCRIPTION = "Give Asset";
    private static final String MILESTONE_WHEN_FINISHED_1 = "Asset Given";

    private static final String STEP_2_TITLE = "Receive Asset";
    private static final String STEP_2_DESCRIPTION = "Receive Asset";
    private static final String MILESTONE_WHEN_FINISHED_2 = "Asset Received";


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

                        .addStep(new GiveAssetStepBuilder.Builder()
                                .title(STEP_1_TITLE)
                                .description(STEP_1_DESCRIPTION)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED_1)
                                .transferType(TRANSER_TO_CUSTOMER)

                                .contactPerson(DemoBatchUtilities.getCustomerContactPerson())
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(DemoBatchUtilities.getCustomerVehicle())
                                        .build())
                                .requireSignature(true)
                                .build())

                        .build())

                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder()
                        .title(SERVICE_ORDER_TWO_TITLE)
                        .description(SERVICE_ORDER_TWO_DESCRIPTION)
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))

                        .addStep(new ReceiveAssetStepBuilder.Builder()
                                .title(STEP_2_TITLE)
                                .description(STEP_2_DESCRIPTION)
                                .milestoneWhenFinished(MILESTONE_WHEN_FINISHED_2)
                                .transferType(TRANSFER_FROM_SERVICE_PROVIDER)

                                .contactPerson(DemoBatchUtilities.getServiceProviderContactPerson())
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(DemoBatchUtilities.getCustomerVehicle())
                                        .build())
                                .requireSignature(true)
                                .build())
                        .build())


                .build();
    }
}
