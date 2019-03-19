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
public class ReceiptTransactionId {
    public static final String TAG = "ReceiptTransactionId";

    public static void findTransactionId(ReceiptOcrSettings settings, ReceiptPatternMatch matcher, ReceiptOcrResults results) {
        Timber.tag(TAG).d("findTransactionId");

        if (settings.getHasTransactionId()) {
            Timber.tag(TAG).d("searching for transaction id");
            String transactionId = matcher.matchPatternRequest(settings.getTransactionIdStartSentinal(), settings.getTransactionIdEndSentinal(), settings.getTransactionIdPattern());
            if (transactionId != null) {
                Timber.tag(TAG).d("   transactionId FOUND -> %s", transactionId);
                results.setFoundTransactionId(true);
                results.setTransactionId(transactionId);
            } else {
                Timber.tag(TAG).d("   transactionId NOT FOUND");
                results.setFoundTransactionId(false);
                results.setTransactionId(null);
            }
        } else {
            Timber.tag(TAG).d("not searching for transaction id");
            results.setFoundTransactionId(false);
            results.setTransactionId(null);
        }
    }
}
