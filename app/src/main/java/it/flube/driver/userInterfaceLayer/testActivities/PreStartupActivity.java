/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.testActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.PreStartupController;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivity;
import it.flube.driver.userInterfaceLayer.activities.DemoActivity;
import it.flube.driver.userInterfaceLayer.activities.EarningsActivity;
import it.flube.driver.userInterfaceLayer.activities.HelpActivity;
import it.flube.driver.userInterfaceLayer.activities.HomeActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.HomeNoActiveBatchActivity;
import it.flube.driver.userInterfaceLayer.activities.signIn.LoginActivity;
import it.flube.driver.userInterfaceLayer.activities.MessagesActivity;
import it.flube.driver.userInterfaceLayer.activities.OffersActivity;
import it.flube.driver.userInterfaceLayer.activities.ScheduledBatchesActivity;
import it.flube.driver.userInterfaceLayer.activities.splashScreen.SplashScreenActivity;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoAccountActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoDemoActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoEarningsActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHelpActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoLoginActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoMessagesActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoOffersActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoScheduledBatchesActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoSplashScreenActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.preStartupActivity.DriverWasUpdatedEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.preStartupActivity.ResultWasUpdatedEvent;
import it.flube.driver.userInterfaceLayer.drawerMenu.NavigationMenu;

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
    private Drawer mDrawer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE+"_"+BuildConfig.VERSION_NAME);}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_startup);
        Log.d(TAG, "SplashScreenActivity CREATED");

        //setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);

        mDrawer = new NavigationMenu(this, mToolbar).getDrawer();

        //get UI elements
        mFirstName = (TextView) findViewById(R.id.firstName_value);
        mLastName = (TextView) findViewById(R.id.lastName_value);
        mClientId = (TextView) findViewById(R.id.clientId_value);
        mEmail = (TextView) findViewById(R.id.email_value);
        mResult = (TextView) findViewById(R.id.result_value);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);


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

        Intent intent = new Intent(this,SplashScreenActivity.class);
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
    //get the GotoMainActivityEventDELETE
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoSplashScreenActivityEvent event) {
        Log.d(TAG,"*** GotoSplashScreenActivityEvent received");
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        Log.d(TAG,"*** Sent intent to start the activity <SplashScreenActivity>");
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

      /* ---------------------------------------
     Activity Navigation Events
     ----------------------------------------- */

    //event bus events
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoAccountActivityEvent event) {
        Intent i = new Intent(this, AccountActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start AccountActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoDemoActivityEvent event) {
        Intent i = new Intent(this, DemoActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start DemoActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoEarningsActivityEvent event) {
        Intent i = new Intent(this, EarningsActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start EarningsActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoHelpActivityEvent event) {
        Intent i = new Intent(this, HelpActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start HelpActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoHomeActiveBatchActivityEventDELETE event) {
        Intent i = new Intent(this, HomeActiveBatchActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start HomeActiveBatchActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoHomeNoActiveBatchActivityEventDELETE event) {
        Intent i = new Intent(this, HomeNoActiveBatchActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start HomeNoActiveBatchActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoMessagesActivityEvent event) {
        Intent i = new Intent(this, MessagesActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start MessagesActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoOffersActivityEvent event) {
        Intent i = new Intent(this, OffersActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start OffersActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoScheduledBatchesActivityEvent event) {
        Intent i = new Intent(this, ScheduledBatchesActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start ScheduledBatchesActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoLoginActivityEvent event) {
        Intent i = new Intent(this, LoginActivity.class);
        this.startActivity(i);
        Log.d(TAG,"sent intent to start LoginActivity");
    }


}
