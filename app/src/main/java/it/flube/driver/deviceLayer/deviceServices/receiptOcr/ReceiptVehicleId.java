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
public class ReceiptVehicleId {
    public static final String TAG = "ReceiptVehicleId";

    public static void findVehicleId(ReceiptOcrSettings settings, ReceiptPatternMatch matcher, ReceiptOcrResults results) {
        Timber.tag(TAG).d("findVehicleId");

        if (settings.getHasTransactionId()) {
            Timber.tag(TAG).d("searching for vehicle vin");
            String vehicleId = matcher.matchPatternRequest(settings.getVehicleIdStartSentinal(), settings.getVehicleIdEndSentinal(), settings.getVehicleIdPattern());
            if (vehicleId != null) {
                Timber.tag(TAG).d("   vehicle id FOUND -> %s", vehicleId);
                results.setFoundVehicleId(true);
                results.setVehicleId(vehicleId);
            } else {
                Timber.tag(TAG).d("   vehicle id NOT FOUND");
                results.setFoundVehicleId(false);
                results.setVehicleId(null);
            }
        } else {
            Timber.tag(TAG).d("not searching for vehicle id");
            results.setFoundVehicleId(false);
            results.setVehicleId(null);
        }
    }
}
