/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.LatLonLocation;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class Destination {
    public enum DestinationType {
        SERVICE_PROVIDER,
        CUSTOMER_HOME,
        CUSTOMER_WORK,
        OTHER
    }

    public enum VerificationMethod {
        AUTO_BY_GPS,
        MANUAL_BY_USER,
        NO_VERIFICATION_PERFORMED
    }

    private LatLonLocation targetLatLon;
    private LatLonLocation actualLatLon;
    private AddressLocation targetAddress;
    private VerificationMethod targetVerificationMethod;
    private DestinationType targetType;

    public void setTargetLatLon(LatLonLocation targetLatLon) {
        this.targetLatLon = targetLatLon;
    }

    public LatLonLocation getTargetLatLon(){
        return targetLatLon;
    }

    public void setActualLatLon(LatLonLocation actualLatLon) {
        this.actualLatLon = actualLatLon;
    }

    public LatLonLocation getActualLatLon(){
        return actualLatLon;
    }

    public void setTargetAddress(AddressLocation targetAddress) {
        this.targetAddress = targetAddress;
    }

    public AddressLocation getTargetAddress(){
        return targetAddress;
    }

    public void setTargetVerificationMethod(VerificationMethod targetVerificationMethod) {
        this.targetVerificationMethod = targetVerificationMethod;
    }

    public VerificationMethod getTargetVerificationMethod(){
        return targetVerificationMethod;
    }

    public void setTargetType(DestinationType targetType) {
        this.targetType = targetType;
    }

    public DestinationType getTargetType() {
        return targetType;
    }
}
