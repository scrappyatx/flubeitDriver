/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import android.graphics.Bitmap;

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
        CloudImageStorageInterface.StopMonitoringForFilesToUploadResponse,
        CloudImageStorageInterface.AddPhotoRequestListToUploadListResponse {

    private final static String TAG = "UseCaseAddAllPhotosToUploadList";

    private final MobileDeviceInterface device;
    private ArrayList<PhotoRequest> photoList;
    private Response response;

    public UseCaseAddAllPhotosToUploadList(MobileDeviceInterface device, ArrayList<PhotoRequest> photoList, Response response){
        this.device = device;
        this.photoList = photoList;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        /// first, stop monitoring for files to upload (will get turned on again when we go to the next step
        device.getCloudImageStorage().stopMonitoringForFilesToUploadRequest(this);
    }

    public void cloudImageStorageStopMonitoringForFilesToUploadComplete(){
        Timber.tag(TAG).d("cloudImageStorageStopMonitoringForFilesToUploadComplete");

        // now add all photo requests to the update list
        Driver driver = device.getCloudAuth().getDriver();
        DeviceInfo deviceInfo = device.getDeviceInfo();
        Timber.tag(TAG).d("   ...add photoList to the upload list");
        device.getCloudImageStorage().addPhotoRequestListToUploadList(driver, deviceInfo, photoList, this);
    }

    public void cloudImageStorageAddPhotoRequestListToUploadListComplete() {
        Timber.tag(TAG).d("   ...cloudImageStorageAddPhotoRequestListToUploadListComplete");
        /// now respond that they have all been added
        response.addAllPhotosToUploadListComplete();
    }


    public interface Response {
        void addAllPhotosToUploadListComplete();
    }
}
