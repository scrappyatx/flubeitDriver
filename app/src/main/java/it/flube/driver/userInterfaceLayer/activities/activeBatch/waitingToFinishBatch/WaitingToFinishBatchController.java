/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseDoAllTheThingsBeforeBatchCanBeFinished;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishBatchRequest;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 6/19/2018
 * Project : Driver
 */
public class WaitingToFinishBatchController implements
        UseCaseDoAllTheThingsBeforeBatchCanBeFinished.Response,
        UseCaseFinishBatchRequest.Response {

    public static final String TAG = "WaitingToFinishBatchController";

    private ReadyToFinishResponse readyResponse;
    private FinishBatchResponse finishResponse;
    private String batchGuid;

    public WaitingToFinishBatchController(){
        Timber.tag(TAG).d("controller CREATED");
    }

    public void getActivityLaunchData(AppCompatActivity activity, WaitingToFinishUtilities.Response response){
        Timber.tag(TAG).d("getActivityLaunchData");
        new WaitingToFinishUtilities().getActivityLaunchInfo(activity, response);
    }

    public void doAllTheThingsBeforeBatchCanBeFinished(String batchGuid, ReadyToFinishResponse readyResponse){
        Timber.tag(TAG).d("doAllTheThingsBeforeBatchCanBeFinished");
        this.readyResponse = readyResponse;
        this.batchGuid = batchGuid;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseDoAllTheThingsBeforeBatchCanBeFinished(AndroidDevice.getInstance(), batchGuid, this));
    }

    public void finishBatchRequest(String batchGuid, FinishBatchResponse finishResponse){
        Timber.tag(TAG).d("finishBatchRequest");
        this.finishResponse = finishResponse;
        this.batchGuid = batchGuid;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseFinishBatchRequest(AndroidDevice.getInstance(), batchGuid, this));
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }

    ////
    ////    UseCaseDoAllTheThingsBeforeBatchCanBeFinished.Response
    ////
    public void batchIsReadyToFinish(String batchGuid){
        Timber.tag(TAG).d("batchIsReadyToFinish");
        readyResponse.batchReadyToFinish();
    }

    public void pendingUploadsRemaining(Integer uploadsPending){
        Timber.tag(TAG).d("pendingUploadsRemaining %s", Integer.toString(uploadsPending));
        readyResponse.pendingImageUploadsRemaining(uploadsPending);
    }

    public void pendingDeviceFileDeletesRemaining(Integer deletesPending){
        Timber.tag(TAG).d("pendingDeviceFileDeletesRemaining %s", deletesPending);
        readyResponse.pendingDeviceFileDeletesRemaining(deletesPending);
    }

    ///
    /// UseCaseFinishBatchRequest.Response
    ///
    public void finishBatchComplete(){
        Timber.tag(TAG).d("finishBatchComplete");
        finishResponse.batchFinished(ActiveBatchManageInterface.ActorType.MOBILE_USER, batchGuid);
    }

    public interface ReadyToFinishResponse {
        void pendingDeviceFileDeletesRemaining(Integer pendingFilesToDelete);

        void pendingImageUploadsRemaining(Integer imagesToUpload);

        void batchReadyToFinish();
    }

    public interface FinishBatchResponse {


        void batchFinished(ActiveBatchManageInterface.ActorType actorType, String batchGuid);
    }

}
