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
    private static final Boolean DEFAULT_HAS_VEHICLE_ID = false;
    private static final Boolean DEFAULT_HAS_VEHICLE_MILEAGE = false;
    private static final Boolean DEFAULT_HAS_VEHICLE_VIN = false;
    private static final Boolean DEFAULT_HAS_SERVICE_CHECKLIST = false;
    private static final Boolean DEFAULT_HAS_TOTAL_CHARGED = false;

    private static final String DEFAULT_TRANSACTION_ID_START_SENTINAL = "TRANSACTIONID";
    private static final String DEFAULT_TRANSACTION_ID_END_SENTINAL = "INVOICE";

    private static final String DEFAULT_VEHICLE_ID_START_SENTINAL = "";
    private static final String DEFAULT_VEHICLE_ID_END_SENTINAL = "";

    private static final String DEFAULT_VEHICLE_MILEAGE_START_SENTINAL = "";
    private static final String DEFAULT_VEHICLE_MILEAGE_END_SENTINAL = "";

    private static final String DEFAULT_VEHICLE_VIN_START_SENTINAL = "";
    private static final String DEFAULT_VEHICLE_VIN_END_SENTINAL = "";

    private static final String DEFAULT_SERVICE_CHECKLIST_START_SENTINAL = "";
    private static final String DEFAULT_SERVICE_CHECKLIST_END_SENTINAL = "";

    private static final String DEFAULT_TOTAL_CHARGED_START_SENTINAL = "";
    private static final String DEFAULT_TOTAL_CHARGED_END_SENTINAL = "";

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

            // now initialize the sentinals
            receiptOcrSettings.setTransactionIdStartSentinal(DEFAULT_TRANSACTION_ID_START_SENTINAL);
            receiptOcrSettings.setTransactionIdEndSentinal(DEFAULT_TRANSACTION_ID_END_SENTINAL);

            receiptOcrSettings.setVehicleIdStartSentinal(DEFAULT_VEHICLE_ID_START_SENTINAL);
            receiptOcrSettings.setVehicleIdEndSentinal(DEFAULT_VEHICLE_ID_END_SENTINAL);

            receiptOcrSettings.setVehicleMileageStartSentinal(DEFAULT_VEHICLE_MILEAGE_START_SENTINAL);
            receiptOcrSettings.setVehicleMileageEndSentinal(DEFAULT_VEHICLE_MILEAGE_END_SENTINAL);

            receiptOcrSettings.setVehicleVinStartSentinal(DEFAULT_VEHICLE_VIN_START_SENTINAL);
            receiptOcrSettings.setVehicleVinEndSentinal(DEFAULT_VEHICLE_VIN_END_SENTINAL);

            receiptOcrSettings.setServiceChecklistStartSentinal(DEFAULT_SERVICE_CHECKLIST_START_SENTINAL);
            receiptOcrSettings.setServiceChecklistEndSentinal(DEFAULT_SERVICE_CHECKLIST_END_SENTINAL);

            receiptOcrSettings.setTotalChargedStartSentinal(DEFAULT_TOTAL_CHARGED_START_SENTINAL);
            receiptOcrSettings.setTotalChargedEndSentinal(DEFAULT_TOTAL_CHARGED_END_SENTINAL);
        }

        public Builder setHasTransactionId(Boolean setHasTransactionId){
            this.receiptOcrSettings.setHasTransactionId(setHasTransactionId);
            return this;
        }

        public Builder setHasVehicleId(Boolean setHasVehicleId){
            this.receiptOcrSettings.setHasVehicleId(setHasVehicleId);
            return this;
        }

        public Builder setHasVehicleMileage(Boolean setHasVehicleMileage){
            this.receiptOcrSettings.setHasVehicleMileage(setHasVehicleMileage);
            return this;
        }

        public Builder setHasVehicleVin(Boolean setHasVehicleVin){
            this.receiptOcrSettings.setHasVehicleVin(setHasVehicleVin);
            return this;
        }

        public Builder setHasServiceChecklist(Boolean setHasServiceChecklist){
            this.receiptOcrSettings.setHasServiceChecklist(setHasServiceChecklist);
            return this;
        }

        public Builder setHasTotalCharged(Boolean setHasTotalCharged){
            this.receiptOcrSettings.setHasTotalCharged(setHasTotalCharged);
            return this;
        }

        public Builder setTransactionIdStartSentinal(String transactionIdStartSentinal){
            this.receiptOcrSettings.setTransactionIdStartSentinal(transactionIdStartSentinal);
            return this;
        }

        public Builder setTransactionIdEndSentinal(String setTransactionIdEndSentinal){
            this.receiptOcrSettings.setTransactionIdEndSentinal(setTransactionIdEndSentinal);
            return this;
        }

        public Builder setVehicleIdStartSentinal(String setVehicleIdStartSentinal){
            this.receiptOcrSettings.setVehicleIdStartSentinal(setVehicleIdStartSentinal);
            return this;
        }

        public Builder setVehicleIdEndSentinal(String setVehicleIdEndSentinal){
            this.receiptOcrSettings.setVehicleIdEndSentinal(setVehicleIdEndSentinal);
            return this;
        }

        public Builder setVehicleMileageStartSentinal(String setVehicleMileageStartSentinal){
            this.receiptOcrSettings.setVehicleMileageStartSentinal(setVehicleMileageStartSentinal);
            return this;
        }

        public Builder setVehicleMileageEndSentinal(String setVehicleMileageEndSentinal){
            this.receiptOcrSettings.setVehicleMileageEndSentinal(setVehicleMileageEndSentinal);
            return this;
        }

        public Builder setVehicleVinStartSentinal(String setVehicleVinStartSentinal){
            this.receiptOcrSettings.setVehicleVinStartSentinal(setVehicleVinStartSentinal);
            return this;
        }

        public Builder setVehicleVinEndSentinal(String setVehicleVinEndSentinal){
            this.receiptOcrSettings.setVehicleVinEndSentinal(setVehicleVinEndSentinal);
            return this;
        }

        public Builder setServiceChecklistStartSentinal(String setServiceChecklistStartSentinal){
            this.receiptOcrSettings.setServiceChecklistStartSentinal(setServiceChecklistStartSentinal);
            return this;
        }

        public Builder setServiceChecklistEndSentinal(String setServiceChecklistEndSentinal){
            this.receiptOcrSettings.setServiceChecklistEndSentinal(setServiceChecklistEndSentinal);
            return this;
        }

        public Builder setTotalChargedStartSentinal(String setTotalChargedStartSentinal){
            this.receiptOcrSettings.setTotalChargedStartSentinal(setTotalChargedStartSentinal);
            return this;
        }

        public Builder setTotalChargedEndSentinal(String setTotalChargedEndSentinal){
            this.receiptOcrSettings.setTotalChargedEndSentinal(setTotalChargedEndSentinal);
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
