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
import it.flube.driver.dataLayer.useCaseResponseHandlers.GetAccountDetailsResponseHandler;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

import static junit.framework.Assert.assertNotNull;


/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";

    private AccountController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private TextView profileDetail;
    private TextView softwareVersion;

    private Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        profileDetail = (TextView) findViewById(R.id.account_profile_details);
        profileDetail.setVisibility(View.INVISIBLE);

        softwareVersion = (TextView) findViewById(R.id.account_software_version);
        softwareVersion.setText("Version : " + BuildConfig.VERSION_NAME);
        softwareVersion.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("software version = " + BuildConfig.VERSION_NAME);

        logoutButton = (Button) findViewById(R.id.account_logout_button);

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume(){
        super.onResume();

        EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.account_activity_title);
        controller = new AccountController();
        controller.getAccountDetailRequest();

        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()){
            logoutButton.setVisibility(View.INVISIBLE);
        } else {
            logoutButton.setVisibility(View.VISIBLE);
        }

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause(){

        drawer.close();
        controller.close();

        EventBus.getDefault().unregister(this);
        Timber.tag(TAG).d("onPause");

        super.onPause();
    }


    public void clickLogoutButton(View v) {
        //user wants to logout
        Timber.tag(TAG).d("*** user clicked Logout button");
        controller.signOutRequest(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(GetAccountDetailsResponseHandler.UseCaseGetAccountDetailsSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(GetAccountDetailsResponseHandler.UseCaseGetAccountDetailsSuccessEvent.class);

        Timber.tag(TAG).d("*** Profille Detail was updated event");

        String details = "Name --> " + event.getDriver().getNameSettings().getDisplayName() + System.getProperty("line.separator")
                + "Email --> " + event.getDriver().getEmail() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "Client ID --> "  + event.getDriver().getClientId() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "Photo Url --> "  + event.getDriver().getPhotoUrl() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "isDev --> " + event.getDriver().getUserRoles().getDev() + System.getProperty("line.separator")
                + "isQA --> " + event.getDriver().getUserRoles().getQa() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "publicOffersNode --> " + event.getDriver().getCloudDatabaseSettings().getPublicOffersNode() + System.getProperty("line.separator")
                + "personalOffersNode --> " + event.getDriver().getCloudDatabaseSettings().getPersonalOffersNode() + System.getProperty("line.separator")
                + "demoOffersNode --> " + event.getDriver().getCloudDatabaseSettings().getDemoOffersNode() + System.getProperty("line.separator")
                + "scheduledBatchesNode --> " + event.getDriver().getCloudDatabaseSettings().getScheduledBatchesNode() + System.getProperty("line.separator")
                + "activeBatchesNode --> " + event.getDriver().getCloudDatabaseSettings().getActiveBatchNode() + System.getProperty("line.separator");

        Timber.tag(TAG).d("details -->" + details);

        profileDetail.setText(details);
        profileDetail.setVisibility(View.VISIBLE);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(GetAccountDetailsResponseHandler.UseCaseGetAccountDetailsFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(GetAccountDetailsResponseHandler.UseCaseGetAccountDetailsFailureEvent.class);

        Timber.tag(TAG).w("profille detail is not available -> should never get here");
        profileDetail.setText(getResources().getString(R.string.account_profile_details_default));
        profileDetail.setVisibility(View.VISIBLE);
    }



}
