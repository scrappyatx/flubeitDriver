/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.layoutComponents;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.text.NumberFormat;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import timber.log.Timber;

/**
 * Created on 8/13/2018
 * Project : Driver
 */
public class PaymentVerificationLayoutComponent implements
        View.OnClickListener {
    private static final String TAG="PaymentVerificationLayoutComponent";

    private ConstraintLayout border;
    private TextView paymentAmount;
    private IconTextView paymentStatus;

    private Response response;
    private PaymentAuthorization paymentAuthorization;


    public PaymentVerificationLayoutComponent(AppCompatActivity activity, Response response){
        this.response = response;

        border = (ConstraintLayout) activity.findViewById(R.id.payment_amount_row_item);
        border.setClickable(true);
        border.setFocusable(true);
        border.setFocusableInTouchMode(true);
        border.setOnClickListener(this);

        paymentAmount = (TextView) activity.findViewById(R.id.payment_amount_title);
        paymentStatus = (IconTextView) activity.findViewById(R.id.payment_amount_status);
        Timber.tag(TAG).d("created");
    }

    public void setValues(AppCompatActivity activity, PaymentAuthorization paymentAuthorization){
        this.paymentAuthorization = paymentAuthorization;

        String displayMaxPaymentAmount = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(paymentAuthorization.getMaxPaymentAmountCents()/100);
        paymentAmount.setText(String.format(activity.getResources().getString(R.string.authorize_payment_amount_row_text), displayMaxPaymentAmount));

        paymentStatus.setText(paymentAuthorization.getVerificationStatusIconText().get(paymentAuthorization.getPaymentVerificationStatus().toString()));
    }

    public void setVisible(){
        if (paymentAuthorization != null){
            Timber.tag(TAG).d("   ...we have a paymentAuthorizaiton");
            if (paymentAuthorization.getVerifyPaymentAmount()){
                Timber.tag(TAG).d("      ...verify payment amount");
                border.setVisibility(View.VISIBLE);
                paymentAmount.setVisibility(View.VISIBLE);
                paymentStatus.setVisibility(View.VISIBLE);
            } else {
                Timber.tag(TAG).d("      ...do NOT verify payment amount");
                setGone();
            }
        } else {
            Timber.tag(TAG).d("   ...paymentAuthorization is null");
            setGone();
        }
    }

    public void setInvisible(){
        border.setVisibility(View.INVISIBLE);
        paymentAmount.setVisibility(View.INVISIBLE);
        paymentStatus.setVisibility(View.INVISIBLE);
    }

    public void setGone(){
        border.setVisibility(View.GONE);
        paymentAmount.setVisibility(View.GONE);
        paymentStatus.setVisibility(View.GONE);
        Timber.tag(TAG).d("setGone");
    }

    public void close(){
        border=null;
        paymentAmount=null;
        paymentStatus=null;
        response = null;
        paymentAuthorization=null;
    }

    ///
    /// View.OnClickInterface
    ///
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        /// update the paymentVerificationStatus
        switch (paymentAuthorization.getPaymentVerificationStatus()){
            case NOT_VERIFIED:
                paymentAuthorization.setPaymentVerificationStatus(PaymentAuthorization.PaymentVerificationStatus.PAYMENT_AT_OR_BELOW_LIMIT);
                break;
            case PAYMENT_EXCEEDS_LIMIT:
                paymentAuthorization.setPaymentVerificationStatus(PaymentAuthorization.PaymentVerificationStatus.PAYMENT_AT_OR_BELOW_LIMIT);
                break;
            case PAYMENT_AT_OR_BELOW_LIMIT:
                paymentAuthorization.setPaymentVerificationStatus(PaymentAuthorization.PaymentVerificationStatus.PAYMENT_EXCEEDS_LIMIT);
                break;
        }
        paymentStatus.setText(paymentAuthorization.getVerificationStatusIconText().get(paymentAuthorization.getPaymentVerificationStatus().toString()));
        response.paymentRowClicked(paymentAuthorization);
    }

    public interface Response {
        void paymentRowClicked(PaymentAuthorization paymentAuthorization);
    }
}
