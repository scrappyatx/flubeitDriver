/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.receiptOcr;

import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import timber.log.Timber;

/**
 * Created on 3/3/2019
 * Project : Driver
 */
public class ReceiptTotalCharged {
    public static final String TAG = "ReceiptTotalCharged";

    public static void findTotalCharged(ReceiptOcrSettings settings, ReceiptPatternMatch matcher, ReceiptOcrResults results) {
        Timber.tag(TAG).d("findTotalCharged");

        if (settings.getHasTransactionId()) {
            Timber.tag(TAG).d("searching for total charged");
            String totalCharged = matcher.matchPatternRequest(settings.getTotalChargedStartSentinal(), settings.getTotalChargedEndSentinal(), settings.getTotalChargedPattern());
            if (totalCharged != null) {
                Timber.tag(TAG).d("   total charged FOUND -> %s", totalCharged);
                results.setFoundTotalCharged(true);
                results.setTotalCharged(totalCharged);
            } else {
                Timber.tag(TAG).d("   total charged NOT FOUND");
                results.setFoundTotalCharged(false);
                results.setTotalCharged(null);
            }
        } else {
            Timber.tag(TAG).d("not searching for total charged");
            results.setFoundTotalCharged(false);
            results.setTotalCharged(null);
        }
    }
}
