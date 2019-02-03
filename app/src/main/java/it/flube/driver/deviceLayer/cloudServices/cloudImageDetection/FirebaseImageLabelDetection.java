/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;

import java.util.HashMap;
import java.util.List;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.ImageLabel;
import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import timber.log.Timber;

/**
 * Created on 8/9/2018
 * Project : Driver
 */
public class FirebaseImageLabelDetection implements
        OnSuccessListener<List<FirebaseVisionLabel>>,
        OnFailureListener {
    private static final String TAG="FirebaseImageLabelDetection";

    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.5f;


    private CloudImageDetectionInterface.DetectImageLabelResponse response;

    public void detectImageRequest(Bitmap bitmap, CloudImageDetectionInterface.DetectImageLabelResponse response){
        Timber.tag(TAG).d("detectImageRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...creating vision image from bitmap");
        //create a vision image from the bitmap
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        //now recycle the bitmap
        bitmap.recycle();
        Timber.tag(TAG).d("   ...recycling the bitmap");

        Timber.tag(TAG).d("   ...creating detector options");
        //create the detector with desired options
        FirebaseVisionLabelDetectorOptions options =
                new FirebaseVisionLabelDetectorOptions.Builder()
                        .setConfidenceThreshold(DEFAULT_CONFIDENCE_THRESHOLD)
                        .build();

        Timber.tag(TAG).d("   ...getting detector with desired options");
        FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
                .getVisionLabelDetector(options);

        Timber.tag(TAG).d("   ...detecting the image");
        detector.detectInImage(image).addOnSuccessListener(this).addOnFailureListener(this);
    }

    public void onSuccess(List<FirebaseVisionLabel> labels){
        Timber.tag(TAG).d("...onSuccess");

        Timber.tag(TAG).d("   ...creating hashmap for results");
        //create hashmap for result
        HashMap<String, ImageLabel> resultMap = new HashMap<String, ImageLabel>();

        if (labels.size() > 0){

            Timber.tag(TAG).d("   ...looping through results");
            for (FirebaseVisionLabel label: labels) {
                ImageLabel imageLabel = new ImageLabel();

                imageLabel.setLabel(label.getLabel());
                imageLabel.setEntityId(label.getEntityId());
                imageLabel.setConfidence(label.getConfidence());

                Timber.tag(TAG).d("   ****************");
                Timber.tag(TAG).d("      ...entityId -> " + imageLabel.getEntityId());
                Timber.tag(TAG).d("      ...label    -> " + imageLabel.getLabel());
                Timber.tag(TAG).d("      ...confidence -> " + imageLabel.getConfidence());
                Timber.tag(TAG).d("   ****************");

                resultMap.put(BuilderUtilities.generateGuid(), imageLabel);
            }
            Timber.tag(TAG).d("   ...returning results");
            response.detectImageLabelSuccess(resultMap);
        } else {
            //no results
            Timber.tag(TAG).d("   ...result set is empty");
            response.detectImageLabelFailure();
        }
    }

    public void onFailure(@NonNull Exception e){
        Timber.tag(TAG).d("onFailure");
        Timber.tag(TAG).e(e);
        response.detectImageLabelFailure();
    }

}
