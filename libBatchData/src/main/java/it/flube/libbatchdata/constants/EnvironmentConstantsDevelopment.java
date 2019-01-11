/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.constants;

/**
 * Created on 1/3/2019
 * Project : Driver
 */
public class EnvironmentConstantsDevelopment {

    //////////////
    ///  Four of the builder classes and one demo batch utility class contain references to files in firebase storage.
    ///  These references are in the form of download URLs, which are specific to a particular firebase instance.
    ///
    ///  Since we are using four different firebase instances, one for each of our target environments, we need
    ///  a unique download url PER TARGET ENVIRONMENT for each resource.
    ///
    ///  This file has all the target environment specific resources for the DEVELOPMENT environment
    //////////////


    ///
    ///  ContactPersonBuilder CONSTANTS
    ///
    /// **** DEVELOPMENT
    public static final String DEFAULT_CUSTOMER_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fcustomer.png?alt=media&token=57dc7e80-9091-44ec-aa58-e4acaaa496f7";
    public static final String DEFAULT_FLUBEIT_SUPPORT_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fflubeit_support.png?alt=media&token=5c4a0bf0-ac4b-486f-9ed5-1ea9f3381623";
    public static final String DEFAULT_SERVICE_PROVIDER_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fservice_provider.png?alt=media&token=4bd7bb42-0e27-4bb1-8e54-5e03836b4ed0";

    ///
    ///  PhotoRequestBuilder CONSTANTS
    ///
    public static final String DEFAULT_NO_ATTEMPT_IMAGE_URL_DEVELOPMENT= "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2Fno_attempts_photo_image_2.png?alt=media&token=82c4c53a-371a-4122-9d11-249344f22557";

    ///
    ///  PhotoRequestListForVehicleBuilder CONSTANTS
    ///
    /// **** DEVELOPMENT
    public static final String FRONT_CORNER_DRIVER_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Ffront_corner_driver_view.jpg?alt=media&token=de13718e-1484-4d51-8ff3-93b50e333d8b";
    public static final String FRONT_CORNER_PASSENGER_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Ffront_corner_passenger_view.jpg?alt=media&token=0579fbaa-2177-456c-88cc-7b55b2b4d836";
    public static final String FRONT_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Ffront_view.jpg?alt=media&token=d7aa3c0c-4d0f-44df-889b-1b0f28b9441e";
    public static final String REAR_CORNER_DRIVER_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Frear_corner_driver_view.jpg?alt=media&token=bdd59a46-c008-453c-94ab-8a3ef0a2339a";
    public static final String REAR_CORNER_PASSENGER_VIEW_HINT_URL_DEVELOPMENT="https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Frear_corner_passenger_view.jpg?alt=media&token=88322f4d-9d79-49a8-ad14-9a4462269e30";
    public static final String REAR_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Frear_view.jpg?alt=media&token=9b1539e5-b458-4f68-a40d-c969fd0d15c8";
    public static final String SIDE_DRIVER_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Fside_driver_view.jpg?alt=media&token=255a6147-7a2b-4911-95de-4488e8fe575b";
    public static final String SIDE_PASSENGER_VIEW_HINT_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Fside_passenger_view.jpg?alt=media&token=bf0529ef-de2e-4f95-b35f-5c8502f57703";

    ///
    ///  VehicleBuilder CONSTANTS
    ///
    public static final String DEFAULT_DISPLAY_IMAGE_URL_DEVELOPMENT="https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/orderStepImages%2FassetTransferImages%2Fasset_vehicle_icon.png?alt=media&token=9439942b-e667-47e7-94a6-2b06f42e2d48";

    ///
    /// ServiceProviderBuilder CONSTANTS
    ///
    public static final String DEFAULT_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Foil-change-icon.png?alt=media&token=b1599ce2-67ec-4bda-9a4b-d3a4ae8cdea3";

    ///
    /// DistanceIndicators
    ///
    public static final String LONG_DISTANCE_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_long_ver2.png?alt=media&token=3e95c94d-9a53-4be3-acdb-7efc0ed83339";
    public static final String MED_DISTANCE_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_med_ver2.png?alt=media&token=dc311b19-d4f2-491d-af3b-f85fa5c0c019";
    public static final String SHORT_DISTANCE_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_short_ver2.png?alt=media&token=aad103c9-a6ca-42ef-b086-74547bafe92e";

    ///
    /// Demo Oil Change Provider Logos
    ///
    public static final String SERVICE_ONE_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fservice_one_logo.PNG?alt=media&token=8116007c-8a04-4e49-9a19-bf9ecb36676f";
    public static final String KWIKKAR_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fkwikkar.PNG?alt=media&token=e60e8956-bb62-4758-abab-811cd1e6546b";
    public static final String EXPRESS_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fexpressoilchange.PNG?alt=media&token=4414e2f8-59ca-474d-95aa-4c7ee424481b";
    public static final String JIFFYLUBE_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fjiffy-lube_ver2.png?alt=media&token=d4d79f18-1d4c-463f-98f3-11b3572bfd37";
    public static final String GENERIC_ICON_URL_DEVELOPMENT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Foil-change-icon.png?alt=media&token=b1599ce2-67ec-4bda-9a4b-d3a4ae8cdea3";

    ///
    ///  BatchNotificationSettings
    ///
    ///
    // notifications when order is placed by customer
    public static final Boolean SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_DEVELOPMENT = true;

    // notifications to driver when this batch is available as an offer
    public static final Boolean SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_DEVELOPMENT = false;
    public static final Boolean SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_DEVELOPMENT = false;

    // notifications when offer expires without being claimed
    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_DEVELOPMENT = false;

    // notifications to driver prior to batch starting
    public static final int UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_DEVELOPMENT = 5;
    public static final Boolean SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEVELOPMENT = true;
    public static final Boolean SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEVELOPMENT = true;

    // notifications to driver for batch late start
    public static final int LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_DEVELOPMENT = 10;
    public static final int LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_DEVELOPMENT = 90;
    public static final Boolean SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEVELOPMENT = true;
    public static final Boolean VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEVELOPMENT = true;

    // notifications to send when batch is removed from driver due to NO START
    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_DEVELOPMENT = true;

    ///  ServiceOrderNotificationSettings
    ///
    // notifications when order is started
    public static final Boolean SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT = false;
    public static final Boolean SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT = false;
    public static final Boolean SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT = false;

    //notifications when driver is navigating to customer
    public static final Boolean SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEVELOPMENT = false;
    public static final Boolean SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEVELOPMENT = false;


    // notifications to send if an order step is late
    public static final int STEP_LATE_MINUTES_DEVELOPMENT = 2;
    public static final int STEP_VERY_LATE_MINUTES_DEVELOPMENT = 5;

    public static final Boolean SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT = false;
    public static final Boolean SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT = false;
    public static final Boolean SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT = false;

    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_DEVELOPMENT = false;
    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_DEVELOPMENT = false;

}
