/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 10/29/2017
 * Project : Driver
 */

public class ActiveBatchAlerts {
    private static final String TAG = "ActiveBatchAlerts";
    private static final int displayDuration = 2000;

    public void showServiceOrderCompletedAlert(AppCompatActivity activity, ServiceOrderCompletedAlertHidden response) {
        Timber.tag(TAG).d("showing Service Order completed Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.active_batch_completed_service_order_alert_title)
                    .setText(R.string.active_batch_completed_service_order_alert_message)
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ServiceOrderCompletedAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showBatchCompletedAlert(AppCompatActivity activity, BatchCompletedAlertHidden response) {
        Timber.tag(TAG).d("showing Batch Completed Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.active_batch_completed_batch_alert_title)
                    .setText(R.string.active_batch_completed_batch_alert_message)
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new BatchCompletedAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }



    public class ServiceOrderCompletedAlertListener implements OnHideAlertListener {
        private ServiceOrderCompletedAlertHidden response;
        public ServiceOrderCompletedAlertListener(ServiceOrderCompletedAlertHidden response){
            this.response = response;
        }

        public void onHide(){
            response.serviceOrderCompletedAlertHidden();
        }
    }

    public interface ServiceOrderCompletedAlertHidden {
        void serviceOrderCompletedAlertHidden();
    }

    public class BatchCompletedAlertListener implements OnHideAlertListener {
        private BatchCompletedAlertHidden response;
        public BatchCompletedAlertListener(BatchCompletedAlertHidden response){
            this.response = response;
        }

        public void onHide(){
            response.batchCompletedAlertHidden();
        }
    }

    public interface BatchCompletedAlertHidden {
        void batchCompletedAlertHidden();
    }
}
