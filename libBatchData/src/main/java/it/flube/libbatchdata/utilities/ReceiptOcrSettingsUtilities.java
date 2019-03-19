/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.utilities;

import it.flube.libbatchdata.builders.ReceiptOcrSettingsBuilder;
import it.flube.libbatchdata.constants.receiptOcr.ServiceOneReceiptOcrConstants;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.interfaces.ServiceProviders;

/**
 * Created on 3/8/2019
 * Project : Driver
 */
public class ReceiptOcrSettingsUtilities {

    public static ReceiptOcrSettings getReceiptOcrSettingsForProfile(ServiceProviders.ReceiptOcrSettingsProfile receiptOcrSettingsProfile){

        switch(receiptOcrSettingsProfile){
            case SERVICE_ONE:
                return getServiceOneProfile();
            default:
                return new ReceiptOcrSettingsBuilder.Builder().build();
        }

    }

    private static ReceiptOcrSettings getServiceOneProfile(){

        return new ReceiptOcrSettingsBuilder.Builder()
                    //transaction Id settings
                    .hasTransactionId(ServiceOneReceiptOcrConstants.HAS_TRANSACTION_ID)
                    .transactionIdStartSentinal(ServiceOneReceiptOcrConstants.TRANSACTION_ID_START_SENTINAL)
                    .transactionIdEndSentinal(ServiceOneReceiptOcrConstants.TRANSACTION_ID_END_SENTINAL)
                    .transactionIdPattern(ServiceOneReceiptOcrConstants.TRANSACTION_ID_PATTERN)

                    // vehicle id settings
                    .hasVehicleId(ServiceOneReceiptOcrConstants.HAS_VEHICLE_ID)
                    .vehicleIdStartSentinal(ServiceOneReceiptOcrConstants.VEHICLE_ID_START_SENTINAL)
                    .vehicleIdEndSentinal(ServiceOneReceiptOcrConstants.VEHICLE_ID_END_SENTINAL)
                    .vehicleIdPattern(ServiceOneReceiptOcrConstants.VEHICLE_ID_PATTERN)

                    // vehicle vin settings
                    .hasVehicleVin(ServiceOneReceiptOcrConstants.HAS_VEHICLE_VIN)
                    .vehicleVinStartSentinal(ServiceOneReceiptOcrConstants.VEHICLE_VIN_START_SENTINAL)
                    .vehicleVinEndSentinal(ServiceOneReceiptOcrConstants.VEHICLE_VIN_END_SENTINAL)
                    .vehicleVinPattern(ServiceOneReceiptOcrConstants.VEHICLE_VIN_PATTERN)

                    //vehicle mileage settings
                    .hasVehicleMileage(ServiceOneReceiptOcrConstants.HAS_VEHICLE_MILEAGE)
                    .vehicleMileageStartSentinal(ServiceOneReceiptOcrConstants.VEHICLE_MILEAGE_START_SENTINAL)
                    .vehicleMileageEndSentinal(ServiceOneReceiptOcrConstants.VEHICLE_MILEAGE_END_SENTINAL)
                    .vehicleMileagePattern(ServiceOneReceiptOcrConstants.VEHICLE_MILEAGE_PATTERN)

                    //total charged settings
                    .hasTotalCharged(ServiceOneReceiptOcrConstants.HAS_TOTAL_CHARGED)
                    .totalChargedStartSentinal(ServiceOneReceiptOcrConstants.TOTAL_CHARGED_START_SENTINAL)
                    .totalChargedEndSentinal(ServiceOneReceiptOcrConstants.TOTAL_CHARGED_END_SENTINAL)
                    .totalChargedPattern(ServiceOneReceiptOcrConstants.TOTAL_CHARGED_PATTERN)

                    //has service checklist settings
                    .hasServiceChecklist(ServiceOneReceiptOcrConstants.HAS_SERVICE_CHECKLIST)
                    .serviceChecklistStartSentinal(ServiceOneReceiptOcrConstants.SERVICE_CHECKLIST_START_SENTINAL)
                    .serviceChecklistEndSentinal(ServiceOneReceiptOcrConstants.SERVICE_CHECKLIST_END_SENTINAL)
                    .serviceChecklistPattern(ServiceOneReceiptOcrConstants.SERVICE_CHECKLIST_PATTERN)

                .build();
    }
}
