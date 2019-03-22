/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.receiptOcr;

import java.util.ArrayList;

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
            ArrayList<String> matchList = matcher.matchPatternRequestIgnoreSentinals(settings.getTransactionIdPattern());

            ///for transaction id, we just use the first match in the list
            if (matchList.size() > 0) {
                Timber.tag(TAG).d("   transactionId FOUND -> %s", matchList.get(0));
                results.setFoundTransactionId(true);
                results.setTransactionId(matchList.get(0));
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
