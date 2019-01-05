/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.constants;

/**
 * Created on 1/3/2019
 * Project : Driver
 */
public class EnvironmentConstantsStaging {

    //////////////
    ///  Four of the builder classes and one demo batch utility class contain references to files in firebase storage.
    ///  These references are in the form of download URLs, which are specific to a particular firebase instance.
    ///
    ///  Since we are using four different firebase instances, one for each of our target environments, we need
    ///  a unique download url PER TARGET ENVIRONMENT for each resource.
    ///
    ///  This file has all the target environment specific resources for the STAGING environment
    //////////////

    ///
    ///  ContactPersonBuilder CONSTANTS
    ///
    public static final String DEFAULT_CUSTOMER_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fcustomer.png?alt=media&token=1be95e0d-4897-4cd8-95a0-8153422ec55a";
    public static final String DEFAULT_FLUBEIT_SUPPORT_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fflubeit_support.png?alt=media&token=689093e3-1a08-4eba-9342-a669e6cff6fb";
    public static final String DEFAULT_SERVICE_PROVIDER_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fservice_provider.png?alt=media&token=38820456-5494-4d97-a052-df11f86ec26f";

    ///
    ///  PhotoRequestBuilder CONSTANTS
    ///
    public static final String DEFAULT_NO_ATTEMPT_IMAGE_URL_STAGING= "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2Fno_attempts_photo_image_2.png?alt=media&token=2231bd6e-e936-4137-a1c1-8f627b14ba8b";

    ///
    ///  PhotoRequestListForVehicleBuilder CONSTANTS
    ///
    public static final String FRONT_CORNER_DRIVER_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_corner_driver_view.jpg?alt=media&token=bc3bd5b9-d870-4f50-819c-26004d16a3cf";
    public static final String FRONT_CORNER_PASSENGER_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_corner_passenger_view.jpg?alt=media&token=6d5df192-2bdd-4cc1-ab72-256f1492cc4c";
    public static final String FRONT_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_view.jpg?alt=media&token=10be4d86-927c-4290-a233-bb97ed300ef2";
    public static final String REAR_CORNER_DRIVER_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_corner_driver_view.jpg?alt=media&token=fa124adc-4abb-487b-a042-dab750a7ecd3";
    public static final String REAR_CORNER_PASSENGER_VIEW_HINT_URL_STAGING="https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_corner_passenger_view.jpg?alt=media&token=95cf0f58-a5bd-463e-8f2d-2ea331613453";
    public static final String REAR_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_view.jpg?alt=media&token=02f8802b-3f61-42f1-ba19-c33b41ee4451";
    public static final String SIDE_DRIVER_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Fside_driver_view.jpg?alt=media&token=1859aff2-a25b-444a-b683-3e9b4478ec7c";
    public static final String SIDE_PASSENGER_VIEW_HINT_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Fside_passenger_view.jpg?alt=media&token=24a64d62-10ae-4280-b2e5-5d8bfb2b6ad2";

    ///
    ///  VehicleBuilder CONSTANTS
    ///
    public static final String DEFAULT_DISPLAY_IMAGE_URL_STAGING="https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2ForderStepImages%2FassetTransfer%2Fasset_vehicle_icon.png?alt=media&token=f493fb9d-8028-4d16-8320-4d3f1a95c165";

    ///
    /// ServiceProviderBuilder CONSTANTS
    ///
    public static final String DEFAULT_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FserviceProviders%2Foil-change-icon.png?alt=media&token=9bd94245-cf13-4f59-be12-b33c6ff2c7fa";

    ///
    /// DistanceIndicators
    ///
    public static final String LONG_DISTANCE_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_long_ver2.png?alt=media&token=ad6ced8a-0521-4563-9f3e-dcf0a96c59b9";
    public static final String MED_DISTANCE_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_med_ver2.png?alt=media&token=c48cc836-d30d-443a-9ada-742b70fa0cdd";
    public static final String SHORT_DISTANCE_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_short_ver2.png?alt=media&token=86345649-2179-4aee-986f-a132361d86ee";

    ///
    /// Demo Oil Change Provider Logos
    ///
    public static final String SERVICE_ONE_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fservice_one_logo.PNG?alt=media&token=2ded7781-e7eb-4597-86ae-b73b5c08ad9d";
    public static final String KWIKKAR_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fkwikkar.PNG?alt=media&token=b55cdbe6-97ce-485e-bb72-67cba15602cc";
    public static final String EXPRESS_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fexpressoilchange.PNG?alt=media&token=32437720-ba2f-4ebf-9943-0657aaa93bd2";
    public static final String JIFFYLUBE_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fjiffylube.PNG?alt=media&token=5aadf683-7e69-4c81-9df0-bad715e04bc2";
    public static final String GENERIC_ICON_URL_STAGING = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-staging.appspot.com/o/mobileAppDriver%2FserviceProviders%2Foil-change-icon.png?alt=media&token=9bd94245-cf13-4f59-be12-b33c6ff2c7fa";


}
