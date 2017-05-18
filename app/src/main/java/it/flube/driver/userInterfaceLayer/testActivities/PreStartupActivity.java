/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.testActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.PreStartupController;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activityNavigation.GotoStartupActivityEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.preStartupActivity.DriverWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.preStartupActivity.ResultWasUpdatedEvent;
import it.flube.driver.userInterfaceLayer.activities.StartupActivity;

public class PreStartupActivity extends AppCompatActivity {
    private static final String TAG = "PreStartupActivity";

    //controller for this activity
    private PreStartupController mController;

    //UI elements for this activity
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mClientId;
    private TextView mEmail;
    private TextView mResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE+"_"+BuildConfig.VERSION_NAME);}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_startup);
        Log.d(TAG, "StartupActivity CREATED");

        //get UI elements
        mFirstName = (TextView) findViewById(R.id.firstName_value);
        mLastName = (TextView) findViewById(R.id.lastName_value);
        mClientId = (TextView) findViewById(R.id.clientId_value);
        mEmail = (TextView) findViewById(R.id.email_value);
        mResult = (TextView) findViewById(R.id.result_value);

    }

    // subscribe to EventBus
    @Override
    public void onStart() {
        Log.d(TAG, "onStart() START");
        super.onStart();

        //register on eventbus
        EventBus.getDefault().register(this);

        //instantiate contoller for this activity
        mController = new PreStartupController(getApplicationContext());

        //clear the driver singleton to get started
        mController.clearDriverSingleton();

        //set results to invisible
        mResult.setVisibility(View.INVISIBLE);

        Log.d(TAG, "onStart() END");
    }

    //unsubscribe to EventBus
    @Override
    public void onStop() {
        Log.d(TAG, "onStop() START");
        EventBus.getDefault().unregister(this);
        super.onStop();
        Log.d(TAG, "onStop() END");
    }

    // button click handlers

    public void clickLoad(View v) {
        Log.d(TAG,"*** clicked Load Button ");
        mController.loadDriver();
    }

    public void clickSave(View v) {
        Log.d(TAG,"*** clicked Save button");
        mController.saveDriver();
    }

    public void clickDelete(View v) {
        Log.d(TAG,"*** clicked Delete button");
        mController.deleteDriver();
    }

    public void clickLoadTestData1(View v) {
        Log.d(TAG, "*** clicked Test Data 1 button");
        mController.loadDriverTestData1();
    }

    public void clickLoadTestData2(View v) {
        Log.d(TAG, "*** clicked Test Data 2 button");
        mController.loadDriverTestData2();
    }

    public void clickClearSingleton(View v) {
        Log.d(TAG, "*** clicked Clear Data button");
        mController.clearDriverSingleton();
    }

    public void clickLoadDriverProfile(View v) {
        Log.d(TAG,"*** clicked LoadDriverProfile button");
        mController.requestDriverProfile();
    }

    public void clickGetAblyToken(View v) {
        Log.d(TAG,"*** clicked GetAblyToken button ");
        mController.requestAblyToken();
    }

    public void clickGoToStartupActivity(View v) {
        Log.d(TAG,"*** clicked GoToStartupActivity Button ");

        Intent intent = new Intent(this,StartupActivity.class);
        startActivity(intent);
        Log.d(TAG,"*** Sent intent to start StartActivity");
     }

     public void clickGoToAblyTestActivity(View v) {
         Log.d(TAG,"*** clicked GoToAblyTestActivity Button ");

         Intent intent = new Intent(this,AblyTestActivity.class);
         startActivity(intent);
         Log.d(TAG,"*** Sent intent to start AblyTestActivity");

     }

    //event bus events
    //get the GotoMainActivityEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoStartupActivityEvent event) {
        Log.d(TAG,"*** GotoStartupActivityEvent received");
        Intent intent = new Intent(this, StartupActivity.class);
        startActivity(intent);
        Log.d(TAG,"*** Sent intent to start the activity <StartupActivity>");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DriverWasUpdatedEvent event) {
        Log.d(TAG, "*** Driver data was updated event");
        mFirstName.setText(event.getDriver().getFirstName());
        mLastName.setText(event.getDriver().getLastName());
        mClientId.setText(event.getDriver().getClientId());
        mEmail.setText(event.getDriver().getEmail());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ResultWasUpdatedEvent event) {
        Log.d(TAG, "*** Result was updated event");
        mResult.setText(event.getResultMessage());
        mResult.setVisibility(View.VISIBLE);
    }

}
