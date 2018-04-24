/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.asset.Vehicle;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class VehicleBuilder {
    private Vehicle vehicle;

    private VehicleBuilder(Builder builder){
        this.vehicle = builder.vehicle;
    }

    private Vehicle getVehicle(){
        return vehicle;
    }

    public static class Builder{
        private Vehicle vehicle;

        public Builder(){
            vehicle = new Vehicle();
            vehicle.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.vehicle.setGuid(guid);
            return this;
        }

        public Builder name(String name){
            this.vehicle.setName(name);
            return this;
        }

        public Builder description(String description){
            this.vehicle.setDescription(description);
            return this;
        }

        public Builder make(String make){
            this.vehicle.setMake(make);
            return this;
        }

        public Builder model(String model){
            this.vehicle.setModel(model);
            return this;
        }

        public Builder year(String year){
            this.vehicle.setYear(year);
            return this;
        }

        public Builder color(String color){
            this.vehicle.setColor(color);
            return this;
        }

        public Builder licenseState(String licenseState){
            this.vehicle.setLicenseState(licenseState);
            return this;
        }

        public Builder licensePlate(String licensePlate){
            this.vehicle.setLicensePlate(licensePlate);
            return this;
        }

        private void validate(Vehicle vehicle){
            // required PRESENT (must not be null)
            if (vehicle.getGuid()==null) {
                throw new IllegalStateException("GUID is null");
            }

            if (vehicle.getName()==null){
                throw new IllegalStateException("name is null");
            }

            if (vehicle.getMake()==null){
                throw new IllegalStateException("make is null");
            }

            if (vehicle.getModel()==null){
                throw new IllegalStateException("model is null");
            }

            if (vehicle.getColor()==null){
                throw new IllegalStateException("color is null");
            }

            if (vehicle.getYear()==null){
                throw new IllegalStateException("year is null");
            }

            if (vehicle.getLicenseState()==null){
                throw new IllegalStateException("license state is null");
            }

            if (vehicle.getLicensePlate()==null){
                throw new IllegalStateException("license plate is null");
            }
        }

        public Vehicle build(){
            Vehicle vehicle = new VehicleBuilder(this).getVehicle();
            validate(vehicle);
            return vehicle;
        }

    }
}
