/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptPhotoTake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 8/31/2018
 * Project : Driver
 */
public class ReceiptPhotoTakeActivity extends AppCompatActivity
    implements
    ReceiptPhotoTakeLayoutComponents.Response,
    ReceiptPhotoTakeController.GetDriverAndAuthorizePaymentStepResponse,
    ReceiptPhotoTakeController.UpdateReceiptRequestWithImageDeviceFilenameResponse {

    private static final String TAG="ReceiptPhotoTakeActivity";

    private String activityGuid;

    private ReceiptPhotoTakeController controller;
    private ReceiptPhotoTakeLayoutComponents layoutComponents;

    private Boolean haveDeviceImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);

        setContentView(R.layout.activity_receipt_photo_take);

        controller = new ReceiptPhotoTakeController();
        layoutComponents = new ReceiptPhotoTakeLayoutComponents(this, this);

        haveDeviceImageFile = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.getDriverAndAuthorizePaymentStep(this);
        layoutComponents.onResume();
        Timber.tag(TAG).d("onResume (%s)", activityGuid);


    }

    @Override
    public void onPause(){
        layoutComponents.onPause();
        super.onPause();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);

    }

    @Override
    public void onBackPressed() {
        Timber.tag(TAG).d("onBackPressed");
        if (layoutComponents.hasDeviceFile()){
            Timber.tag(TAG).d("...going to receipt detail");
            ActivityNavigator.getInstance().gotoActivityReceiptDetail(this);
        } else {
            Timber.tag(TAG).d("...going to active batch step");
            ActivityNavigator.getInstance().gotoActiveBatchStep(this);
        }

    }

    @Override
    public void onDestroy(){
        controller.close();
        layoutComponents.close();
        controller = null;
        layoutComponents = null;

        super.onDestroy();
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
    }


    /// receiptTakePhotoLayoutComponents response interface
    public void receiptTakePhotoComplete(String imageDeviceAbsoluteFileName, ReceiptRequest receiptRequest){
        Timber.tag(TAG).d("receiptTakePhotoComplete (%s)", activityGuid);
        controller.updateReceiptRequestWithImageDeviceFilename(imageDeviceAbsoluteFileName, receiptRequest, this);
    }

    /// interface for updating the image device filename
    public void updateReceiptRequestWithImageDeviceFilenameComplete(){
        Timber.tag(TAG).d("updateReceiptRequestWithImageDeviceFilenameComplete (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityReceiptDetail(this);
    }

    /// controller GetDriverAndAuthorizePaymentStep response
    public void getDriverAndAuthorizePaymentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("getDriverAndAuthorizePaymentStepSuccess (%s)", activityGuid);
        layoutComponents.setValues(orderStep.getReceiptRequest());
        layoutComponents.setVisible();
    }

    public void getDriverAndAuthorizePaymentStepFailureDriverOnly(Driver driver){
        Timber.tag(TAG).d("getDriverAndAuthorizePaymentStepFailureDriverOnly (%s)", activityGuid);
        layoutComponents.setInvisible();
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void getDriverAndAuthorizePaymentStepFailureNoDriverNoStep(){
        Timber.tag(TAG).d("getDriverAndAuthorizePaymentStepFailureNoDriverNoStep (%s)", activityGuid);
        layoutComponents.setInvisible();
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    public void getDriverAndAuthorizePaymentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType){
        Timber.tag(TAG).d("getDriverAndAuthorizePaymentStepFailureStepMismatch (%s)", activityGuid);
        layoutComponents.setInvisible();
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

}
