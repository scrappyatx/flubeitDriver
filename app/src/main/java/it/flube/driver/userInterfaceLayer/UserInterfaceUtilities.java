/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/10/2017
 * Project : Driver
 */

public class UserInterfaceUtilities {
    private static final String TAG = "UserInterfaceUtilities";

    /// font awesome icons to use to indicate the task type
    /// fontawesome.io
    private static final String NAVIGATION_ICON = "{fa-road}";
    private static final String PHOTO_ICON = "{fa-camera}";
    private static final String WAIT_FOR_USER_TRIGGER_ICON = "{fa-cog}";
    private static final String WAIT_FOR_EXTERNAL_TRIGGER_ICON = "{fa-clock-o}";
    private static final String AUTHORIZE_PAYMENT_ICON = "{fa-credit-card}";
    private static final String GIVE_ASSET_ICON = "{fa-sign-out}";
    private static final String RECEIVE_ASSET_ICON = "{fa-sign-in}";
    private static final String WAIT_FOR_SERVICE_ON_ASSET_ICON = "{fa-wrench}";





    public static String getCountIconUrl(Integer count){
        switch(count) {
            case 1:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F1_circle_c1600.png?alt=media&token=36f3eb68-2a08-4cf3-bc50-ebecd4689985";
            case 2:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F2_circle_c1600.png?alt=media&token=992cbf90-53af-4ed2-aa6d-293cb451f71b";
            case 3:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F3_circle_c1600.png?alt=media&token=6bd0afa2-fa04-4dda-9a41-a9269ee50572";
            case 4:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F4_circle_c1600.png?alt=media&token=9dca371c-9b32-432a-a97e-d67131adf46e";
            case 5:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F5_circle_filled1600.png?alt=media&token=cf08cbd7-828d-441f-ad4e-10d6753ebc66";
            case 6:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F6_circle_c1600.png?alt=media&token=c9ed6111-09f0-48c5-9ee2-8eda1a7104f0";
            case 7:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F7_circle_c1600.png?alt=media&token=5bb1f0d0-a12f-44dd-bcee-5a6b5bf3f8b5";
            case 8:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F8_circle_c1600.png?alt=media&token=a731bf00-6baa-4221-8e9c-45c2d14c2c5f";
            case 9:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F9_circle_c1600.png?alt=media&token=631e6fa5-9ec6-42d1-b4ea-f8c1cbf1b6d1";
            default:
                return "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/numberImages%2F1_circle_c1600.png?alt=media&token=36f3eb68-2a08-4cf3-bc50-ebecd4689985";
        }
    }

    public static String getStepTaskTypeIcon(OrderStepInterface.TaskType taskType) {
        switch (taskType) {
            case NAVIGATION:
                return NAVIGATION_ICON;
            case TAKE_PHOTOS:
                return PHOTO_ICON;
            case WAIT_FOR_EXTERNAL_TRIGGER:
                return WAIT_FOR_USER_TRIGGER_ICON;
            case WAIT_FOR_USER_TRIGGER:
                return WAIT_FOR_EXTERNAL_TRIGGER_ICON;
            case AUTHORIZE_PAYMENT:
                return AUTHORIZE_PAYMENT_ICON;
            case GIVE_ASSET:
                return GIVE_ASSET_ICON;
            case RECEIVE_ASSET:
                return RECEIVE_ASSET_ICON;
            case WAIT_FOR_SERVICE_ON_ASSET:
                return WAIT_FOR_SERVICE_ON_ASSET_ICON;
            default:
                return "";
        }
    }
}
