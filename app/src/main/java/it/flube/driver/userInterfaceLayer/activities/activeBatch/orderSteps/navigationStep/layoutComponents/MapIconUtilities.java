/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 9/20/2018
 * Project : Driver
 */
public class MapIconUtilities {
    private static final String TAG="MapIconUtilities";

    public static Bitmap getServiceProviderActiveIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getServiceProviderActiveIcon");
        return BitmapFactory.decodeResource(activity.getResources(),R.drawable.map_icon_service_provider_active);
    }

    public static Bitmap getServiceProviderInactiveIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getServiceProviderInactiveIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_icon_service_provider_inactive);
    }

    public static Bitmap getCustomerHomeActiveIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getCustomerHomeActiveIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_icon_customer_home_active);
    }

    public static Bitmap getCustomerHomeInactiveIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getCustomerHomeInactiveIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_icon_customer_home_inactive);
    }

    public static Bitmap getCustomerWorkActiveIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getCustomerWorkActiveIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_icon_customer_work_active);
    }

    public static Bitmap getCustomerWorkInactiveIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getCustomerWorkInactiveIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_icon_customer_work_inactive);
    }

    public static Bitmap getDriverLocationIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getDriverLocationIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.map_icon_driver_location);
    }

    public static Bitmap getOtherIcon(AppCompatActivity activity){
        Timber.tag(TAG).d("getOtherIcon");
        return BitmapFactory.decodeResource(activity.getResources(), R.drawable.mapbox_marker);
    }


}
