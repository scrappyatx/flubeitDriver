/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.facebook.device.yearclass.YearClass;
import com.jaredrummler.android.device.DeviceName;

import java.util.UUID;

import it.flube.driver.modelLayer.entities.DeviceInfo;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created on 3/10/2018
 * Project : Driver
 */

public class DeviceDetails implements DeviceName.Callback {
    private static final String TAG = "DeviceDetails";

    private static final String DEVICE_PREFS = "DevicePrefs";
    private static final String DEVICE_GUID_FIELD_NAME = "guid";

    private DeviceInfo deviceInfo;
    private Response response;

    public DeviceDetails(Context appContext, Response response){
        this.response = response;
        initializeDeviceInfo(appContext);
    }

    public DeviceInfo getDeviceInfo(){
        return deviceInfo;
    }

    ////
    ////
    ////

    private void initializeDeviceInfo(Context appContext){


        //initialize device Info
        this.deviceInfo = new DeviceInfo();

        //set the device Guid, globally unique to this device
        deviceInfo.setDeviceGUID(getDeviceGuidFromSharedPreferences(appContext));

        //set the year the device was considered high-end
        //uses https://github.com/facebook/device-year-class
        deviceInfo.setYearWhenDeviceConsideredHighEnd(Integer.toString(YearClass.get(appContext)));

        // set the version API and version Release API
        deviceInfo.setVersionAPI(Integer.toString(Build.VERSION.SDK_INT));
        deviceInfo.setVersionReleaseAPI(Build.VERSION.RELEASE);

        //set default market name
        deviceInfo.setMarketName(Build.MODEL);

        // set the device model name, will overwrite our default market name with a friendlier name
        // uses https://github.com/jaredrummler/AndroidDeviceNames
        DeviceName.with(appContext).request(this);
    }

    private String getDeviceGuidFromSharedPreferences(Context appContext) {
        //check to see if a device GUID has been saved to shared preferences on this device
        // if NO, then create one and save it
        // read device GUID from shared preferences and return it
        SharedPreferences prefs = appContext.getSharedPreferences(DEVICE_PREFS, MODE_PRIVATE);
        if (!prefs.contains(DEVICE_GUID_FIELD_NAME)) {
            SharedPreferences.Editor editor;
            editor = prefs.edit();
            editor.putString(DEVICE_GUID_FIELD_NAME, UUID.randomUUID().toString());
            editor.apply();
        }
        return prefs.getString(DEVICE_GUID_FIELD_NAME, null);
    }

    /// callback from https://github.com/jaredrummler/AndroidDeviceNames
    /// we set the model details if it returned with no exceptions
    public void onFinished(DeviceName.DeviceInfo info, Exception exception) {

        if (exception == null) {
            deviceInfo.setManufacturer(info.manufacturer);
            deviceInfo.setMarketName(info.marketName);
            deviceInfo.setModel(info.model);
            deviceInfo.setCodeName(info.codename);
        }
        response.deviceDetailsUpdateComplete();
    }

    public interface Response {
        void deviceDetailsUpdateComplete();
    }

}
