/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class BatchManageAlerts {
    private static final String TAG = "BatchManageAlerts";
    private static final int displayDuration = 2000;

    public void showForfeitBatchAlert(AppCompatActivity activity, ForfeitBatchAlertHidden response) {
        Timber.tag(TAG).d("showing Forfeit Batch Alert");
        try {
            Alerter.create(activity)
                    .setTitle("FORFEIT BATCH")
                    .setText("You forfeited a batch.  Lazy sack of shit.")
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ForfeitBatchHideAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showStartedBatchAlert(AppCompatActivity activity, StartedBatchAlertHidden response) {
        Alerter.create(activity)
                .setTitle("BATCH STARTED")
                .setText("You started a batch.  Don't wreck the car, bitch")
                .setBackgroundColorRes(R.color.alerter_default_success_background)
                .setIcon(R.drawable.ic_mood_black_24dp)
                .setDuration(displayDuration)
                .setOnHideListener(new StartedBatchAlertHiddenListener(response))
                .show();
    }

    public class ForfeitBatchHideAlertListener implements OnHideAlertListener {
        private ForfeitBatchAlertHidden response;
        public ForfeitBatchHideAlertListener(ForfeitBatchAlertHidden response){
            this.response = response;
        }
        public void onHide(){
            response.forfeitBatchAlertHidden();
        }
    }

    public class StartedBatchAlertHiddenListener implements OnHideAlertListener {
        private StartedBatchAlertHidden response;
        public StartedBatchAlertHiddenListener(StartedBatchAlertHidden response){
            this.response = response;
        }

        public void onHide(){
            response.startedBatchAlertHidden();
        }
    }

    public interface ForfeitBatchAlertHidden {
        void forfeitBatchAlertHidden();
    }

    public interface StartedBatchAlertHidden {
        void startedBatchAlertHidden();
    }
}
