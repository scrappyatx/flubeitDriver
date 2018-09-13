/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.BatchDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.BatchDetailTabLayoutComponents;
import it.flube.libbatchdata.builders.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class BatchItineraryActivity extends AppCompatActivity {

    private static final String TAG = "BatchItineraryActivity";

    private BatchItineraryController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private BatchDetailTitleLayoutComponents batchTitle;
    private BatchDetailTabLayoutComponents batchTab;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_itinerary_new);

        batchTitle = new BatchDetailTitleLayoutComponents(this);
        batchTab = new BatchDetailTabLayoutComponents(this, savedInstanceState, false);

        controller = new BatchItineraryController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onStart(){
        super.onStart();
        batchTab.onStart();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    public void onResume(){
        super.onResume();
        batchTab.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.batch_itinerary_activity_title);


        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()){
            Timber.tag(TAG).d("   ...we have a active batch");

            //set values
            batchTitle.setValues(this, AndroidDevice.getInstance().getActiveBatch().getBatchDetail());
            batchTab.setValues(this,
                    AndroidDevice.getInstance().getActiveBatch().getBatchDetail(),
                    AndroidDevice.getInstance().getActiveBatch().getServiceOrderList(),
                    AndroidDevice.getInstance().getActiveBatch().getRouteStopList());

            //show
            batchTitle.setVisible();
            batchTab.setVisible();

        } else {
            Timber.tag(TAG).d("   ...no active batch");
            batchTitle.setInvisible();
            batchTab.setInvisible();
        }
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }


    @Override
    public void onPause(){

        DrawerMenu.getInstance().clearActivity();

        batchTab.onPause();
        super.onPause();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
    }

    @Override
    public void onStop(){
        super.onStop();
        batchTab.onStop();
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        batchTab.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState (%s)", activityGuid);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        batchTab.onLowMemory();
        Timber.tag(TAG).d("onLowMemory (%s)", activityGuid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controller.close();
        batchTab.onDestroy();
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
    }

}
