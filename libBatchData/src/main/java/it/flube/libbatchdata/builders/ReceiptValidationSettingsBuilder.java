/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.ReceiptValidationSettings;

/**
 * Created on 3/7/2019
 * Project : Driver
 */
public class ReceiptValidationSettingsBuilder {
    private static final Boolean DEFAULT_USER_MUST_VALIDATE_TRANSACTION_ID = true;

    private static final Boolean DEFAULT_USER_MUST_VALIDATE_VEHICLE_ID = false;
    private static final Boolean DEFAULT_USER_MUST_VALIDATE_VEHICLE_VIN = false;
    private static final Boolean DEFAULT_USER_MUST_VALIDATE_VEHICLE_MILEAGE = false;

    private static final Boolean DEFAULT_USER_MUST_VALIDATE_SERVICE_CHECKLIST = false;

    private static final Boolean DEFAULT_USER_MUST_VALIDATE_TOTAL_CHARGED = true;

    private ReceiptValidationSettings receiptValidationSettings;

    private ReceiptValidationSettingsBuilder(Builder builder){
        this.receiptValidationSettings = builder.receiptValidationSettings;
    }

    private ReceiptValidationSettings getReceiptValidationSettings(){
        return this.receiptValidationSettings;
    }

    public static class Builder {
        private ReceiptValidationSettings receiptValidationSettings;

        public Builder(){
            this.receiptValidationSettings = new ReceiptValidationSettings();

            //set defaults
            this.receiptValidationSettings.setUserMustValidateTransactionId(DEFAULT_USER_MUST_VALIDATE_TRANSACTION_ID);

            this.receiptValidationSettings.setUserMustValidateVehicleId(DEFAULT_USER_MUST_VALIDATE_VEHICLE_ID);
            this.receiptValidationSettings.setUserMustValidateVehicleVin(DEFAULT_USER_MUST_VALIDATE_VEHICLE_VIN);
            this.receiptValidationSettings.setUserMustValidateVehicleMileage(DEFAULT_USER_MUST_VALIDATE_VEHICLE_MILEAGE);

            this.receiptValidationSettings.setUserMustValidateServiceChecklist(DEFAULT_USER_MUST_VALIDATE_SERVICE_CHECKLIST);

            this.receiptValidationSettings.setUserMustValidateTotalCharged(DEFAULT_USER_MUST_VALIDATE_TOTAL_CHARGED);
        }

        private void validate(ReceiptValidationSettings receiptValidationSettings){
            // do nothing
        }

        public ReceiptValidationSettings build(){
            ReceiptValidationSettings receiptValidationSettings = new ReceiptValidationSettingsBuilder(this).getReceiptValidationSettings();
            validate(receiptValidationSettings);
            return receiptValidationSettings;
        }
    }
}
