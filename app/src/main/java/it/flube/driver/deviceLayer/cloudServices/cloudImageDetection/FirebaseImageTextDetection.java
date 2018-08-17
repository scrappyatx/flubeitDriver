/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.libbatchdata.builders.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 8/10/2018
 * Project : Driver
 */
public class FirebaseImageTextDetection implements
        OnSuccessListener<FirebaseVisionText>,
        OnFailureListener {
    private static final String TAG="FirebaseImageTextDetection";

    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.8f;


    private CloudImageDetectionInterface.DetectImageTextResponse response;

    public void detectImageRequest(Bitmap bitmap, CloudImageDetectionInterface.DetectImageTextResponse response){
        Timber.tag(TAG).d("detectImageRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...creating vision image from bitmap");
        //create a vision image from the bitmap
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        Timber.tag(TAG).d("   ...getting detector with desired options");
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance()
                .getVisionTextDetector();

        Timber.tag(TAG).d("   ...detecting the image");
        detector.detectInImage(image).addOnSuccessListener(this).addOnFailureListener(this);
    }

    public void onSuccess(FirebaseVisionText firebaseVisionText){
        Timber.tag(TAG).d("...onSuccess");

        Timber.tag(TAG).d("   ...creating hashmap for results");
        //create hashmap for result
        HashMap<String, String> resultMap = new HashMap<String, String>();

        Timber.tag(TAG).d("   ...looping through results");
        for (FirebaseVisionText.Block block: firebaseVisionText.getBlocks()) {

            Rect boundingBox = block.getBoundingBox();
            Point[] cornerPoints = block.getCornerPoints();
            String text = block.getText();

            ///for (FirebaseVisionText.Line line : block.getLines()){
            ///
            ///    for (FirebaseVisionText.Element element: line.getElements()){
            ///
            ///
            ///    }
            /// }

            Timber.tag(TAG).d("   ****************");
            Timber.tag(TAG).d("      ...text -> " + block.getText());
            Timber.tag(TAG).d("   ****************");

            resultMap.put(BuilderUtilities.generateGuid(), block.getText());
        }
        Timber.tag(TAG).d("   ...returning results");
        response.detectImageTextSuccess(resultMap);
    }

    public void onFailure(@NonNull Exception e){
        Timber.tag(TAG).d("onFailure");
        Timber.tag(TAG).e(e);
        response.detectImageTextFailure();
    }
}
