/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseDoAllTheThingsBeforeBatchCanBeFinished;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishBatchRequest;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 6/19/2018
 * Project : Driver
 */
public class WaitingToFinishBatchActivity extends AppCompatActivity implements
        WaitingToFinishBatchLayoutComponents.FinishedAnimationResponse,
        WaitingToFinishUtilities.Response,
        UseCaseDoAllTheThingsBeforeBatchCanBeFinished.Response,
        UseCaseFinishBatchRequest.Response {


    private static final String TAG = "WaitingToFinishBatchActivity";

    private ActivityNavigator navigator;
    private WaitingToFinishBatchController controller;
    private DrawerMenu drawer;

    private WaitingToFinishBatchLayoutComponents components;
    private String batchGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.tag(TAG).d( "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_waiting_to_finish);

        components = new WaitingToFinishBatchLayoutComponents(this);
        components.setGone();
    }

    @Override
    public void onResume() {
        Timber.tag(TAG).d( "onResume");
        super.onResume();
        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.batch_waiting_to_finish_activity_title);
        controller = new WaitingToFinishBatchController();

        controller.getActivityLaunchData(this, this);

    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d( "onPause");

        drawer.close();
        controller.close();
        components.close();

        super.onPause();
    }

    ////
    //// interface for WaitingToFinishUtilities.Response
    ////
    public void getActivityLaunchDataSuccess(String batchGuid){
        Timber.tag(TAG).d("getActivityLaunchDataSuccess, batchGuid -> " + batchGuid);
        this.batchGuid = batchGuid;
        //// start waiting animation
        components.showWaitingAnimation(this);
        controller.doAllTheThingsBeforeBatchCanBeFinished(batchGuid, this);
    }

    public void getActivityLaunchDataFailure(){
        Timber.tag(TAG).d("getActivityLaunchDataFailure -> this should never happen");
        navigator.gotoActivityHome(this);
    }



    ////
    ////    UseCaseDoAllTheThingsBeforeBatchCanBeFinished.Response
    ////
    public void batchIsReadyToFinish(String batchGuid){
        Timber.tag(TAG).d("batchFinished");
        //show complete animation
        components.showFinishedAnimation(this, this);

    }

    ////
    //// interface for WaitingToFinishBatchLayoutComponents
    ////
    public void finishedAnimationComplete(){
        Timber.tag(TAG).d("finishedAnimationComplete");
        //complete animation is finished
        components.stopFinishedAnimation();
        controller.finishBatchRequest(batchGuid, this);
    }

    ///
    ///  UseCaseFinishBatchRequest.Response
    ///
    public void finishBatchComplete(){
        Timber.tag(TAG).d("finishBatchComplete");
    }

}
