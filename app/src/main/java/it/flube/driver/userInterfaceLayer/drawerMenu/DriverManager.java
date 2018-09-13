/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu;

import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthAccessDeniedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoProfileEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoUserEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthUserChangedEvent;
import timber.log.Timber;

/**
 * Created on 9/12/2018
 * Project : Driver
 */
public class DriverManager {
    private static final String TAG="DriverManager";

    private Driver driver;
    private Boolean gotDriver;
    private Response response;

    public DriverManager(Response response){
        Timber.tag(TAG).d("creating START...");
        this.response = response;

        if (AndroidDevice.getInstance().getCloudAuth().hasDriver()){
            Timber.tag(TAG).d("...have a driver");
            driver = AndroidDevice.getInstance().getCloudAuth().getDriver();
            gotDriver = true;
        } else {
            Timber.tag(TAG).d("...don't have a driver");
            driver = null;
            gotDriver = false;
        }
        Timber.tag(TAG).d("...listening for driver updates");
        EventBus.getDefault().register(this);
    }

    public void close(){
        driver = null;
        gotDriver=false;
        EventBus.getDefault().unregister(this);
    }

    public Boolean hasDriver(){
        return gotDriver;
    }

    public Driver getDriver(){
        return driver;
    }

    public AccountHeader getAccountHeader(AppCompatActivity activity){
        //String photoUrl = "http://lorempixel.com/60/60/people/";
        if (gotDriver) {
            Timber.tag(TAG).d("driver display name -> " + driver.getNameSettings().getDisplayName());
            Timber.tag(TAG).d("driver email        -> " + driver.getEmail());

            IProfile profile = new ProfileDrawerItem().withName(driver.getNameSettings().getDisplayName())
                    .withEmail(driver.getEmail()).withIcon(driver.getPhotoUrl());

            //.withIcon(R.drawable.demo_profile_pic)
            return new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withCompactStyle(true)
                    .withHeaderBackground(R.drawable.account_header_background)
                    .addProfiles(profile)
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();
        } else {
            //build dummy account header
            Timber.tag(TAG).d("NO DRIVER");
            IProfile profile = new ProfileDrawerItem().withName("No Driver")
                    .withEmail("No email").withIcon(activity.getResources().getDrawable(R.drawable.exclamation_circle_red_1));

            return new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withCompactStyle(true)
                    .withHeaderBackground(R.drawable.account_header_background)
                    .addProfiles(profile)
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthAccessDeniedEvent event){
        Timber.tag(TAG).d("onEvent -> CloudAuthAccessDeniedEvent");
        driver = null;
        gotDriver = false;
        response.driverManangerRedoAccountHeader(driver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthNoProfileEvent event){
        Timber.tag(TAG).d("onEvent -> CloudAuthNoProfileEvent");
        driver = null;
        gotDriver = false;
        response.driverManangerRedoAccountHeader(driver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthNoUserEvent event){
        Timber.tag(TAG).d("onEvent -> CloudAuthNoUserEvent");
        driver = null;
        gotDriver = false;
        response.driverManangerRedoAccountHeader(driver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthUserChangedEvent event){
        ////see if this is a different driver than one we currently have
        if (isDifferentDriver(event.getDriver())){
            Timber.tag(TAG).d("different driver, rebuild account header");

            ///save this driver
            this.driver = event.getDriver();
            gotDriver = true;
            response.driverManangerRedoAccountHeader(driver);

        } else {
            //do nothing
            Timber.tag(TAG).d("same driver, do nothing");
        }
        driver = event.getDriver();
        gotDriver = true;

    }

    private Boolean isDifferentDriver(Driver newDriver){
        Timber.tag(TAG).d("isDifferentDriver START...");
        if (gotDriver){
            Timber.tag(TAG).d("   ...we currently have a driver, let's compare clientId");
            if (driver.getClientId().equals(newDriver.getClientId())){
                Timber.tag(TAG).d("      ...clientId's match, same driver, return FALSE");
                return false;
            } else {
                Timber.tag(TAG).d("      ...clientId's different, different driver, return TRUE");
                return true;
            }
        } else {
            Timber.tag(TAG).d("   ...we don't currently have a driver, so this is a different driver, return TRUE");
            return true;
        }
    }

    public interface Response {
        void driverManangerRedoAccountHeader(Driver driver);
    }





}
