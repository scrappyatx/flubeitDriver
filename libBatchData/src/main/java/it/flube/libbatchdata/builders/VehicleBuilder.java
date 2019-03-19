/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.asset.Vehicle;
import it.flube.libbatchdata.interfaces.AssetInterface;
import it.flube.libbatchdata.utilities.BuilderUtilities;

import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.DEFAULT_DISPLAY_IMAGE_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.DEFAULT_DISPLAY_IMAGE_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.DEFAULT_DISPLAY_IMAGE_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.DEFAULT_DISPLAY_IMAGE_URL_STAGING;


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

        ///public Builder(){
        ///    initializeStuff(DEFAULT_TARGET_ENVIRONMENT);
        ///}

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            vehicle = new Vehicle();
            vehicle.setGuid(BuilderUtilities.generateGuid());

            //set the asset type
            vehicle.setAssetType(AssetInterface.AssetType.VEHICLE);

            //set the display image url
            switch (targetEnvironment){
                case PRODUCTION:
                    vehicle.setDisplayImageUrl(DEFAULT_DISPLAY_IMAGE_URL_PRODUCTION);
                    break;
                case DEMO:
                    vehicle.setDisplayImageUrl(DEFAULT_DISPLAY_IMAGE_URL_DEMO);
                    break;
                case STAGING:
                    vehicle.setDisplayImageUrl(DEFAULT_DISPLAY_IMAGE_URL_STAGING);
                    break;
                case DEVELOPMENT:
                    vehicle.setDisplayImageUrl(DEFAULT_DISPLAY_IMAGE_URL_DEVELOPMENT);
                    break;
            }
        }

        public Builder guid(String guid){
            this.vehicle.setGuid(guid);
            return this;
        }

        public Builder displayImageUrl(String displayImageUrl){
            this.vehicle.setDisplayImageUrl(displayImageUrl);
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

        public Builder engineDetail(String engineDetail){
            this.vehicle.setEngineDetail(engineDetail);
            return this;
        }

        private void validate(Vehicle vehicle){
            // required PRESENT (must not be null)
            if (vehicle.getGuid()==null) {
                throw new IllegalStateException("GUID is null");
            }

            if (vehicle.getMake()==null){
                throw new IllegalStateException("make is null");
            }

            if (vehicle.getModel()==null){
                throw new IllegalStateException("model is null");
            }

            if (vehicle.getColor()==null){
                //throw new IllegalStateException("color is null");
            }

            if (vehicle.getYear()==null){
                throw new IllegalStateException("year is null");
            }

            if (vehicle.getLicenseState()==null){
                //throw new IllegalStateException("license state is null");
            }

            if (vehicle.getLicensePlate()==null){
                //throw new IllegalStateException("license plate is null");
            }

            if (vehicle.getDisplayImageUrl()==null){
                throw new IllegalStateException("display image url is null");
            }
        }

        private void postProcess(Vehicle vehicle){
            ///
            /// put the pertinent vehicle information into the "generic asset" fields shared by all assets
            ///
            vehicle.setDisplayTitle(vehicle.getYear() + " " + vehicle.getMake() + vehicle.getModel());
            vehicle.setDisplayDescription(vehicle.getColor());
            vehicle.setDisplayIdentifier(vehicle.getLicenseState() + " " + vehicle.getLicensePlate());
            vehicle.setDetailInfo(vehicle.getEngineDetail());
        }

        public Vehicle build(){
            Vehicle vehicle = new VehicleBuilder(this).getVehicle();
            validate(vehicle);
            postProcess(vehicle);
            return vehicle;
        }

    }
}
