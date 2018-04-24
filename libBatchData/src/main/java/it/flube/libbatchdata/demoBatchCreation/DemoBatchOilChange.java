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
import it.flube.libbatchdata.builders.PhotoRequestListForVehicleBuilder;
import it.flube.libbatchdata.builders.PotentialEarningsBuilder;
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

import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferType.TRANSFER_FROM_SERVICE_PROVIDER;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class DemoBatchOilChange implements DemoBatchInterface {

    public BatchHolder createDemoBatch(String clientId){
        return getDemoBatch(clientId, BuilderUtilities.generateGuid());
    }

    public BatchHolder createDemoBatch(String clientId, String batchGuid){
        return getDemoBatch(clientId, batchGuid);
    }

    public BatchHolder getDemoBatch(String clientId, String batchGuid){

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


        ////
        ////    CUSTOMER INFO
        ////

        //create customer
        ContactPerson customerPerson = new ContactPersonBuilder.Builder()
                .contactRole(ContactPerson.ContactRole.CUSTOMER)
                .displayName("John Q. Customer")
                .displayPhoneNumber("(512) 555-1212")
                .build();

        //create customer's vehicle
        Vehicle customerVehicle = new VehicleBuilder.Builder()
                .name("customer's vehicle")
                .make("Audi")
                .model("A6")
                .year("2011")
                .color("black")
                .licenseState("TX")
                .licensePlate("ATX 555")
                .build();

        //create customer location (both address & latitude / longitude)
        AddressLocation customerAddress = new AddressLocationBuilder.Builder()
                        .street("1606 Mohle Drive")
                        .city("Austin")
                        .state("TX")
                        .zip("78703")
                        .build();

        LatLonLocation customerLatLon = new LatLonLocationBuilder.Builder()
                        .location(30.3007342, -97.7545089)
                        .build();


        ////
        ////    SERVICE PROVIDER INFO
        ////

        //create service provider
        ServiceProvider oilChangeProvider = new ServiceProviderBuilder.Builder()
                .iconURL(BatchIconGenerator.getRandomIconUrl())
                .name("Slippery Lester's Quick Lube")
                .contactPhone("(512) 555-1414")
                .contactText("(512) 555-1515")
                .addressLocation(new AddressLocationBuilder.Builder()
                        .street("3700 North Lamar Blvd")
                        .city("Austin")
                        .state("TX")
                        .zip("78705")
                        .build())
                .latLonLocation(new LatLonLocationBuilder.Builder()
                        .location(30.3046318, -97.7438983)
                        .build())
                .contactPerson(new ContactPersonBuilder.Builder()
                        .displayName("Billy Oilchange")
                        .contactRole(ContactPerson.ContactRole.SERVICE_PROVIDER)
                        .displayPhoneNumber("(512) 555-1414")
                        .build())
                .build();


        return new BatchHolderBuilder.Builder()
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                .title(BatchRandomTitleGenerator.getRandomTitle())
                .description("oil change")
                .iconUrl(oilChangeProvider.getIconURL())
                .displayTiming(new DisplayTimingBuilder.Builder()
                        .date("Today")
                        .hours("9:30 am - 12:00 pm")
                        .duration("2.5 hours")
                        .offerExpiryDate("Claim by today, 3:00 pm")
                        .build())
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
                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder()
                        .title("DEMO OIL CHANGE")
                        .description("Get an oil change")
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))

                        //// STEP 1 - NAVIGATION ( to customer's location)
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to customer's location")
                                .description("Travel to customer's location")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(customerAddress)
                                        .targetLatLon(customerLatLon)
                                        .targetType(Destination.DestinationType.CUSTOMER_HOME)
                                        .build())
                                .build())

                        //// STEP 2 -> RECEIVE ASSET (customer's vehicle)
                        .addStep(new ReceiveAssetStepBuilder.Builder()
                                .title("Pick up customer's vehicle")
                                .description("Pick up customer's vehicle")
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle Pickup")
                                .transferType(TRANSFER_FROM_CUSTOMER)
                                .contactPerson(customerPerson)
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())

                        /// STEP 3 -> TAKE PHOTOS (customer's vehicle)
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take photos")
                                .description("Take photos of customer's vehicle")
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addVehiclePhotoRequests(new PhotoRequestListForVehicleBuilder.Builder()
                                        .build())
                                .build())


                        /// STEP 4 -> NAVIGATION (to oil change location)
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to oil change provider")
                                .description("Travel to oil change provider location")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(oilChangeProvider.getAddressLocation())
                                        .targetLatLon(oilChangeProvider.getLatLonLocation())
                                        .targetType(Destination.DestinationType.SERVICE_PROVIDER)
                                        .build())
                                .build())

                        /// STEP 5 -> GIVE ASSET (give car to oil change location)
                        .addStep(new GiveAssetStepBuilder.Builder()
                                .title("Give customer's vehicle to oil change provider")
                                .description("Give customer's vehicle to oil change provider")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle given to oil change provider")
                                .contactPerson(oilChangeProvider.getContactPerson())
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())

                        /// STEP 6 -> USER TRIGGER (oil change performed by oil change shop)
                        .addStep(new UserTriggerStepBuilder.Builder()
                                .title("Wait for oil change to be completed")
                                .description("Wait for oil change to be completed")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Oil change completed")
                                .build())

                        /// STEP 7 -> AUTHORIZE PAYMENT (payment for oil change to oil change shop)
                        .addStep(new AuthorizePaymentStepBuilder.Builder()
                                .title("Paying for oil change")
                                .description("Waiting for oil change to be paid")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Driver has paid for oil change")
                                .maxPaymentAmount("$150.00")
                                .build())

                        /// STEP 8 -> RECEIVE ASSET (customer's vehicle)
                        .addStep(new ReceiveAssetStepBuilder.Builder()
                                .title("Retrieve customer's vehicle")
                                .description("Retreive customer's vehicle")
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle Retrieved")
                                .transferType(TRANSFER_FROM_SERVICE_PROVIDER)
                                .contactPerson(oilChangeProvider.getContactPerson())
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())

                        /// STEP 9 -> NAVIGATION (to customer's location)
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to customer's location")
                                .description("Travel to customer's location")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(customerAddress)
                                        .targetLatLon(customerLatLon)
                                        .targetType(Destination.DestinationType.CUSTOMER_HOME)
                                        .build())
                                .build())

                        /// STEP 10 -> TAKE PHOTOS (customer's vehicle)
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take photos")
                                .description("Take photos of customer's vehicle")
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addVehiclePhotoRequests(new PhotoRequestListForVehicleBuilder.Builder()
                                        .build())
                                .build())

                        /// STEP 11 -> GIVE ASSET (give customer's vehicle to customer)
                        .addStep(new GiveAssetStepBuilder.Builder()
                                .title("Give customer's vehicle to customer")
                                .description("Give customer's vehicle to customer")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .milestoneWhenFinished("Vehicle given to customer")
                                .contactPerson(customerPerson)
                                .addAssetTransfer(new AssetTransferBuilder.Builder()
                                        .asset(customerVehicle)
                                        .build())
                                .build())
                        .build())
                .build();

    }
}
