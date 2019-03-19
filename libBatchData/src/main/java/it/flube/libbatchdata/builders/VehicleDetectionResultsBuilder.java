/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.VehicleDetectionResults;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class VehicleDetectionResultsBuilder {
    private VehicleDetectionResults vehicleDetectionResults;

    private VehicleDetectionResultsBuilder(Builder builder){
        this.vehicleDetectionResults = builder.vehicleDetectionResults;
    }

    private VehicleDetectionResults getVehicleDetectionResults(){
        return this.vehicleDetectionResults;
    }

    public static class Builder {
        private VehicleDetectionResults vehicleDetectionResults;

        public Builder(){
            this.vehicleDetectionResults = new VehicleDetectionResults();
            // initialize
            this.vehicleDetectionResults.setFoundLicensePlate(false);
            this.vehicleDetectionResults.setFoundMake(false);
            this.vehicleDetectionResults.setFoundModel(false);

            this.vehicleDetectionResults.setLicensePlate(null);
            this.vehicleDetectionResults.setMake(null);
            this.vehicleDetectionResults.setModel(null);
        }

        private void validate(VehicleDetectionResults vehicleDetectionResults){
            //do nothing
        }

        public VehicleDetectionResults build(){
            VehicleDetectionResults vehicleDetectionResults = new VehicleDetectionResultsBuilder(this).getVehicleDetectionResults();
            validate(vehicleDetectionResults);
            return vehicleDetectionResults;
        }
    }
}
