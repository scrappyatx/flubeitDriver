/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AddressLocationBuilder;
import it.flube.libbatchdata.builders.ContactPersonBuilder;
import it.flube.libbatchdata.builders.LatLonLocationBuilder;
import it.flube.libbatchdata.builders.VehicleBuilder;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.asset.Vehicle;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class DemoBatchUtilities {

    public static ContactPerson getCustomerContactPerson(){
        return new ContactPersonBuilder.Builder()
                .contactRole(ContactPerson.ContactRole.CUSTOMER)
                .displayName("John Q. Customer")
                .displayPhoneNumber("(512) 555-1212")
                .build();
    }

    public static Vehicle getCustomerVehicle(){
        return new VehicleBuilder.Builder()
                .name("customer's vehicle")
                .make("Audi")
                .model("A6")
                .year("2011")
                .color("black")
                .licenseState("TX")
                .licensePlate("ATX 555")
                .build();
    }

    public static LatLonLocation getCustomerLatLon(){
        return new LatLonLocationBuilder.Builder()
                .location(30.3007342, -97.7545089)
                .build();
    }

    public static AddressLocation getCustomerAddress(){
        return new AddressLocationBuilder.Builder()
                .street("1606 Mohle Drive")
                .city("Austin")
                .state("TX")
                .zip("78703")
                .build();
    }
}
