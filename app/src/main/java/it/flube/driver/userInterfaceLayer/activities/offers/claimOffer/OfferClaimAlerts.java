/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import android.support.v7.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class OfferClaimAlerts {
    private static final String TAG = "OfferClaimAlerts";
    private static final int displayDuration = 2000;

    public void showSuccessAlert(AppCompatActivity activity) {
        Timber.tag(TAG).d("showing Success Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.offer_claim_alert_success_title)
                    .setText(R.string.offer_claim_alert_success_message)
                    .setBackgroundColor(R.color.alerter_default_success_background)
                    .setIcon(R.drawable.ic_mood_black_24dp)
                    .setDuration(displayDuration)
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void showFailureAlert(AppCompatActivity activity) {
        Timber.tag(TAG).d("showing Failure Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.offer_claim_alert_failure_title)
                    .setText(R.string.offer_claim_alert_failure_message)
                    .setBackgroundColor(R.color.alert_default_error_background)
                    .setIcon(R.drawable.ic_mood_bad_black_24dp)
                    .setDuration(displayDuration)
                    .show();

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }

    }

    public void showTimeoutAlert(AppCompatActivity activity) {
        Timber.tag(TAG).d("showing Timeout Alert");
        try {
            Alerter.create(activity)
                    .setTitle(R.string.offer_claim_alert_timeout_title)
                    .setText(R.string.offer_claim_alert_timeout_message)
                    .setBackgroundColor(R.color.accent)
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .setDuration(displayDuration)
                    .show();
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public static class ShowClaimOfferSuccessAlertEvent {
        public ShowClaimOfferSuccessAlertEvent() {

        }
    }

    public static class ShowClaimOfferFailureAlertEvent {
        public ShowClaimOfferFailureAlertEvent(){

        }
    }

    public static class ShowClaimOfferTimeoutAlertEvent {
        public ShowClaimOfferTimeoutAlertEvent(){

        }
    }
}
