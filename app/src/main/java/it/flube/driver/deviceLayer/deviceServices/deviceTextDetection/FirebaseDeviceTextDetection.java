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

        Timber.tag(TAG).d("   ...creating vision image from bitmap");
        //create a vision image from the bitmap
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        //recycle bitmap
        //bitmap.recycle();

        Timber.tag(TAG).d("   ...getting detector with desired options");
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        Timber.tag(TAG).d("   ...detecting the image");
        detector.processImage(image).addOnSuccessListener(this).addOnFailureListener(this);
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
            try {
                resultBlock.setConfidence(block.getConfidence());
            } catch (Exception e){
                Timber.tag(TAG).w("couldn't get block confidence");
                Timber.tag(TAG).e(e);
                resultBlock.setConfidence(0f);
            }

            resultBlock.setText(block.getText());

            Timber.tag(TAG).d("      ...text -> " + block.getText());



            //now loop through all the lines in the block and add them
            resultBlock.setLines(new ArrayList<TextDetectLine>());

            for (FirebaseVisionText.Line line : block.getLines()){
                Timber.tag(TAG).d("      **** LINE START ****");
                TextDetectLine resultLine = new TextDetectLine();

                try {
                    resultLine.setConfidence(line.getConfidence());
                } catch (Exception e){
                    Timber.tag(TAG).w("couldn't get line confidence");
                    Timber.tag(TAG).e(e);
                    resultLine.setConfidence(0f);
                }

                resultLine.setText(line.getText());

                resultLine.setElements(new ArrayList<TextDetectElement>());

                       for (FirebaseVisionText.Element element: line.getElements()){
                           Timber.tag(TAG).d("         **** ELEMENT START ****");
                           TextDetectElement resultElement = new TextDetectElement();

                           try {
                               resultElement.setConfidence(element.getConfidence());
                           } catch (Exception e){
                               resultElement.setConfidence(0f);
                               Timber.tag(TAG).w("couldn't get element confidence");
                               Timber.tag(TAG).e(e);
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
