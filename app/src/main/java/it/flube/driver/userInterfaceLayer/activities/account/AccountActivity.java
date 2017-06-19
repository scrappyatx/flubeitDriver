/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.rollbar.android.Rollbar;

import junit.framework.Assert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.AccountController;
import it.flube.driver.userInterfaceLayer.drawerMenu.NavigationMenu;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoAccountActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoDemoActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoEarningsActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHelpActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoLoginActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoMessagesActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoOffersActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoScheduledBatchesActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.accountActivity.ProfileDetailNotAvailableEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.accountActivity.ProfileDetailWasUpdatedEvent;
import timber.log.Timber;

import static junit.framework.Assert.assertNotNull;


/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class AccountActivity extends AppCompatActivity implements AccountActivityInterface {
    private static final String TAG = "AccountActivity";

    private AccountController mController;
    private Toolbar mToolbar;
    private SwitchCompat mSwitch;
    private Drawer mDrawer;

    private TextView mProfileDetail;

     /* ------------------------------------------------------------------
     Activity Lifecycle Overrides - onCreate

     1.  Instantiate Rollbar (if required)
     2.  Call superclass onCreate()
     3.  Inflate the view associated with this activity
     4.  Create toolbar & navigation menu
     ------------------------------------------------------------------ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        //setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.account_activity_title);

        mSwitch = (SwitchCompat) findViewById(R.id.switch_looking_for_work);

        mDrawer = new NavigationMenu(this, mToolbar).getDrawer();

        //setup UI elements
        mProfileDetail = (TextView) findViewById(R.id.account_profile_details);
        mProfileDetail.setVisibility(View.INVISIBLE);

        //instantiate controller for this activity
        mController = new AccountController(this, this);

        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onStart() {
        super.onStart();
        Timber.tag(TAG).d(TAG, "onStart");
    }

    //unsubscribe to EventBus
    @Override
    public void onStop() {
        super.onStop();
        Timber.tag(TAG).d(TAG, "onStop");
    }

    /* ---------------------------------------
     UI button click event
     ----------------------------------------- */
    public void clickLogoutButton(View v) {
        //user wants to logout
        Timber.tag(TAG).d("*** user clicked Logout button");
        mController.signOut();
    }

    /* ---------------------------------------
     UI update Events
     ----------------------------------------- */

    public void ProfileDetailUpdate(String firstName, String lastName, String email, String role, String clientId){

        Timber.tag(TAG).d("*** Profille Detail was updated event");

        String details = "Name --> " + firstName + " " + lastName  + System.getProperty("line.separator")
                + "Email --> " + email + System.getProperty("line.separator")
                + "Role --> " +  role + System.getProperty("line.separator")
                + "Client ID --> "  + clientId;

        Timber.tag(TAG).d("details -->" + details);

        mProfileDetail.setText(details);
        mProfileDetail.setVisibility(View.VISIBLE);
    }

    public void ProfileDetailNotAvailable(String message) {
        Timber.tag(TAG).w("*** Profille Detail is not available -> should never get here");
        mProfileDetail.setText(message);
        mProfileDetail.setVisibility(View.VISIBLE);
    }

}
