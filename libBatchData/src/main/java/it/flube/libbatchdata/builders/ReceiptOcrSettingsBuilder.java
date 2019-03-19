/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.ReceiptOcrSettings;

/**
 * Created on 2/21/2019
 * Project : Driver
 */
public class ReceiptOcrSettingsBuilder {
    private static final Boolean DEFAULT_HAS_TRANSACTION_ID = true;
    private static final Boolean DEFAULT_HAS_VEHICLE_ID = true;
    private static final Boolean DEFAULT_HAS_VEHICLE_MILEAGE = true;
    private static final Boolean DEFAULT_HAS_VEHICLE_VIN = true;
    private static final Boolean DEFAULT_HAS_SERVICE_CHECKLIST = false;
    private static final Boolean DEFAULT_HAS_TOTAL_CHARGED = true;

    private static final String DEFAULT_TRANSACTION_ID_START_SENTINAL = "TRANSACTION NO";
    private static final String DEFAULT_TRANSACTION_ID_END_SENTINAL = "INVOICE NO";
    private static final String DEFAULT_TRANSACTION_ID_PATTERN = "[0-9]{14}";

    private static final String DEFAULT_VEHICLE_ID_START_SENTINAL = "VEHICLE ID";
    private static final String DEFAULT_VEHICLE_ID_END_SENTINAL = "Customer Information";
    private static final String DEFAULT_VEHICLE_ID_PATTERN = "[A-Za-z]{2}[-][A-Za-z0-9]{1,10}";

    private static final String DEFAULT_VEHICLE_MILEAGE_START_SENTINAL = "MILEAGE";
    private static final String DEFAULT_VEHICLE_MILEAGE_END_SENTINAL = "ALT ID";
    private static final String DEFAULT_VEHICLE_MILEAGE_PATTERN = "[0-9]{1,6}";

    private static final String DEFAULT_VEHICLE_VIN_START_SENTINAL = "VIN";
    private static final String DEFAULT_VEHICLE_VIN_END_SENTINAL = "MILEAGE";
    private static final String DEFAULT_VEHICLE_VIN_PATTERN = "[A-HJ-NPR-Z0-9]{17}";

    private static final String DEFAULT_SERVICE_CHECKLIST_START_SENTINAL = "";
    private static final String DEFAULT_SERVICE_CHECKLIST_END_SENTINAL = "";
    private static final String DEFAULT_SERVICE_CHECKLIST_PATTERN = "";

    private static final String DEFAULT_TOTAL_CHARGED_START_SENTINAL = "TOTAL";
    private static final String DEFAULT_TOTAL_CHARGED_END_SENTINAL = "CHANGE";
    private static final String DEFAULT_TOTAL_CHARGED_PATTERN = "[$][0-9]{1,3}[.][0-9]{2}";


    private ReceiptOcrSettings receiptOcrSettings;

    private ReceiptOcrSettingsBuilder(Builder builder){
        this.receiptOcrSettings = builder.receiptOcrSettings;
    }

    private ReceiptOcrSettings getReceiptOcrSettings(){
        return this.receiptOcrSettings;
    }

    public static class Builder {
        private ReceiptOcrSettings receiptOcrSettings;

        public Builder(){
            receiptOcrSettings = new ReceiptOcrSettings();

            //set the thing we expect to find in the receipt image
            receiptOcrSettings.setHasTransactionId(DEFAULT_HAS_TRANSACTION_ID);

            receiptOcrSettings.setHasVehicleId(DEFAULT_HAS_VEHICLE_ID);
            receiptOcrSettings.setHasVehicleMileage(DEFAULT_HAS_VEHICLE_MILEAGE);
            receiptOcrSettings.setHasVehicleVin(DEFAULT_HAS_VEHICLE_VIN);

            receiptOcrSettings.setHasServiceChecklist(DEFAULT_HAS_SERVICE_CHECKLIST);
            receiptOcrSettings.setHasTotalCharged(DEFAULT_HAS_TOTAL_CHARGED);

            // now initialize the sentinals & patterns
            receiptOcrSettings.setTransactionIdStartSentinal(DEFAULT_TRANSACTION_ID_START_SENTINAL);
            receiptOcrSettings.setTransactionIdEndSentinal(DEFAULT_TRANSACTION_ID_END_SENTINAL);
            receiptOcrSettings.setTransactionIdPattern(DEFAULT_TRANSACTION_ID_PATTERN);

            receiptOcrSettings.setVehicleIdStartSentinal(DEFAULT_VEHICLE_ID_START_SENTINAL);
            receiptOcrSettings.setVehicleIdEndSentinal(DEFAULT_VEHICLE_ID_END_SENTINAL);
            receiptOcrSettings.setVehicleIdPattern(DEFAULT_VEHICLE_ID_PATTERN);

            receiptOcrSettings.setVehicleMileageStartSentinal(DEFAULT_VEHICLE_MILEAGE_START_SENTINAL);
            receiptOcrSettings.setVehicleMileageEndSentinal(DEFAULT_VEHICLE_MILEAGE_END_SENTINAL);
            receiptOcrSettings.setVehicleMileagePattern(DEFAULT_VEHICLE_MILEAGE_PATTERN);

            receiptOcrSettings.setVehicleVinStartSentinal(DEFAULT_VEHICLE_VIN_START_SENTINAL);
            receiptOcrSettings.setVehicleVinEndSentinal(DEFAULT_VEHICLE_VIN_END_SENTINAL);
            receiptOcrSettings.setVehicleVinPattern(DEFAULT_VEHICLE_VIN_PATTERN);

            receiptOcrSettings.setServiceChecklistStartSentinal(DEFAULT_SERVICE_CHECKLIST_START_SENTINAL);
            receiptOcrSettings.setServiceChecklistEndSentinal(DEFAULT_SERVICE_CHECKLIST_END_SENTINAL);
            receiptOcrSettings.setServiceChecklistPattern(DEFAULT_SERVICE_CHECKLIST_PATTERN);

            receiptOcrSettings.setTotalChargedStartSentinal(DEFAULT_TOTAL_CHARGED_START_SENTINAL);
            receiptOcrSettings.setTotalChargedEndSentinal(DEFAULT_TOTAL_CHARGED_END_SENTINAL);
            receiptOcrSettings.setTotalChargedPattern(DEFAULT_TOTAL_CHARGED_PATTERN);
        }

        //// FLAGS OF FIELDS TO SEARCH FOR
        public Builder hasTransactionId(Boolean hasTransactionId){
            this.receiptOcrSettings.setHasTransactionId(hasTransactionId);
            return this;
        }

        public Builder hasVehicleId(Boolean hasVehicleId){
            this.receiptOcrSettings.setHasVehicleId(hasVehicleId);
            return this;
        }

        public Builder hasVehicleMileage(Boolean hasVehicleMileage){
            this.receiptOcrSettings.setHasVehicleMileage(hasVehicleMileage);
            return this;
        }

        public Builder hasVehicleVin(Boolean hasVehicleVin){
            this.receiptOcrSettings.setHasVehicleVin(hasVehicleVin);
            return this;
        }

        public Builder hasServiceChecklist(Boolean hasServiceChecklist){
            this.receiptOcrSettings.setHasServiceChecklist(hasServiceChecklist);
            return this;
        }

        public Builder hasTotalCharged(Boolean hasTotalCharged){
            this.receiptOcrSettings.setHasTotalCharged(hasTotalCharged);
            return this;
        }

        /// TRANSACTION ID
        public Builder transactionIdStartSentinal(String transactionIdStartSentinal){
            this.receiptOcrSettings.setTransactionIdStartSentinal(transactionIdStartSentinal);
            return this;
        }

        public Builder transactionIdEndSentinal(String setTransactionIdEndSentinal){
            this.receiptOcrSettings.setTransactionIdEndSentinal(setTransactionIdEndSentinal);
            return this;
        }

        public Builder transactionIdPattern(String transactionIdPattern){
            this.receiptOcrSettings.setTransactionIdPattern(transactionIdPattern);
            return this;
        }

        /// VEHICLE ID
        public Builder vehicleIdStartSentinal(String vehicleIdStartSentinal){
            this.receiptOcrSettings.setVehicleIdStartSentinal(vehicleIdStartSentinal);
            return this;
        }

        public Builder vehicleIdEndSentinal(String vehicleIdEndSentinal){
            this.receiptOcrSettings.setVehicleIdEndSentinal(vehicleIdEndSentinal);
            return this;
        }

        public Builder vehicleIdPattern(String vehicleIdPattern){
            this.receiptOcrSettings.setVehicleIdPattern(vehicleIdPattern);
            return this;
        }

        /// VEHICLE MILEAGE
        public Builder vehicleMileageStartSentinal(String vehicleMileageStartSentinal){
            this.receiptOcrSettings.setVehicleMileageStartSentinal(vehicleMileageStartSentinal);
            return this;
        }

        public Builder vehicleMileageEndSentinal(String vehicleMileageEndSentinal){
            this.receiptOcrSettings.setVehicleMileageEndSentinal(vehicleMileageEndSentinal);
            return this;
        }

        public Builder vehicleMileagePattern(String vehicleMileagePattern){
            this.receiptOcrSettings.setVehicleMileagePattern(vehicleMileagePattern);
            return this;
        }

        //// VEHICLE VIN
        public Builder vehicleVinStartSentinal(String vehicleVinStartSentinal){
            this.receiptOcrSettings.setVehicleVinStartSentinal(vehicleVinStartSentinal);
            return this;
        }

        public Builder vehicleVinEndSentinal(String vehicleVinEndSentinal){
            this.receiptOcrSettings.setVehicleVinEndSentinal(vehicleVinEndSentinal);
            return this;
        }

        public Builder vehicleVinPattern(String vehicleVinPattern){
            this.receiptOcrSettings.setVehicleVinPattern(vehicleVinPattern);
            return this;
        }

        //// SERVICE CHECKLIST
        public Builder serviceChecklistStartSentinal(String serviceChecklistStartSentinal){
            this.receiptOcrSettings.setServiceChecklistStartSentinal(serviceChecklistStartSentinal);
            return this;
        }

        public Builder serviceChecklistEndSentinal(String serviceChecklistEndSentinal){
            this.receiptOcrSettings.setServiceChecklistEndSentinal(serviceChecklistEndSentinal);
            return this;
        }

        public Builder serviceChecklistPattern(String serviceChecklistPattern){
            this.receiptOcrSettings.setServiceChecklistPattern(serviceChecklistPattern);
            return this;
        }

        //// TOTAL CHARGED
        public Builder totalChargedStartSentinal(String totalChargedStartSentinal){
            this.receiptOcrSettings.setTotalChargedStartSentinal(totalChargedStartSentinal);
            return this;
        }

        public Builder totalChargedEndSentinal(String totalChargedEndSentinal){
            this.receiptOcrSettings.setTotalChargedEndSentinal(totalChargedEndSentinal);
            return this;
        }

        public Builder totalChargedPattern(String totalChargedPattern){
            this.receiptOcrSettings.setTotalChargedPattern(totalChargedPattern);
            return this;
        }


        private void validate(ReceiptOcrSettings receiptOcrSettings){
            //do nothing
        }

        public ReceiptOcrSettings build(){
            ReceiptOcrSettings receiptOcrSettings = new ReceiptOcrSettingsBuilder(this).getReceiptOcrSettings();
            validate(receiptOcrSettings);
            return receiptOcrSettings;
        }
    }

}
