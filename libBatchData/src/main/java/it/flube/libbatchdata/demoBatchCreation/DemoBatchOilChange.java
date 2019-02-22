/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AddressLocationBuilder;
import it.flube.libbatchdata.builders.AssetTransferBuilder;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.ContactPersonBuilder;
import it.flube.libbatchdata.builders.DestinationBuilder;
import it.flube.libbatchdata.builders.LatLonLocationBuilder;
import it.flube.libbatchdata.builders.PaymentAuthorizationBuilder;
import it.flube.libbatchdata.builders.PhotoRequestListForServiceProviderBuilder;
import it.flube.libbatchdata.builders.PhotoRequestListForVehicleBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
import it.flube.libbatchdata.builders.ProductListBuilder;
import it.flube.libbatchdata.builders.ServiceProviderBuilder;
import it.flube.libbatchdata.builders.VehicleBuilder;
import it.flube.libbatchdata.builders.batch.BatchHolderBuilder;
import it.flube.libbatchdata.builders.orderSteps.AuthorizePaymentStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.GiveAssetStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.NavigationStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.PhotoStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.ReceiveAssetStepBuilder;
import it.flube.libbatchdata.builders.orderSteps.UserTriggerStepBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderScaffoldBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.DisplayDistanceBuilder;
import it.flube.libbatchdata.entities.DisplayTimingBuilder;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.ServiceProvider;
import it.flube.libbatchdata.entities.asset.Vehicle;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;

import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_SERVICE_PROVIDER;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class DemoBatchOilChange implements DemoBatchInterface {

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

    //// batch generation method
    private BatchHolder createBatch(String clientId, String batchGuid, TargetEnvironmentConstants.TargetEnvironment targetEnvironment){

        ///     Oil Change batch has 11 steps
        ///
        ///     1.  NAVIGATION                   (go to customer location)
        ///     2.  RECEIVE_ASSET                (get vehicle from customer)
        ///     3.  TAKE_PHOTOS                  (take photos of vehicle)
        ///     4.  NAVIGATION                   (go to service location)
        ///     5.  GIVE_ASSET                   (give vehicle to service location)
        ///     6.  USER_TRIGGER                 (oil gets changed)
        ///     7.  AUTHORIZE_PAYMENT            (pay for oil change)
        ///     8.  RECEIVE_ASSET                (get vehicle from service location)
        ///     9.  NAVIGATION                   (go to customer location)
        ///     10. TAKE_PHOTOS                  (take photos of vehicle)
        ///     11. GIVE_ASSET                   (give vehicle to customer)

        ContactPerson customerPerson = DemoBatchUtilities.getCustomerContactPerson(targetEnvironment);
        AddressLocation customerAddress = DemoBatchUtilities.getCustomerAddress();
        LatLonLocation customerLatLon = DemoBatchUtilities.getCustomerLatLon();
        Vehicle customerVehicle = DemoBatchUtilities.getCustomerVehicle(targetEnvironment);

        ServiceProvider oilChangeProvider = DemoBatchUtilities.getServiceProvider(targetEnvironment);

        return new BatchHolderBuilder.Builder(targetEnvironment)
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                //.title(BatchRandomTitleGenerator.getRandomTitle())
                .title("Demo Oil Change")
                .description("oil change")
                .customer(DemoBatchUtilities.getCustomer())
                .iconUrl(oilChangeProvider.getIconURL())
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
                //.expectedFinishTime(BuilderUtilities.getFutureDate(150))
                .offerExpiryTime(BuilderUtilities.getFutureDate(150))
                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder(targetEnvironment)
                        .title("Demo Oil Change")
                        .description("Get an oil change")
                        .productList(new ProductListBuilder.Builder()
                                .addCartItem(DemoBatchUtilities.getCustomerCartItem())
                                .build())
                        //.startTime(BuilderUtilities.getNowDate())
                        //.finishTime(BuilderUtilities.getFutureDate(30))

                        //// STEP 1 - NAVIGATION ( to customer's location)
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to location")
                                .description("Go to location")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(customerAddress)
                                        .targetLatLon(customerLatLon)
                                        .targetType(Destination.DestinationType.CUSTOMER_WORK)
                                        .build())
                                .build())

                        //// STEP 2 -> RECEIVE ASSET (customer's vehicle)
                        .addStep(new ReceiveAssetStepBuilder.Builder()
                                .title("Pick up vehicle")
                                .description("Pick up vehicle")
                                //.startTime(BuilderUtilities.getNowDate(), 10)
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle Pickup")
                                .transferType(TRANSFER_FROM_CUSTOMER)
                                .contactPerson(customerPerson)
                                .requireSignature(true)
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())

                        /// STEP 3 -> TAKE PHOTOS (customer's vehicle)
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take photos")
                                .description("Take photos of customer's vehicle")
                                //.startTime(BuilderUtilities.getNowDate(), 10)
                                //.finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addVehiclePhotoRequests(new PhotoRequestListForVehicleBuilder.Builder(targetEnvironment)
                                        .addReducedSetPhotos()
                                        .build())
                                .build())


                        /// STEP 4 -> NAVIGATION (to oil change location)
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to service provider")
                                .description("Go to service provider")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(oilChangeProvider.getAddressLocation())
                                        .targetLatLon(oilChangeProvider.getLatLonLocation())
                                        .targetType(Destination.DestinationType.SERVICE_PROVIDER)
                                        .build())
                                .build())



                        /// STEP 5 -> GIVE ASSET (give car to oil change location)
                        .addStep(new GiveAssetStepBuilder.Builder()
                                .title("Give vehicle to service")
                                .description("Give vehicle to service")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle given to service provider")
                                .contactPerson(oilChangeProvider.getContactPerson())
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())

                        /// STEP 6 -> TAKE PHOTOS OF SERVICE PROVIDER LOCATION
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take photo")
                                .description("Take photo of service provider")
                                .milestoneWhenFinished("Photo Taken")
                                .addServiceProviderPhotoRequest(new PhotoRequestListForServiceProviderBuilder.Builder(targetEnvironment)
                                        .addFullSetPhotos()
                                        .build())
                                .build())


                        /// STEP 7 -> USER TRIGGER (oil change performed by oil change shop)
                        .addStep(new UserTriggerStepBuilder.Builder()
                                .title("Oil Change")
                                .description("Wait for oil change to be completed")
                                .displayMessage("Wait for oil change to be completed")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Oil change completed")
                                .build())

                        /// STEP 8 -> AUTHORIZE PAYMENT (payment for oil change to oil change shop)
                        .addStep(new AuthorizePaymentStepBuilder.Builder()
                                .title("Pay for service")
                                .description("Pay for service")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Driver has paid for oil change")
                                .requireReceipt(true)
                                .paymentAuthorization(new PaymentAuthorizationBuilder.Builder()
                                        .maxPaymentAmountCents(15000)
                                        .build())
                                .build())

                        /// STEP 9 -> RECEIVE ASSET (customer's vehicle)
                        .addStep(new ReceiveAssetStepBuilder.Builder()
                                .title("Get vehicle from service")
                                .description("Get vehicle from service")
                                //.startTime(BuilderUtilities.getNowDate(), 10)
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle Retrieved")
                                .transferType(TRANSFER_FROM_SERVICE_PROVIDER)
                                .contactPerson(oilChangeProvider.getContactPerson())
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())

                        /// STEP 10 -> NAVIGATION (to customer's location)
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to location")
                                .description("Go to location")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(customerAddress)
                                        .targetLatLon(customerLatLon)
                                        .targetType(Destination.DestinationType.CUSTOMER_WORK)
                                        .build())
                                .build())

                        /// STEP 11 -> TAKE PHOTOS (customer's vehicle)
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take photos")
                                .description("Take photos of customer's vehicle")
                                //.startTime(BuilderUtilities.getNowDate(), 10)
                                //.finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addVehiclePhotoRequests(new PhotoRequestListForVehicleBuilder.Builder(targetEnvironment)
                                        .addReducedSetPhotos()
                                        .build())
                                .build())

                        /// STEP 12 -> GIVE ASSET (give customer's vehicle to customer)
                        .addStep(new GiveAssetStepBuilder.Builder()
                                .title("Give vehicle to customer")
                                .description("Give vehicle to customer")
                                //.startTime(BuilderUtilities.getNowDate())
                                //.finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle given to customer")
                                .contactPerson(customerPerson)
                                .requireSignature(true)
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())
                        .build())
                .build();

    }
}
