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
public class ReceiptVehicleVin {
    public static final String TAG = "ReceiptVehicleVin";

    public static void findVehicleVin(ReceiptOcrSettings settings, ReceiptPatternMatch matcher, ReceiptOcrResults results) {
        Timber.tag(TAG).d("findVehicleVin");

        if (settings.getHasTransactionId()) {
            Timber.tag(TAG).d("searching for vehicle vin");
            ArrayList<String> matchList = matcher.matchPatternRequestIgnoreSentinals(settings.getVehicleVinPattern());

            //for vehicle vin just return the first result in the list
            if (matchList.size() > 0) {
                Timber.tag(TAG).d("   vehicle vin FOUND -> %s", matchList.get(0));
                results.setFoundVehicleVin(true);
                results.setVehicleVin(matchList.get(0));
            } else {
                Timber.tag(TAG).d("   vehicle vin NOT FOUND");
                results.setFoundVehicleVin(false);
                results.setVehicleVin(null);
            }
        } else {
            Timber.tag(TAG).d("not searching for vehicle vin");
            results.setFoundVehicleVin(false);
            results.setVehicleVin(null);
        }
    }
}
