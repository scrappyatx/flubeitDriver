/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceImageDetection;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.nio.charset.StandardCharsets;
import java.util.List;

import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageDetectionInterface;
import it.flube.libbatchdata.builders.ImageDetectionResultsBuilder;
import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageLabel;
import timber.log.Timber;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class FirebaseDeviceImageLabelDetection implements
        OnSuccessListener<List<FirebaseVisionImageLabel>>,
        OnFailureListener {
    private static final String TAG="FirebaseDeviceImageLabelDetection";

    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.5f;

    private DeviceImageDetectionInterface.DeviceDetectImageLabelResponse response;

    public void detectImageRequest(Bitmap bitmap, DeviceImageDetectionInterface.DeviceDetectImageLabelResponse response){
        Timber.tag(TAG).d("detectImageRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...creating vision image from bitmap");
        //create a vision image from the bitmap
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        //now recycle the bitmap
        ///bitmap.recycle();
        ///Timber.tag(TAG).d("   ...recycling the bitmap");

        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();

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
                String base64 = "";
                try {
                    base64 = Base64.encodeToString(label.getEntityId().getBytes(), Base64.DEFAULT);
                } catch (Exception e){
                    Timber.tag(TAG).e(e);
                    Timber.tag(TAG).w("error trying to base64 encode an entityId -> %s", label.getEntityId());
                }

                Timber.tag(TAG).d("***** Image Label START ****");
                Timber.tag(TAG).d("  label -> %s", label.getText());
                Timber.tag(TAG).d("  entityId -> %s", label.getEntityId());
                Timber.tag(TAG).d("  entityId (base64) -> %s", base64);
                Timber.tag(TAG).d("  confidence -> %s", Float.toString(label.getConfidence()));

                //create an image label, and add to the imageLabel array
                ImageLabel imageLabel = new ImageLabel();
                imageLabel.setLabel(label.getText());
                imageLabel.setEntityId(base64);
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
                Timber.tag(TAG).d("***** Image Label END ****");
            }
            response.deviceDetectImageLabelSuccess(results);
        } else {
            Timber.tag(TAG).d("found 0 labels");
            response.deviceDetectImageLabelFailure();
        }
    }

    public void onFailure(@NonNull Exception e){
        Timber.tag(TAG).d("onFailure");
        Timber.tag(TAG).e(e);
        response.deviceDetectImageLabelFailure();
    }

}

