/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.ReceiptValidationResults;
import it.flube.libbatchdata.interfaces.ReceiptValidationInterface;

/**
 * Created on 3/8/2019
 * Project : Driver
 */
public class ReceiptValidationResultsBuilder {
    private static final ReceiptValidationInterface.DataSource DEFAULT_SOURCE_TRANSACTION_ID = ReceiptValidationInterface.DataSource.NONE;
    private static final ReceiptValidationInterface.DataSource DEFAULT_SOURCE_VEHICLE_ID = ReceiptValidationInterface.DataSource.NONE;
    private static final ReceiptValidationInterface.DataSource DEFAULT_SOURCE_VEHICLE_VIN = ReceiptValidationInterface.DataSource.NONE;
    private static final ReceiptValidationInterface.DataSource DEFAULT_SOURCE_VEHICLE_MILEAGE = ReceiptValidationInterface.DataSource.NONE;
    private static final ReceiptValidationInterface.DataSource DEFAULT_SOURCE_TOTAL_CHARGED = ReceiptValidationInterface.DataSource.NONE;
    private static final ReceiptValidationInterface.DataSource DEFAULT_SOURCE_SERVICE_CHECKLIST = ReceiptValidationInterface.DataSource.NONE;

    private ReceiptValidationResults receiptValidationResults;

    private ReceiptValidationResultsBuilder(Builder builder){
        this.receiptValidationResults = builder.receiptValidationResults;
    }

    private ReceiptValidationResults getReceiptValidationResults(){
        return this.receiptValidationResults;
    }

    public static class Builder {
        private ReceiptValidationResults receiptValidationResults;

        public Builder(){
            this.receiptValidationResults = new ReceiptValidationResults();
            this.receiptValidationResults.setTransactionIdSource(DEFAULT_SOURCE_TRANSACTION_ID);
            this.receiptValidationResults.setVehicleIdSource(DEFAULT_SOURCE_VEHICLE_ID);
            this.receiptValidationResults.setVehicleVinSource(DEFAULT_SOURCE_VEHICLE_VIN);
            this.receiptValidationResults.setVehicleMileageSource(DEFAULT_SOURCE_VEHICLE_MILEAGE);
            this.receiptValidationResults.setTotalChargeSource(DEFAULT_SOURCE_TOTAL_CHARGED);
            this.receiptValidationResults.setServiceChecklistSource(DEFAULT_SOURCE_SERVICE_CHECKLIST);
        }

        private void validate(ReceiptValidationResults receiptValidationResults){
            //do nothing
        }

        public ReceiptValidationResults build(){
            ReceiptValidationResults receiptValidationResults = new ReceiptValidationResultsBuilder(this).getReceiptValidationResults();
            validate(receiptValidationResults);
            return receiptValidationResults;
        }

    }
}
