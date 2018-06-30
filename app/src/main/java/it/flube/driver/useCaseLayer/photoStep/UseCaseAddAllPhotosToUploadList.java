/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 6/23/2018
 * Project : Driver
 */
public class UseCaseAddAllPhotosToUploadList implements
        Runnable,
        CloudImageStorageInterface.AddDeviceImageResponse {

    private final static String TAG = "UseCaseAddAllPhotosToUploadList";

    private final MobileDeviceInterface device;
    private ArrayList<PhotoRequest> photoList;
    private Response response;

    private ResponseCounter responseCounter;

    public UseCaseAddAllPhotosToUploadList(MobileDeviceInterface device, ArrayList<PhotoRequest> photoList, Response response){
        this.device = device;
        this.photoList = photoList;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        Driver driver = device.getUser().getDriver();
        DeviceInfo deviceInfo = device.getDeviceInfo();

        responseCounter = new ResponseCounter(photoList.size());

        for (PhotoRequest photoRequest : photoList){

            Timber.tag(TAG).d("   ...adding photo " + photoRequest.getSequence());
            device.getCloudImageStorage().addDeviceImageToActiveBatchUploadList(driver, deviceInfo, photoRequest.getDeviceAbsoluteFileName(),
                    photoRequest.getBatchGuid(), photoRequest.getServiceOrderGuid(), photoRequest.getStepGuid(), photoRequest.getGuid(),
                    this);
        }

    }

    public void cloudImageStorageAddDeviceImageComplete(){
        Timber.tag(TAG).d("   ...cloudImageStorageAddDeviceImageComplete");
        checkIfFinished();
    }

    private void checkIfFinished(){
        responseCounter.onResponse();
        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("         ...FINISHED!");
            response.addAllPhotosToUploadListComplete();
        } else {
            Timber.tag(TAG).d("         ...response " + responseCounter.getCount());
        }
    }

    public interface Response {
        void addAllPhotosToUploadListComplete();
    }
}
