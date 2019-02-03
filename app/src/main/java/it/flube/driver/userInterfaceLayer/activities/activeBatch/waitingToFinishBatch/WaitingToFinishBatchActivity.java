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
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 6/19/2018
 * Project : Driver
 */
public class WaitingToFinishBatchActivity extends AppCompatActivity implements
        WaitingToFinishBatchLayoutComponents.FinishedAnimationResponse,
        WaitingToFinishUtilities.Response,
        WaitingToFinishBatchController.ReadyToFinishResponse,
        WaitingToFinishBatchController.FinishBatchResponse {


    private static final String TAG = "WaitingToFinishBatchActivity";

    private String activityGuid;

    private WaitingToFinishBatchController controller;

    private WaitingToFinishBatchLayoutComponents components;
    private String batchGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_batch_waiting_to_finish);


        controller = new WaitingToFinishBatchController();
        components = new WaitingToFinishBatchLayoutComponents(this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
        DrawerMenu.getInstance().setActivityDontMonitorActiveBatch(this, R.string.batch_waiting_to_finish_activity_title);
        controller.getActivityLaunchData(this, this);
        super.onResume();

    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        super.onPause();
        DrawerMenu.getInstance().close();
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
        components.close();
        super.onDestroy();

    }

    ////
    //// interface for WaitingToFinishUtilities.Response
    ////
    public void getActivityLaunchDataSuccess(String batchGuid){
        Timber.tag(TAG).d("getActivityLaunchDataSuccess (%s), batchGuid -> " + batchGuid, activityGuid);
        this.batchGuid = batchGuid;
        //// start waiting animation
        components.showWaitingAnimation(this);
        controller.doAllTheThingsBeforeBatchCanBeFinished(batchGuid, this);
    }

    public void getActivityLaunchDataFailure(){
        Timber.tag(TAG).w("getActivityLaunchDataFailure (%s) -> this should never happen", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }



    ////
    ////    UseCaseDoAllTheThingsBeforeBatchCanBeFinished.Response
    ////
    public void batchReadyToFinish(){
        Timber.tag(TAG).d("batchReadyToFinish (%s)", activityGuid);
        //show complete animation
        components.showFinishedAnimation(this, this);

    }

    public void pendingDeviceFileDeletesRemaining(Integer pendingFilesToDelete){
        Timber.tag(TAG).d("pendingDeviceFileDeletesRemaining %s",pendingFilesToDelete );
        //TODO pass this to layout component to update the UI
    }

    public void pendingImageUploadsRemaining(Integer imagesToUpload){
        Timber.tag(TAG).d("pendingImageUploadsRemaining %s",imagesToUpload );
        //TODO pass this to layout component to update the UI
    }

    ////
    //// interface for WaitingToFinishBatchLayoutComponents
    ////
    public void finishedAnimationComplete(){
        Timber.tag(TAG).d("finishedAnimationComplete (%s)", activityGuid);
        //complete animation is finished
        components.stopFinishedAnimation();
        controller.finishBatchRequest(batchGuid, this);
    }

    ///
    ///  WaitingToFinishController.batch finish response
    ///
    public void batchFinished(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("batchFinished (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHomeAndShowBatchFinishedMessage(this, actorType, batchGuid);
    }

}
