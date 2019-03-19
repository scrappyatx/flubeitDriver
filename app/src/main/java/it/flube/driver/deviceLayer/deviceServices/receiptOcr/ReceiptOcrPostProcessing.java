/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.receiptOcr;

import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class ReceiptOcrPostProcessing {
    private static final String TAG = "ReceiptOcrPostProcessing";

    public static void checkReceiptOcrResults(ReceiptOcrSettings settings, ReceiptOcrResults results){
        Timber.tag(TAG).d("checkReceiptOcrResults");
        //check all the fields we expect to find, and see if we found it

        //assume we have everything we need - one thing missing will cause us to fail
        results.setAllExpectedFieldsFound(true);

        //transaction id
        if (settings.getHasTransactionId() && !results.getFoundTransactionId()){
            Timber.tag(TAG).d("expected transaction id, did not find it");
            results.setAllExpectedFieldsFound(false);
        }

        //vehicle id
        if (settings.getHasVehicleId() && !results.getFoundVehicleId()){
            Timber.tag(TAG).d("expected vehicle id, did not find it");
            results.setAllExpectedFieldsFound(false);
        }

        //vehicle mileage
        if (settings.getHasVehicleMileage() && !results.getFoundVehicleMileage()){
            Timber.tag(TAG).d("expected vehicle mileage, did not find it");
            results.setAllExpectedFieldsFound(false);
        }

        //vehicle vin
        if (settings.getHasVehicleVin() && !results.getFoundVehicleVin()){
            Timber.tag(TAG).d("expected vehicle vin, did not find it");
            results.setAllExpectedFieldsFound(false);
        }

        //total charged
        if (settings.getHasTotalCharged() && !results.getFoundTotalCharged()){
            Timber.tag(TAG).d("expected total charged, did not find it");
            results.setAllExpectedFieldsFound(false);
        }

    }

}
