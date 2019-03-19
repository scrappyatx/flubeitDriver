/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageMatchResults;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 3/9/2019
 * Project : Driver
 */
public class UseCaseImageAnalysis implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        DeviceImageDetectionInterface.DeviceDetectImageLabelResponse,
        DeviceImageDetectionInterface.DeviceMatchImageLabelResponse,
        CloudImageDetectionInterface.CloudDetectImageLabelResponse,
        CloudImageDetectionInterface.CloudMatchImageLabelResponse,
        CloudActiveBatchInterface.PhotoRequestDeviceAbsoluteFileNameResponse {

    private final static String TAG = "UseCaseImageAnalysis";

    private MobileDeviceInterface device;
    private String imageDeviceAbsoluteFilename;
    private PhotoRequest photoRequest;
    private Response response;

    public UseCaseImageAnalysis(MobileDeviceInterface device, String imageDeviceAbsoluteFilename, PhotoRequest photoRequest, Response response){
        Timber.tag(TAG).d("UseCaseImageAnalysis");
        this.device = device;
        this.imageDeviceAbsoluteFilename = imageDeviceAbsoluteFilename;
        this.photoRequest = photoRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        if (photoRequest.getHasDeviceFile()){
            //if this photoRequest already has a device file, we need to delete this old file and save the new file name to the photoRequest
            Timber.tag(TAG).d("...deleting OLD image file -> " + photoRequest.getDeviceAbsoluteFileName());
            device.getDeviceImageStorage().deleteImageRequest(photoRequest.getDeviceAbsoluteFileName(), this);
        } else {
            //put this filename into the photoRequest & detect image label
            Timber.tag(TAG).d("...no prior image file, go straight to label detection");
            photoRequest.setDeviceAbsoluteFileName(imageDeviceAbsoluteFilename);
            photoRequest.setHasDeviceFile(true);
            detectImageLabel();
        }
    }

    private void close(){
        Timber.tag(TAG).d("close");
        device = null;
        imageDeviceAbsoluteFilename = null;
        photoRequest = null;
        response = null;
    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        Timber.tag(TAG).d("deviceImageStorageDeleteComplete");
        // put the new file name into the photoRequest object
        photoRequest.setDeviceAbsoluteFileName(imageDeviceAbsoluteFilename);
        photoRequest.setHasDeviceFile(true);

        //NOW do a detectImageLabel on the image
        Timber.tag(TAG).d("...deviceImageStorageDeleteComplete, detecting image label");
        detectImageLabel();
    }

    //// once old photo is deleted, we can detect label
    private void detectImageLabel(){

        ///here's what we do
        /// 1. do device image detection (if we need to), and do imageAnalysis (if we need to)
        /// 2. if we didn't get a match, then do cloud image detection (if we are allowed to)

        if (photoRequest.getImageAnalysis().getDoDeviceImageLabelDetection()) {
            Timber.tag(TAG).d("going to do device image label detection");
            ///we are going to do device image analysis
            if (photoRequest.getImageAnalysis().getDoDeviceImageLabelMatching()){
                Timber.tag(TAG).d("   ...with image matching ");
                device.getDeviceImageDetection().detectImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, this);
            } else {
                Timber.tag(TAG).d("   ...but no image matching");
                device.getDeviceImageDetection().matchImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, photoRequest.getImageAnalysis().getImageMatchSettings(), this);
            }
        } else {
            Timber.tag(TAG).d("NOT going to do device image label detection");
            /// see if we need to do cloud image detection
            if (photoRequest.getImageAnalysis().getDoCloudImageLabelDetection()){
                Timber.tag(TAG).d("we are going to do cloud image label detection");
                if (photoRequest.getImageAnalysis().getDoCloudImageLabelMatching()){
                    Timber.tag(TAG).d("   ...with image matching");
                    device.getCloudImageDetection().detectImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, this);
                } else {
                    Timber.tag(TAG).d("   ...but no image matching");
                    device.getCloudImageDetection().matchImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, photoRequest.getImageAnalysis().getImageMatchSettings(),this);
                }
            } else {
                Timber.tag(TAG).d("we are NOT going to do cloud image label detection");
                ///just update from here, no analysis at all
                finishUp();
            }
        }
    }

    /// response interface for Device image label detection
    public void deviceDetectImageLabelSuccess(ImageDetectionResults imageDetectionResults){
        //// we are doing detection ONLY, no matching, so we are done
        Timber.tag(TAG).d("deviceDetectImageLabelSuccess");
        photoRequest.getImageAnalysis().setDeviceImageDetectionResults(imageDetectionResults);
        finishUp();
    }

    public void deviceDetectImageLabelFailure(){
        Timber.tag(TAG).d("deviceDetectImageLabelFailure");
        photoRequest.getImageAnalysis().setDeviceImageDetectionResults(null);
        //// we are doing detection ONLY, but it failed, so let's try cloud if we are allowed
        if (photoRequest.getImageAnalysis().getDoCloudImageLabelDetection()){
            Timber.tag(TAG).d("...trying cloud image label detection");
            device.getCloudImageDetection().detectImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, this);
        } else {
            Timber.tag(TAG).d("...not trying cloud image label detection, we're done");
            finishUp();
        }
    }

    ///response interface for Device image match detection
    public void deviceMatchImageLabelSuccess(ImageMatchResults imageMatchResults){
        Timber.tag(TAG).d("deviceMatchImageLabelSuccess");

        this.photoRequest.getImageAnalysis().setDeviceImageMatchResults(imageMatchResults);
        /// we are doing matching, let's see if we got a success
        /// if we were successful, we're done.  if not, we need to check to see if we need to do cloud image matching

        if (imageMatchResults.getFoundMatchingLabels()){
            Timber.tag(TAG).d("we found matching labels, we're done");
            finishUp();
        } else {
            Timber.tag(TAG).d("we didn't find matching labels");
            if (photoRequest.getImageAnalysis().getDoCloudImageLabelMatching()){
                Timber.tag(TAG).d("...trying cloud image label matching");
                device.getCloudImageDetection().matchImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, photoRequest.getImageAnalysis().getImageMatchSettings(),this);
            } else {
                Timber.tag(TAG).d("...not truing cloud image label matching, we're done");
                finishUp();
            }
        }
    }

    public void deviceMatchImageLabelFailure(){
        Timber.tag(TAG).d("deviceMatchImageLabelFailure");

        this.photoRequest.getImageAnalysis().setDeviceImageMatchResults(null);

        ///we had a failure on device match, let's see if we need to try cloud matching
        if (photoRequest.getImageAnalysis().getDoCloudImageLabelMatching()){
            Timber.tag(TAG).d("...trying cloud image label matching");
            device.getCloudImageDetection().matchImageLabelRequest(device.getDeviceImageStorage(), imageDeviceAbsoluteFilename, photoRequest.getImageAnalysis().getImageMatchSettings(),this);
        } else {
            //we're done
            Timber.tag(TAG).d("...not trying cloud image label matching, we're done");
            finishUp();
        }
    }

    /// response interface for Cloud Image Detection
    public void cloudDetectImageLabelSuccess(ImageDetectionResults imageDetectionResults){
        Timber.tag(TAG).d("cloudDetectImageLabelSuccess");
        this.photoRequest.getImageAnalysis().setCloudImageDetectionResults(imageDetectionResults);
        finishUp();
    }

    public void cloudDetectImageLabelFailure(){
        Timber.tag(TAG).d("cloudDetectImageLabelFailure");
        this.photoRequest.getImageAnalysis().setCloudImageDetectionResults(null);
        finishUp();
    }

    //// response interface for Cloud Image Match Detection
    public void cloudMatchImageLabelSuccess(ImageMatchResults imageMatchResults){
        Timber.tag(TAG).d("cloudMatchImageLabelSuccess");
        this.photoRequest.getImageAnalysis().setCloudImageMatchResults(imageMatchResults);
        finishUp();
    }

    public void cloudMatchImageLabelFailure(){
        Timber.tag(TAG).d("cloudMatchImageLabelFailure");
        this.photoRequest.getImageAnalysis().setCloudImageMatchResults(null);
        finishUp();
    }

    private void finishUp(){
        Timber.tag(TAG).d("finishUp");
        //we're done with all our branching logic for device & cloud image detection & matching, now check to see if we need to do text detection
        //TODO do text detection
        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), photoRequest, imageDeviceAbsoluteFilename, true, this);
    }

    /// response interface for updating photoRequest with file name for device image, and with results of label detection
    public void cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete");
        response.useCaseImageAnalysisComplete(photoRequest);
        close();
    }



    public interface Response {
        void useCaseImageAnalysisComplete(PhotoRequest photoRequest);
    }
}
