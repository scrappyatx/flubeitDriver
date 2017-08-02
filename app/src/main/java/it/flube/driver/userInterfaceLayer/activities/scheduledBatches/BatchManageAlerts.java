/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class BatchManageAlerts {
    private static final String TAG = "BatchManageAlerts";
    private static final int displayDuration = 2000;

    public void showForfeitBatchAlert(AppCompatActivity activity) {
        Timber.tag(TAG).d("showing Forfeit Batch Alert");
        try {
            Alerter.create(activity)
                    .setTitle("FORFEIT BATCH")
                    .setText("You forfeited a batch.  Lazy sack of shit.")
                    .setBackgroundColor(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showStartedBatchAlert(AppCompatActivity activity) {
        Alerter.create(activity)
                .setTitle("BATCH STARTED")
                .setText("You started a batch.  Don't wreck the car, bitch")
                .setBackgroundColor(R.color.alerter_default_success_background)
                .setIcon(R.drawable.ic_mood_black_24dp)
                .setDuration(displayDuration)
                .show();
    }

    public static class ShowForfeitBatchAlertEvent {
        public ShowForfeitBatchAlertEvent() {

        }
    }

    public static class ShowStartedBatchAlertEvent {
        public ShowStartedBatchAlertEvent(){

        }
    }
}
