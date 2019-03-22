/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudTextDetection;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;

import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.libbatchdata.entities.TextDetectBlock;
import it.flube.libbatchdata.entities.TextDetectElement;
import it.flube.libbatchdata.entities.TextDetectLine;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

/**
 * Created on 8/10/2018
 * Project : Driver
 */
public class FirebaseCloudTextDetection implements
        OnSuccessListener<FirebaseVisionText>,
        OnFailureListener {
    private static final String TAG="FirebaseCloudTextDetection";

    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.8f;


    private CloudTextDetectionInterface.CloudDetectImageTextResponse response;

    public void detectTextRequest(Bitmap bitmap, CloudTextDetectionInterface.CloudDetectImageTextResponse response){
        Timber.tag(TAG).d("detectTextRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...creating vision image from bitmap");
        //create a vision image from the bitmap
        //FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        //recycle bitmap
        //bitmap.recycle();

        Timber.tag(TAG).d("   ...getting detector with desired options");
        FirebaseVision.getInstance().getCloudTextRecognizer().processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(this).addOnFailureListener(this);


        Timber.tag(TAG).d("   ...detecting the image");
        //detector.processImage(image).addOnSuccessListener(this).addOnFailureListener(this);
    }

    public void onSuccess(FirebaseVisionText firebaseVisionText){
        Timber.tag(TAG).d("...onSuccess");

        Timber.tag(TAG).d("   ...creating hashmap for results");


        //create TextDetectionResults object
        TextDetectionResults textDetectionResults = new TextDetectionResults();

        //save the results
        Timber.tag(TAG).d("   ...detected text -> %s", firebaseVisionText.getText());
        textDetectionResults.setText(firebaseVisionText.getText());

        textDetectionResults.setBlocks(new ArrayList<TextDetectBlock>());

        Timber.tag(TAG).d("   ...looping through results");


        for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
            //Timber.tag(TAG).d("   **** BLOCK START ****");
            //// https://firebase.google.com/docs/ml-kit/android/recognize-text
            TextDetectBlock resultBlock = new TextDetectBlock();

            //set the confidence
            if (block.getConfidence() != null){
                //Timber.tag(TAG).w("      block confidence -> %s", block.getConfidence());
                resultBlock.setConfidence(block.getConfidence());
            } else {
                //Timber.tag(TAG).w("      block confidence is null");
                resultBlock.setConfidence(0f);
            }

            resultBlock.setText(block.getText());
            Timber.tag(TAG).d("      ...block text -> " + block.getText());



            //now loop through all the lines in the block and add them
            resultBlock.setLines(new ArrayList<TextDetectLine>());

            for (FirebaseVisionText.Line line : block.getLines()){
                //Timber.tag(TAG).d("      **** LINE START ****");
                TextDetectLine resultLine = new TextDetectLine();

                //set the confidence
                if (line.getConfidence() != null){
                    //Timber.tag(TAG).w("      line confidence -> %s", line.getConfidence());
                    resultLine.setConfidence(line.getConfidence());
                } else {
                    //Timber.tag(TAG).w("      line confidence is null");
                    resultLine.setConfidence(0f);
                }

                resultLine.setText(line.getText());
                Timber.tag(TAG).d("         ...line text -> " + line.getText());

                resultLine.setElements(new ArrayList<TextDetectElement>());

                for (FirebaseVisionText.Element element: line.getElements()){
                    //Timber.tag(TAG).d("         **** ELEMENT START ****");
                    TextDetectElement resultElement = new TextDetectElement();

                    //set the confidence
                    if (element.getConfidence() != null){
                        //Timber.tag(TAG).w("      element confidence -> %s", element.getConfidence());
                        resultElement.setConfidence(element.getConfidence());
                    } else {
                        //Timber.tag(TAG).w("      element confidence is null");
                        resultElement.setConfidence(0f);
                    }

                    resultElement.setText(element.getText());
                    Timber.tag(TAG).d("            ...element text -> " + element.getText());

                    //add this element to the result line
                    resultLine.getElements().add(resultElement);
                    //Timber.tag(TAG).d("         **** ELEMENT END ****");
                }

                //add this line to the result block
                resultBlock.getLines().add(resultLine);
                //Timber.tag(TAG).d("      **** LINE END ****");
            }
            //add this block to the results
            textDetectionResults.getBlocks().add(resultBlock);
            //Timber.tag(TAG).d("   **** BLOCK END ****");

        }
        Timber.tag(TAG).d("   ...returning results");

        response.cloudDetectImageTextSuccess(textDetectionResults);
        response = null;
    }


    public void onFailure(@NonNull Exception e){
        Timber.tag(TAG).d("onFailure");
        Timber.tag(TAG).e(e);
        response.cloudDetectImageTextFailure();
        response = null;
    }
}
