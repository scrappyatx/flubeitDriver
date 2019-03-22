/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.receiptOcr;

import java.util.ArrayList;

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
            ArrayList<String> matchList = matcher.matchPatternRequestIgnoreSentinals(settings.getTotalChargedPattern());

            /// for total charged, we will pick the largest value out of the list
            float maxValue = 0.0f;
            String maxString = null;

            for (String candidate : matchList){
                Timber.tag(TAG).d("   candidate      -> " + candidate);
                float candidateValue = Float.valueOf(candidate.replaceAll("[$,]",""));
                Timber.tag(TAG).d("   candidate value -> " + candidateValue);
                if (candidateValue > maxValue){
                    maxString = candidate;
                    Timber.tag(TAG).d("   setting maxString -> " + candidate);
                }
            }
            Timber.tag(TAG).d("returning maxString -> " + maxString);

            if (maxString != null) {
                Timber.tag(TAG).d("   total charged FOUND -> %s", maxString);
                results.setFoundTotalCharged(true);
                results.setTotalCharged(maxString);
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
