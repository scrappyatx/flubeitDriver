/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.builders.BuilderUtilities;
import timber.log.Timber;

import static junit.framework.Assert.assertNotNull;


/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class AccountActivity extends AppCompatActivity
    implements
    AccountController.Response,
    AccountActivityLayoutComponents.Response {

    private static final String TAG = "AccountActivity";

    private String activityGuid;

    private AccountController controller;
    private AccountActivityLayoutComponents layoutComponents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        controller = new AccountController();
        layoutComponents = new AccountActivityLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume(){
        super.onResume();
        DrawerMenu.getInstance().setActivity(this, R.string.account_activity_title);
        controller.getAccountDetailRequest(this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause(){
        DrawerMenu.getInstance().close();
        super.onPause();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);

    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }


    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        layoutComponents.close();
        super.onDestroy();
    }



    ////layout component resposne
    public void logoutButtonClicked() {
        Timber.tag(TAG).d("logoutButtonClicked");
        controller.signOutRequest(this);
    }


    /// getAccountDetailRequest Response
    public void getAccountDetailSuccess(Driver driver) {
        Timber.tag(TAG).d("getAccountDetailSuccess");
        layoutComponents.setValuesDriver(driver);
        layoutComponents.setVisible();
    }


    public void getAccountDetailFailure() {
        Timber.tag(TAG).d("getAccountDetailFailure");
        layoutComponents.setValuesNoDriver(this);
        layoutComponents.setVisible();
    }



}
