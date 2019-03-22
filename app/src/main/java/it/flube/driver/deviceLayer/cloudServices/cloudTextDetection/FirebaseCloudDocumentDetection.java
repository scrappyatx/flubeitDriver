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
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.List;

import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.libbatchdata.entities.TextDetectBlock;
import it.flube.libbatchdata.entities.TextDetectElement;
import it.flube.libbatchdata.entities.TextDetectLine;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

import static com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions.DENSE_MODEL;

/**
 * Created on 3/21/2019
 * Project : Driver
 */
public class FirebaseCloudDocumentDetection implements
        OnSuccessListener<FirebaseVisionDocumentText>,
        OnFailureListener {

    private static final String TAG="FirebaseCloudDocumentDetection";

    private CloudTextDetectionInterface.CloudDetectImageTextResponse response;

    public void detectTextRequest(Bitmap bitmap, CloudTextDetectionInterface.CloudDetectImageTextResponse response){
        Timber.tag(TAG).d("detectTextRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...getting detector with desired options");
        FirebaseVision.getInstance().getCloudDocumentTextRecognizer().processImage(FirebaseVisionImage.fromBitmap(bitmap)).addOnSuccessListener(this).addOnFailureListener(this);


        Timber.tag(TAG).d("   ...detecting the image");
        //detector.processImage(image).addOnSuccessListener(this).addOnFailureListener(this);
    }

    public void onSuccess(FirebaseVisionDocumentText firebaseVisionDocumentText){
        Timber.tag(TAG).d("...onSuccess");

        Timber.tag(TAG).d("   ...creating hashmap for results");

        //create object to return results
        TextDetectionResults textDetectionResults = new TextDetectionResults();

        //save the results
        /// strip all non-ascii characters from the text
        Timber.tag(TAG).d("   ...detected text -> %s", firebaseVisionDocumentText.getText());



        textDetectionResults.setText(firebaseVisionDocumentText.getText());

        Timber.tag(TAG).d("   ...looping through results");
        textDetectionResults.setBlocks(loopThroughAllBlocks(firebaseVisionDocumentText));
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

    private List<TextDetectBlock> loopThroughAllBlocks(FirebaseVisionDocumentText firebaseVisionDocumentText){
        Timber.tag(TAG).d("loopThroughAllBlocks");
        //create TextDetectionResults object
        ArrayList<TextDetectBlock> blockList = new ArrayList<TextDetectBlock>();

        for (FirebaseVisionDocumentText.Block block: firebaseVisionDocumentText.getBlocks()) {
            //Timber.tag(TAG).d("   **** BLOCK START ****");
            //// https://firebase.google.com/docs/ml-kit/android/recognize-text
            TextDetectBlock resultBlock = new TextDetectBlock();

            //set the confidence
            if (block.getConfidence() != null) {
                //Timber.tag(TAG).w("      block confidence -> %s", block.getConfidence());
                resultBlock.setConfidence(block.getConfidence());
            } else {
                //Timber.tag(TAG).w("      block confidence is null");
                resultBlock.setConfidence(0f);
            }

            //set the text
            resultBlock.setText(block.getText());
            Timber.tag(TAG).d("      ...block text -> " + resultBlock.getText());
            Timber.tag(TAG).d("      ...block conf -> " + resultBlock.getConfidence());

            //set the line list by looping through all the paragraphs
            resultBlock.setLines(loopThroughAllParagraphs(block));

            //add the block to our result list
            blockList.add(resultBlock);
        }

        ///return the results
        return blockList;
    }

    private List<TextDetectLine> loopThroughAllParagraphs(FirebaseVisionDocumentText.Block block){
        //// we will map paragraphs to lines in our text detection results

        /// create a a new list of TextDetectLine to return result
        ArrayList<TextDetectLine> lineList = new ArrayList<TextDetectLine>();

        for (FirebaseVisionDocumentText.Paragraph paragraph : block.getParagraphs()) {
            //Timber.tag(TAG).d("      **** LINE START ****");
            TextDetectLine resultLine = new TextDetectLine();

            //set the confidence
            if (paragraph.getConfidence() != null) {
                //Timber.tag(TAG).w("      line confidence -> %s", line.getConfidence());
                resultLine.setConfidence(paragraph.getConfidence());
            } else {
                //Timber.tag(TAG).w("      line confidence is null");
                resultLine.setConfidence(0f);
            }

            //set the text
            resultLine.setText(paragraph.getText());
            Timber.tag(TAG).d("         ...paragraph text -> " + resultLine.getText());
            Timber.tag(TAG).d("         ...paragraph conf -> " + resultLine.getConfidence());

            ///get the element list by looping through all the words in the paragraph
            resultLine.setElements(loopThroughAllWords(paragraph));

            /// add to the line list
            lineList.add(resultLine);
        }

        // return the results
        return lineList;
    }


    private List<TextDetectElement> loopThroughAllWords(FirebaseVisionDocumentText.Paragraph paragraph){
        //we will map words to elements in our text detection results
        //Timber.tag(TAG).d("loopThroughAllWords");
        //create a new list of TextDetectElement to return result
        ArrayList<TextDetectElement> elementList = new ArrayList<TextDetectElement>();

        for (FirebaseVisionDocumentText.Word word: paragraph.getWords()){
            //Timber.tag(TAG).d("         **** ELEMENT START ****");
            TextDetectElement resultElement = new TextDetectElement();

            //set the confidence
            if (word.getConfidence() != null){
                //Timber.tag(TAG).w("      element confidence -> %s", element.getConfidence());
                resultElement.setConfidence(word.getConfidence());
            } else {
                //Timber.tag(TAG).w("      element confidence is null");
                resultElement.setConfidence(0f);
            }

            //set the text
            resultElement.setText(word.getText());
            //Timber.tag(TAG).d("            ...word text -> " + word.getText());

            //add this element to the result list
            elementList.add(resultElement);
            //Timber.tag(TAG).d("         **** ELEMENT END ****");
        }
        //return the element list
        return elementList;
    }


}
