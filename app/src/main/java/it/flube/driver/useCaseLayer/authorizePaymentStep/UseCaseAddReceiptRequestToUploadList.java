/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.receiveAssetStep.UseCaseAddSignatureRequestToUploadList;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import timber.log.Timber;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class UseCaseAddReceiptRequestToUploadList implements
        Runnable,
        CloudImageStorageInterface.AddImageToUploadListResponse {

    private final static String TAG = "UseCaseAddReceiptRequestToUploadList";

    private MobileDeviceInterface device;
    private ReceiptRequest receiptRequest;
    private Response response;

    public UseCaseAddReceiptRequestToUploadList(MobileDeviceInterface device, ReceiptRequest receiptRequest, Response response){
        this.device = device;
        this.receiptRequest = receiptRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        Driver driver = device.getCloudAuth().getDriver();
        DeviceInfo deviceInfo = device.getDeviceInfo();
        Timber.tag(TAG).d("   ...add receiptRequest to the upload list");
        device.getCloudImageStorage().addReceiptRequestToUploadList(driver, deviceInfo, receiptRequest, this);
    }

    public void cloudImageStorageAddImageToUploadListComplete(String ownerGuid){
        Timber.tag(TAG).d("cloudImageStorageAddImageToUploadListComplete");
        response.addReceiptRequestToUploadListComplete();
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
        device = null;
        receiptRequest = null;
    }

    public interface Response {
        void addReceiptRequestToUploadListComplete();
    }
}
