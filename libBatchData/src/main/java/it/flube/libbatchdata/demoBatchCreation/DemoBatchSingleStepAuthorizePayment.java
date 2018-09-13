/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AssetTransferBuilder;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.PaymentAuthorizationBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.ProductListBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.AuthorizePaymentStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.GiveAssetStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER;

/**
 * Created on 6/25/2018
 * Project : Driver
 */
public class DemoBatchSingleStepAuthorizePayment implements DemoBatchInterface {
    private static final String BATCH_TITLE = "Single Step -> Authorize Payment";
    private static final String BATCH_DESCRIPTION = "Single Step Authorize Payment";
    private static final String SERVICE_ORDER_TITLE = "Demo Service Order";
    private static final String SERVICE_ORDER_DESCRIPTION = "Authorize Payment";
    private static final String STEP_TITLE = "Authorize Payment";
    private static final String STEP_DESCRIPTION = "Authorize Payment for Service";
    private static final String MILESTONE_WHEN_FINISHED = "Payment Authorized";
    private static final Integer MAX_PAYMENT_AMOUNT_CENTS = 15000;
    private static final Boolean REQUIRE_RECEIPT=true;

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

        return new BatchHolderBuilder.Builder()
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
                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder()
                        .title(SERVICE_ORDER_TITLE)
                        .description(SERVICE_ORDER_DESCRIPTION)
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))
                        .productList(new ProductListBuilder.Builder()
                                .addCartItem(DemoBatchUtilities.getCustomerCartItem())
                                .build())
                        .addStep(new AuthorizePaymentStepBuilder.Builder()
                            .title(STEP_TITLE)
                            .description(STEP_DESCRIPTION)
                            //.startTime(BuilderUtilities.getNowDate())
                            //.finishTime(BuilderUtilities.getNowDate(),10)
                            .milestoneWhenFinished(MILESTONE_WHEN_FINISHED)
                            .paymentAuthorization(new PaymentAuthorizationBuilder.Builder()
                                    .verifyPaymentAmount(true)
                                    .maxPaymentAmountCents(MAX_PAYMENT_AMOUNT_CENTS)
                                    .build())
                            .requireReceipt(REQUIRE_RECEIPT)
                            .build())

                        .build())
                .build();
    }
}
