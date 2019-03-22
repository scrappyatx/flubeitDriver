/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.util.List;


import it.flube.libbatchdata.builders.ImageDetectionResultsBuilder;
import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageLabel;
import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import timber.log.Timber;

/**
 * Created on 8/9/2018
 * Project : Driver
 */
public class FirebaseCloudImageLabelDetection implements
        OnSuccessListener<List<FirebaseVisionImageLabel>>,
        OnFailureListener {
    private static final String TAG="FirebaseCloudImageLabelDetection";

    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.5f;

    private CloudImageDetectionInterface.CloudDetectImageLabelResponse response;

    public void detectImageRequest(Bitmap bitmap, CloudImageDetectionInterface.CloudDetectImageLabelResponse response){
        Timber.tag(TAG).d("detectImageRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...creating vision image from bitmap");
        //create a vision image from the bitmap
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        //now recycle the bitmap
        //bitmap.recycle();
        //Timber.tag(TAG).d("   ...recycling the bitmap");

        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getCloudImageLabeler();

        Timber.tag(TAG).d("   ...labeling the image");
        labeler.processImage(image).addOnSuccessListener(this).addOnFailureListener(this);
    }

    public void onSuccess(List<FirebaseVisionImageLabel> labels){
        Timber.tag(TAG).d("...onSuccess");

        if (labels.size() > 0) {
            Timber.tag(TAG).d("found %s labels", labels.size());

            //create object to return results
            ImageDetectionResults results = new ImageDetectionResultsBuilder.Builder().build();
            /// build the results object from the FirebaseVisionImageLabel object
            float highestConfidenceFound = 0.0f;

            for (FirebaseVisionImageLabel label : labels) {
                //put this into an ImageLabel object

                Timber.tag(TAG).d("***** Image Label START ****");

                if (label.getEntityId() != null){

                    Timber.tag(TAG).d("  label -> %s", label.getText());
                    Timber.tag(TAG).d("  entityId -> %s", label.getEntityId());
                    Timber.tag(TAG).d("  entityId (base64) -> %s", Base64.encodeToString(label.getEntityId().getBytes(), Base64.DEFAULT));
                    Timber.tag(TAG).d("  confidence -> %s", Float.toString(label.getConfidence()));

                    //create an image label, and add to the imageLabel array
                    ImageLabel imageLabel = new ImageLabel();
                    imageLabel.setLabel(label.getText());
                    imageLabel.setEntityId(Base64.encodeToString(label.getEntityId().getBytes(), Base64.DEFAULT));
                    imageLabel.setConfidence(label.getConfidence());

                    results.getLabelMap().add(imageLabel);

                    //store this in the mostLikelyImageLabel object if it has a higher confidence than the one there
                    if (imageLabel.getConfidence() > highestConfidenceFound) {
                        Timber.tag(TAG).d("  this is the highest confidence label we've found so far");
                        highestConfidenceFound = imageLabel.getConfidence();
                        results.setMostLikelyLabel(imageLabel);
                    } else {
                        Timber.tag(TAG).d("  this isn't the highest confidence label we've found so far");
                    }

                } else {
                    Timber.tag(TAG).w("  label.getEntityId() is null, nothing to base64 encode");
                }

                Timber.tag(TAG).d("***** Image Label END ****");
            }
            response.cloudDetectImageLabelSuccess(results);
            close();
        } else {
            Timber.tag(TAG).d("found 0 labels");
            response.cloudDetectImageLabelFailure();
            close();
        }
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
    }
    public void onFailure(@NonNull Exception e){
        Timber.tag(TAG).d("onFailure");
        Timber.tag(TAG).e(e);
        response.cloudDetectImageLabelFailure();
        close();
    }

}
