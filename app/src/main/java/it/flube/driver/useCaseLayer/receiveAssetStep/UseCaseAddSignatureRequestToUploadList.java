/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.receiveAssetStep;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.photoStep.UseCaseAddAllPhotosToUploadList;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import timber.log.Timber;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class UseCaseAddSignatureRequestToUploadList implements
        Runnable,
        CloudImageStorageInterface.AddImageToUploadListResponse {

    private final static String TAG = "UseCaseAddSignatureRequestToUploadList";

    private final MobileDeviceInterface device;
    private SignatureRequest signatureRequest;
    private Response response;

    public UseCaseAddSignatureRequestToUploadList(MobileDeviceInterface device, SignatureRequest signatureRequest, Response response){
        this.device = device;
        this.signatureRequest = signatureRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        Driver driver = device.getCloudAuth().getDriver();
        DeviceInfo deviceInfo = device.getDeviceInfo();
        Timber.tag(TAG).d("   ...add signatureRequest to the upload list");
        device.getCloudImageStorage().addSignatureRequestToUploadList(driver, deviceInfo, signatureRequest, this);
    }

    public void cloudImageStorageAddImageToUploadListComplete(String ownerGuid){
        Timber.tag(TAG).d("cloudImageStorageAddImageToUploadListComplete");
        response.addSignatureRequestToUploadListComplete();
    }

    public interface Response {
        void addSignatureRequestToUploadListComplete();
    }
}
