/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.receiptOcr;

import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class ReceiptOcrAnalysis {
    public static final String TAG = "ReceiptOcrAnalysis";

    public void analyzeReceiptRequest(TextDetectionResults textDetectionResults, ReceiptOcrSettings receiptOcrSettings, Response response){
        Timber.tag(TAG).d("analyzeReceiptRequest");

        //create pattern matcher
        ReceiptPatternMatch receiptPatternMatch = new ReceiptPatternMatch(textDetectionResults.getText());

        //create results object
        ReceiptOcrResults receiptOcrResults = new ReceiptOcrResults();
        receiptOcrResults.setOcrText(textDetectionResults);

        Timber.tag(TAG).d("   checking transaction id...");
        ReceiptTransactionId.findTransactionId(receiptOcrSettings, receiptPatternMatch, receiptOcrResults);

        Timber.tag(TAG).d("   checking vehicle info...");
        ReceiptVehicleId.findVehicleId(receiptOcrSettings, receiptPatternMatch, receiptOcrResults);
        ReceiptVehicleVin.findVehicleVin(receiptOcrSettings, receiptPatternMatch, receiptOcrResults);
        ReceiptVehicleMileage.findVehicleMileage(receiptOcrSettings, receiptPatternMatch, receiptOcrResults);

        Timber.tag(TAG).d("   checking total amount...");
        ReceiptTotalCharged.findTotalCharged(receiptOcrSettings, receiptPatternMatch, receiptOcrResults);

        //post processing, see if we found everything we expected to find
        Timber.tag(TAG).d("   checking if we found everything we epxected to find...");
        ReceiptOcrPostProcessing.checkReceiptOcrResults(receiptOcrSettings, receiptOcrResults);

        response.analyzeReceiptComplete(receiptOcrResults);
    }

    public interface Response {
        void analyzeReceiptComplete(ReceiptOcrResults receiptOcrResults);
    }
}
