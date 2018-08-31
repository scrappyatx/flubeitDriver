/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AddressLocationBuilder;
import it.flube.libbatchdata.builders.ContactPersonBuilder;
import it.flube.libbatchdata.builders.LatLonLocationBuilder;
import it.flube.libbatchdata.builders.ServiceProviderBuilder;
import it.flube.libbatchdata.builders.VehicleBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.ServiceProvider;
import it.flube.libbatchdata.entities.asset.Vehicle;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class DemoBatchUtilities {



    private static final String CUSTOMER_CONTACT_PERSON_GUID = "8793eff8-b7f5-4360-98ea-5187fb2f0bef";
    private static final String CUSTOMER_CONTACT_PERSON_NAME = "John Q. Customer";
    private static final String CUSTOMER_DIAL_PHONE_NUMBER = "5122979032"; // phone number of one of the dev phones
    private static final String CUSTOMER_DISPLAY_PHONE_NUMBER = "(512) XXX-XXXX";
    private static final Boolean CUSTOMER_CAN_SMS = true;
    private static final Boolean CUSTOMER_CAN_VOICE = true;
    private static final Boolean CUSTOMER_HAS_PROXY_PHONE_NUMBER = true;
    private static final String CUSTOMER_CONTACT_PROXY_DIAL_PHONE_NUMBER = "5125732942";
    private static final String CUSTOMER_CONTACT_PROXY_DISPLAY_PHONE_NUMBER = "(512) 573-2942";

    private static final String CUSTOMER_VEHICLE_MAKE = "Audi";
    private static final String CUSTOMER_VEHICLE_MODEL = "A6";
    private static final String CUSTOMER_VEHICLE_YEAR = "2011";
    private static final String CUSTOMER_VEHICLE_COLOR = "black";
    private static final String CUSTOMER_VEHICLE_LICENSE_STATE = "TX";
    private static final String CUSTOMER_VEHICLE_LICENSE_PLATE = "ATX 555";
    private static final String CUSTOMER_VEHICLE_ENGINE_DETAIL = "GS 1.6L MFI DOHC 4cyl";

    private static final Double CUSTOMER_LATITUDE = 30.3007342;
    private static final Double CUSTOMER_LONGITUDE = -97.7545089;

    private static final String CUSTOMER_ADDRESS_STREET = "1606 Mohle Drive";
    private static final String CUSTOMER_ADDRESS_CITY = "Austin";
    private static final String CUSTOMER_ADDRESS_STATE = "TX";
    private static final String CUSTOMER_ADDRESS_ZIP = "78703";

    private static final String SERVICE_PROVIDER_CONTACT_PERSON_GUID = "b898b9d5-2921-42e6-8404-a21a584889b6";
    private static final String SERVICE_PROVIDER_CONTACT_PERSON_NAME = "Billy Oilchange";
    private static final String SERVICE_PROVIDER_DIAL_PHONE_NUMBER = "5122979032"; // phone number of one of the dev phones
    private static final String SERVICE_PROVIDER_DISPLAY_PHONE_NUMBER = "(512) XXX-XXXX";
    private static final Boolean SERVICE_PROVIDER_CAN_SMS = false;
    private static final Boolean SERVICE_PROVIDER_CAN_VOICE = true;
    private static final Boolean SERVICE_PROVIDER_HAS_PROXY_PHONE_NUMBER = false;
    private static final String SERVICE_PROVIDER_PROXY_DIAL_PHONE_NUMBER = "5127483079";
    private static final String SERVICE_PROVIDER_PROXY_DISPLAY_PHONE_NUMBER = "(512) 748-3079";



    private static final String SERVICE_PROVIDER_NAME = "Slippery Lester's Quick Lube";
    private static final String SERVICE_PROVIDER_ADDRESS_STREET = "3700 North Lamar Blvd";
    private static final String SERVICE_PROVIDER_ADDRESS_CITY = "Austin";
    private static final String SERVICE_PROVIDER_ADDRESS_STATE = "TX";
    private static final String SERVICE_PROVIDER_ADDRESS_ZIP = "78705";

    private static final Double SERVICE_PROVIDER_LATITUDE = 30.3046318;
    private static final Double SERVICE_PROVIDER_LONGITUDE = -97.7438983;


    public static ContactPerson getCustomerContactPerson(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return new ContactPersonBuilder.Builder(targetEnvironment)
                .guid(CUSTOMER_CONTACT_PERSON_GUID)
                .contactRole(ContactPerson.ContactRole.CUSTOMER)
                .displayName(CUSTOMER_CONTACT_PERSON_NAME)
                .dialPhoneNumber(CUSTOMER_DIAL_PHONE_NUMBER)
                .displayPhoneNumber(CUSTOMER_DISPLAY_PHONE_NUMBER)
                .canSMS(CUSTOMER_CAN_SMS)
                .canVoice(CUSTOMER_CAN_VOICE)
                .hasProxyPhoneNumber(CUSTOMER_HAS_PROXY_PHONE_NUMBER)
                .proxyDialPhoneNumber(CUSTOMER_CONTACT_PROXY_DIAL_PHONE_NUMBER)
                .proxyDisplayPhoneNumber(CUSTOMER_CONTACT_PROXY_DISPLAY_PHONE_NUMBER)
                .build();
    }

    public static Vehicle getCustomerVehicle(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return new VehicleBuilder.Builder(targetEnvironment)
                .make(CUSTOMER_VEHICLE_MAKE)
                .model(CUSTOMER_VEHICLE_MODEL)
                .year(CUSTOMER_VEHICLE_YEAR)
                .color(CUSTOMER_VEHICLE_COLOR)
                .licenseState(CUSTOMER_VEHICLE_LICENSE_STATE)
                .licensePlate(CUSTOMER_VEHICLE_LICENSE_PLATE)
                .engineDetail(CUSTOMER_VEHICLE_ENGINE_DETAIL)
                .build();
    }

    public static LatLonLocation getCustomerLatLon(){
        return new LatLonLocationBuilder.Builder()
                .location(CUSTOMER_LATITUDE, CUSTOMER_LONGITUDE)
                .build();
    }

    public static AddressLocation getCustomerAddress(){
        return new AddressLocationBuilder.Builder()
                .street(CUSTOMER_ADDRESS_STREET)
                .city(CUSTOMER_ADDRESS_CITY)
                .state(CUSTOMER_ADDRESS_STATE)
                .zip(CUSTOMER_ADDRESS_ZIP)
                .build();
    }

    public static ContactPerson getServiceProviderContactPerson(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return new ContactPersonBuilder.Builder(targetEnvironment)
                .guid(SERVICE_PROVIDER_CONTACT_PERSON_GUID)
                .displayName(SERVICE_PROVIDER_CONTACT_PERSON_NAME)
                .contactRole(ContactPerson.ContactRole.SERVICE_PROVIDER)
                .dialPhoneNumber(SERVICE_PROVIDER_DIAL_PHONE_NUMBER)
                .displayPhoneNumber(SERVICE_PROVIDER_DISPLAY_PHONE_NUMBER)
                .canSMS(SERVICE_PROVIDER_CAN_SMS)
                .canVoice(SERVICE_PROVIDER_CAN_VOICE)
                .hasProxyPhoneNumber(SERVICE_PROVIDER_HAS_PROXY_PHONE_NUMBER)
                .proxyDisplayPhoneNumber(SERVICE_PROVIDER_PROXY_DISPLAY_PHONE_NUMBER)
                .proxyDialPhoneNumber(SERVICE_PROVIDER_PROXY_DIAL_PHONE_NUMBER)
                .build();
    }

    public static ServiceProvider getServiceProvider(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return new ServiceProviderBuilder.Builder(targetEnvironment)
                .iconURL(BatchIconGenerator.getRandomIconUrl(targetEnvironment))
                .name(SERVICE_PROVIDER_NAME)
                .addressLocation(new AddressLocationBuilder.Builder()
                        .street(SERVICE_PROVIDER_ADDRESS_STREET)
                        .city(SERVICE_PROVIDER_ADDRESS_CITY)
                        .state(SERVICE_PROVIDER_ADDRESS_STATE)
                        .zip(SERVICE_PROVIDER_ADDRESS_ZIP)
                        .build())
                .latLonLocation(new LatLonLocationBuilder.Builder()
                        .location(SERVICE_PROVIDER_LATITUDE, SERVICE_PROVIDER_LONGITUDE)
                        .build())
                .contactPerson(getServiceProviderContactPerson(targetEnvironment))
                .build();
    }
}
