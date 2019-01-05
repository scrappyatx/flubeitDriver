/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.constants;

/**
 * Created on 1/3/2019
 * Project : Driver
 */
public class EnvironmentConstantsDemo {

    //////////////
    ///  Four of the builder classes and one demo batch utility class contain references to files in firebase storage.
    ///  These references are in the form of download URLs, which are specific to a particular firebase instance.
    ///
    ///  Since we are using four different firebase instances, one for each of our target environments, we need
    ///  a unique download url PER TARGET ENVIRONMENT for each resource.
    ///
    ///  This file has all the target environment specific resources for the DEMO environment
    //////////////

    ///
    ///  ContactPersonBuilder CONSTANTS

    public static final String DEFAULT_CUSTOMER_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fcustomer.png?alt=media&token=31a4c9d4-2ffe-4570-a4a9-d20c080e1722";
    public static final String DEFAULT_FLUBEIT_SUPPORT_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fflubeit_support.png?alt=media&token=2cf128cf-d2a7-40de-88bd-7034f2b83880";
    public static final String DEFAULT_SERVICE_PROVIDER_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fservice_provider.png?alt=media&token=377a0d99-e595-4f2d-9549-26e35eb632fa";

    ///
    ///  PhotoRequestBuilder CONSTANTS
    ///
    public static final String DEFAULT_NO_ATTEMPT_IMAGE_URL_DEMO= "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2Fno_attempts_photo_image_2.png?alt=media&token=abfec932-76d0-497d-8ebb-899022285150";


    ///
    ///  PhotoRequestListForVehicleBuilder CONSTANTS
    ///
    /// **** DEMO
    public static final String FRONT_CORNER_DRIVER_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_corner_driver_view.jpg?alt=media&token=9e4fedac-7013-4ca5-88d1-b1a80e2e56e3";
    public static final String FRONT_CORNER_PASSENGER_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_corner_passenger_view.jpg?alt=media&token=a332ee8a-04bf-4410-b926-1de394315e3e";
    public static final String FRONT_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_view.jpg?alt=media&token=60dfb6e3-d4b0-4350-842a-adc72f1720d2";
    public static final String REAR_CORNER_DRIVER_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_corner_driver_view.jpg?alt=media&token=ba4e69d0-6a1a-4e0c-bac6-f30a13913281";
    public static final String REAR_CORNER_PASSENGER_VIEW_HINT_URL_DEMO="https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_corner_passenger_view.jpg?alt=media&token=98e5e9a7-d9d2-4466-b4e4-aa22562d22dc";
    public static final String REAR_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_view.jpg?alt=media&token=ef11594a-ff2f-46db-9ed4-c1d43e43e34d";
    public static final String SIDE_DRIVER_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Fside_driver_view.jpg?alt=media&token=8ffc8989-f919-41e0-b2e7-143a8e3ded88";
    public static final String SIDE_PASSENGER_VIEW_HINT_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Fside_passenger_view.jpg?alt=media&token=9dfc6422-59af-40b6-949b-1005660a7391";

    ///
    ///  VehicleBuilder CONSTANTS
    ///
    public static final String DEFAULT_DISPLAY_IMAGE_URL_DEMO="https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2ForderStepImages%2FassetTransfer%2Fasset_vehicle_icon.png?alt=media&token=765a6c0d-0436-4ab3-b05e-badab713fd45";

    ///
    /// ServiceProviderBuilder CONSTANTS
    ///
    public static final String DEFAULT_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FserviceProviders%2Foil-change-icon.png?alt=media&token=5ccf5b98-a331-4a25-93d7-b5df0f439e5a";

    ///
    /// DistanceIndicators
    ///
    public static final String LONG_DISTANCE_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_long_ver2.png?alt=media&token=acc0c5a8-8602-409e-98da-c7b9f78ca6bf";
    public static final String MED_DISTANCE_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_med_ver2.png?alt=media&token=2eb5e177-d88c-48be-ab6a-dbc68bc7b206";
    public static final String SHORT_DISTANCE_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_short_ver2.png?alt=media&token=acf5f514-5257-407c-abdb-80f0c8e21d44";


    ///
    /// Demo Oil Change Provider Logos
    ///
    public static final String SERVICE_ONE_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fservice_one_logo.PNG?alt=media&token=a1d0d776-65fc-4f5d-8f55-17937928e257";
    public static final String KWIKKAR_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fkwikkar.PNG?alt=media&token=4ea0e9e1-1549-46fb-b452-5b8952a7d710";
    public static final String EXPRESS_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fexpressoilchange.PNG?alt=media&token=0e1bca7b-1252-4497-9bd0-e8a9b3679759";
    public static final String JIFFYLUBE_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fjiffy-lube_ver2.png?alt=media&token=01d08897-c2f8-4e26-b703-5dbf6b51a2f9";
    public static final String GENERIC_ICON_URL_DEMO = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-demo.appspot.com/o/mobileAppDriver%2FserviceProviders%2Foil-change-icon.png?alt=media&token=5ccf5b98-a331-4a25-93d7-b5df0f439e5a";
}
