/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceTextDetection;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;

import it.flube.driver.modelLayer.interfaces.DeviceTextDetectionInterface;
import it.flube.libbatchdata.entities.TextDetectBlock;
import it.flube.libbatchdata.entities.TextDetectElement;
import it.flube.libbatchdata.entities.TextDetectLine;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class FirebaseDeviceTextDetection implements
        OnSuccessListener<FirebaseVisionText>,
        OnFailureListener {
    private static final String TAG="FirebaseDeviceTextDetection";

    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.8f;


    private DeviceTextDetectionInterface.DeviceDetectImageTextResponse response;

    public void detectTextRequest(Bitmap bitmap, DeviceTextDetectionInterface.DeviceDetectImageTextResponse response){
        Timber.tag(TAG).d("detectImageRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...getting detector with desired options");
        FirebaseVision.getInstance().getOnDeviceTextRecognizer().processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(this).addOnFailureListener(this);

        Timber.tag(TAG).d("   ...detecting the image");
        //detector.processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(this).addOnFailureListener(this);
    }

    public void onSuccess(FirebaseVisionText firebaseVisionText){
        Timber.tag(TAG).d("...onSuccess");

        Timber.tag(TAG).d("   ...creating hashmap for results");


        //create TextDetectionResults object
        TextDetectionResults textDetectionResults = new TextDetectionResults();
        textDetectionResults.setText(firebaseVisionText.getText());
        textDetectionResults.setBlocks(new ArrayList<TextDetectBlock>());

        Timber.tag(TAG).d("   ...looping through results");
        for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
            Timber.tag(TAG).d("   **** BLOCK START ****");
            //// https://firebase.google.com/docs/ml-kit/android/recognize-text
            TextDetectBlock resultBlock = new TextDetectBlock();

            //set the confidence
            if (block.getConfidence() != null){
                Timber.tag(TAG).d("      confidence -> %s", block.getConfidence().toString());
                resultBlock.setConfidence(block.getConfidence());
            } else {
                Timber.tag(TAG).d("      confidence is null");
                resultBlock.setConfidence(0f);
            }

            resultBlock.setText(block.getText());

            Timber.tag(TAG).d("      ...text -> " + block.getText());



            //now loop through all the lines in the block and add them
            resultBlock.setLines(new ArrayList<TextDetectLine>());

            for (FirebaseVisionText.Line line : block.getLines()){
                Timber.tag(TAG).d("      **** LINE START ****");
                TextDetectLine resultLine = new TextDetectLine();

                if (line.getConfidence() != null){
                    Timber.tag(TAG).d("        confidence -> %s", line.getConfidence().toString());
                    resultLine.setConfidence(line.getConfidence());
                } else {
                    Timber.tag(TAG).d("        confidence is null");
                    resultLine.setConfidence(0f);
                }

                resultLine.setText(line.getText());

                resultLine.setElements(new ArrayList<TextDetectElement>());

                       for (FirebaseVisionText.Element element: line.getElements()){
                           Timber.tag(TAG).d("         **** ELEMENT START ****");
                           TextDetectElement resultElement = new TextDetectElement();

                           if (element.getConfidence() != null){
                               Timber.tag(TAG).d("            confidence -> %s", element.getConfidence().toString());
                               resultElement.setConfidence(element.getConfidence());

                           } else {
                               Timber.tag(TAG).d("            confidence is null");
                               resultElement.setConfidence(0f);
                           }

                           resultElement.setText(element.getText());

                           //add this element to the result line
                           resultLine.getElements().add(resultElement);
                           Timber.tag(TAG).d("         **** ELEMENT END ****");
                        }

                //add this line to the result block
                resultBlock.getLines().add(resultLine);
                Timber.tag(TAG).d("      **** LINE END ****");
             }
             //add this block to the results
            textDetectionResults.getBlocks().add(resultBlock);
            Timber.tag(TAG).d("   **** BLOCK END ****");

        }
        Timber.tag(TAG).d("   ...returning results");

        response.deviceDetectImageTextSuccess(textDetectionResults);
        response = null;
    }

    public void onFailure(@NonNull Exception e){
        Timber.tag(TAG).d("onFailure");
        Timber.tag(TAG).e(e);
        response.deviceDetectImageTextFailure();
        response = null;
    }
}
