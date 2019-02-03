/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.testOffers;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOfferAlerts;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchSimpleTwoStep;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;
import timber.log.Timber;

/**
 * Created on 6/26/2018
 * Project : Driver
 */
public class TestOffersMakeController implements
    UseCaseMakeDemoBatchRequest.Response {

    private final String TAG = "TestOffersMakeController";

    private MobileDeviceInterface device;
    private ExecutorService useCaseExecutor;
    private CreateTestBatchResponse response;

    public TestOffersMakeController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();

        Timber.tag(TAG).d("created");
    }


    public void createTestBatchRequest(TestOfferOption option, CreateTestBatchResponse response){
        Timber.tag(TAG).d("createTestBatchRequest");
        this.response = response;
        DemoBatchInterface optionClass = TestOfferUtilities.getClassByName(option.getClassName());
        if (optionClass != null){
            useCaseExecutor.execute(new UseCaseMakeDemoBatchRequest(device, optionClass, this));
        } else {
            Timber.tag(TAG).d("...couldn't get option class name");
            response.testBatchCreateFailure();
        }
    }

    ////
    //// UseCaseMakeDemoBatchRequest response
    ////
    public void makeDemoBatchSuccess(){
        Timber.tag(TAG).d("makeDemoBatchSuccess");
        //new DemoOfferAlerts().showDemoOfferCreatedAlert(activity, this);
        response.testBatchCreateSuccess();
    }

    public void makeDemoBatchFailure(){
        Timber.tag(TAG).d("makeDemoBatchFailure");
        response.testBatchCreateFailure();
    }

    public void close(){
        device = null;
        useCaseExecutor = null;
    }


    public interface CreateTestBatchResponse {
        void testBatchCreateSuccess();
        void testBatchCreateFailure();
    }
}
