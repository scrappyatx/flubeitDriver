/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class UseCaseReceiptOcr implements
        Runnable,
        DeviceTextDetectionInterface.DeviceDetectReceiptOcrResponse,
        CloudTextDetectionInterface.CloudDetectReceiptOcrResponse,
        CloudActiveBatchInterface.ReceiptRequestOcrResultsResponse {

    private final static String TAG = "UseCaseReceiptOcr";

    private MobileDeviceInterface device;
    private String imageDeviceAbsoluteFilename;
    private UseCaseReceiptOcr.Response response;

    private ReceiptRequest receiptRequest;

    public UseCaseReceiptOcr(MobileDeviceInterface device, ReceiptRequest receiptRequest, UseCaseReceiptOcr.Response response){
        Timber.tag(TAG).d("UseCaseReceiptOcr");
        this.device = device;
        this.receiptRequest = receiptRequest;
        this.response = response;
    }

    private void close(){
        device = null;
        imageDeviceAbsoluteFilename=null;
        receiptRequest = null;
        response = null;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        if (receiptRequest.getHasDeviceFile()){
            // do ocr on this file
            Timber.tag(TAG).d("...has device file, doing ocr analysis");
            readyForOcr();
        } else {
            // do nothing
            Timber.tag(TAG).d("...no device file, do nothing");
            doNothing();
        }
    }

    private void readyForOcr(){

        ////so here's what we do:
        /// 1. first, we do a device text recognition, and see if we get all the required fields
        /// 2. second, if we don't get values for all our expected fields, then we follow up with cloud text recognition

        if (receiptRequest.getReceiptAnalysis().getDoDeviceOcrRecognition()){
            Timber.tag(TAG).d("...doing device text detection");
            device.getDeviceTextDetection().detectReceiptOcrRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, receiptRequest.getReceiptAnalysis().getReceiptOcrSettings(), this);
        } else {
            Timber.tag(TAG).d("...not doing device text detection, check to see if we need to do cloud text detection");
            if (receiptRequest.getReceiptAnalysis().getDoCloudOcrRecognition()){
                Timber.tag(TAG).d("...doing cloud text detection");
                device.getCloudTextDetection().detectReceiptOcrRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, receiptRequest.getReceiptAnalysis().getReceiptOcrSettings(), this);
            } else {
                Timber.tag(TAG).d("...not doing cloud text detection, do nothing");
                doNothing();
            }
        }

    }

    /// interface for device ocr recognition results
    public void deviceDetectReceiptOcrSuccess(ReceiptOcrResults receiptOcrResults){
        Timber.tag(TAG).d("deviceDetectReceiptOcrSuccess");
        //update the receiptRequest
        receiptRequest.getReceiptAnalysis().setDeviceOcrResults(receiptOcrResults);

        //if we got everything we expected, there is no need to try for cloud text detection
        if (receiptOcrResults.getAllExpectedFieldsFound()){
            Timber.tag(TAG).d("...found all expected fields,no need to check for cloud text detection");
            finishUp();
        } else {
            Timber.tag(TAG).d("...did not find all expected fields, check to see if we need cloud text detection");
            if (receiptRequest.getReceiptAnalysis().getDoCloudOcrRecognition()){
                Timber.tag(TAG).d("   ...doing cloud text recognition");
                device.getCloudTextDetection().detectReceiptOcrRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, receiptRequest.getReceiptAnalysis().getReceiptOcrSettings(), this);
            } else {
                Timber.tag(TAG).d("   ...not doing cloud text recognition");
                finishUp();
            }
        }
    }

    public void deviceDetectReceiptOcrFailure(){
        Timber.tag(TAG).d("deviceDetectReceiptOcrFailure");
        //update the receiptRequest
        receiptRequest.getReceiptAnalysis().setDeviceOcrResults(null);

        //check to see if we need to do cloud text ocr;
        if (receiptRequest.getReceiptAnalysis().getDoCloudOcrRecognition()){
            Timber.tag(TAG).d("   ...doing cloud text recognition");
            device.getCloudTextDetection().detectReceiptOcrRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, receiptRequest.getReceiptAnalysis().getReceiptOcrSettings(), this);
        } else {
            Timber.tag(TAG).d("   ...not doing cloud text recognition");
            finishUp();
        }
    }

    /// interface for cloud ocr recognition results
    public void cloudDetectReceiptOcrSuccess(ReceiptOcrResults receiptOcrResults){
        Timber.tag(TAG).d("deviceDetectReceiptOcrSuccess");
        receiptRequest.getReceiptAnalysis().setCloudOcrResults(receiptOcrResults);
        finishUp();
    }

    public void cloudDetectReceiptOcrFailure(){
        Timber.tag(TAG).d("deviceDetectReceiptOcrSuccess");
        receiptRequest.getReceiptAnalysis().setCloudOcrResults(null);
        finishUp();
    }

    /// do nothing
    private void doNothing(){
        Timber.tag(TAG).d("doNothing");
        response.useCaseReceiptOcrComplete(receiptRequest);
        close();
    }

    /// finish up
    private void finishUp(){
        Timber.tag(TAG).d("finishUp");
        device.getCloudActiveBatch().updateReceiptRequestOcrResultsRequest(device.getCloudAuth().getDriver(), receiptRequest, this);
    }

    ///updateReceiptRequest response interface
    public void cloudActiveBatchUpdateReceiptRequestOcrResultsComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdateReceiptRequestOcrResultsComplete");
        response.useCaseReceiptOcrComplete(receiptRequest);
        close();
    }


    public interface Response {
        void useCaseReceiptOcrComplete(ReceiptRequest receiptRequest);
    }
}
