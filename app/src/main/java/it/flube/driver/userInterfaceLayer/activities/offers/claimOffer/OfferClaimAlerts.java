/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageAlerts;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class OfferClaimAlerts {
    private static final String TAG = "OfferClaimAlerts";
    private static final int displayDuration = 2000;

    public void showSuccessAlert(AppCompatActivity activity, ClaimOfferSuccessAlertHidden response) {
        Timber.tag(TAG).d("showing Success Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.offer_claim_alert_success_title)
                    .setText(R.string.offer_claim_alert_success_message)
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ClaimOfferSuccessAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showFailureAlert(AppCompatActivity activity, ClaimOfferFailureAlertHidden response) {
        Timber.tag(TAG).d("showing Failure Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.offer_claim_alert_failure_title)
                    .setText(R.string.offer_claim_alert_failure_message)
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ClaimOfferFailureAlertListener(response))
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }

    }

    public void showTimeoutAlert(AppCompatActivity activity, ClaimOfferTimeoutAlertHidden response) {
        Timber.tag(TAG).d("showing Timeout Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.offer_claim_alert_timeout_title)
                    .setText(R.string.offer_claim_alert_timeout_message)
                    .setBackgroundColorRes(R.color.accent)
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .setDuration(displayDuration)
                    .setOnHideListener(new ClaimOfferTimeoutAlertListener(response))
                    .show();
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public class ClaimOfferSuccessAlertListener implements OnHideAlertListener {
        private ClaimOfferSuccessAlertHidden response;
        public ClaimOfferSuccessAlertListener(ClaimOfferSuccessAlertHidden response){
            this.response = response;
        }

        public void onHide(){
            response.claimOfferSuccessAlertHidden();
        }
    }

    public interface ClaimOfferSuccessAlertHidden {
        void claimOfferSuccessAlertHidden();
    }

    public class ClaimOfferFailureAlertListener implements OnHideAlertListener {
        private ClaimOfferFailureAlertHidden response;
        public ClaimOfferFailureAlertListener(ClaimOfferFailureAlertHidden response){
            this.response = response;
        }

        public void onHide(){
            response.claimOfferFailureAlertHidden();
        }
    }

    public interface ClaimOfferFailureAlertHidden {
        void claimOfferFailureAlertHidden();
    }

    public class ClaimOfferTimeoutAlertListener implements OnHideAlertListener {
        private ClaimOfferTimeoutAlertHidden response;
        public ClaimOfferTimeoutAlertListener(ClaimOfferTimeoutAlertHidden response){
            this.response = response;
        }

        public void onHide(){
            response.claimOfferTimeoutAlertHidden();
        }
    }

    public interface ClaimOfferTimeoutAlertHidden {
        void claimOfferTimeoutAlertHidden();
    }



}
