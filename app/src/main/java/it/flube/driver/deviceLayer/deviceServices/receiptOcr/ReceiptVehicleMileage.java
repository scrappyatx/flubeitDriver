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
public class ReceiptVehicleMileage {
    public static final String TAG = "ReceiptVehicleMileage";

    public static void findVehicleMileage(ReceiptOcrSettings settings, ReceiptPatternMatch matcher, ReceiptOcrResults results) {
        Timber.tag(TAG).d("findVehicleMileage");

        if (settings.getHasTransactionId()) {
            Timber.tag(TAG).d("searching for vehicle vin");
            String vehicleMileage = matcher.matchPatternRequest(settings.getVehicleMileageStartSentinal(), settings.getVehicleMileageEndSentinal(), settings.getVehicleMileagePattern());
            if (vehicleMileage != null) {
                Timber.tag(TAG).d("   vehicle mileage FOUND -> %s", vehicleMileage);
                results.setFoundVehicleMileage(true);
                results.setVehicleMileage(vehicleMileage);
            } else {
                Timber.tag(TAG).d("   vehicle mileage NOT FOUND");
                results.setFoundVehicleMileage(false);
                results.setVehicleMileage(null);
            }
        } else {
            Timber.tag(TAG).d("not searching for vehicle mileage");
            results.setFoundVehicleMileage(false);
            results.setVehicleMileage(null);
        }
    }
}
