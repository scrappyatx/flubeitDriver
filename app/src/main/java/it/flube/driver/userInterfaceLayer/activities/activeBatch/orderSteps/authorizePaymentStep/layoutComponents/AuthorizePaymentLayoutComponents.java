/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 8/14/2018
 * Project : Driver
 */
public class AuthorizePaymentLayoutComponents implements
        StepDetailSwipeCompleteButtonComponent.Response,
        PaymentVerificationLayoutComponent.Response,
        ReceiptRequestLayoutComponent.Response,
        TransactionIdLayoutComponent.Response,
        TransactionTotalLayoutComponent.Response {
    private static final String TAG = "AuthorizePaymentLayoutComponents";

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private TextView paymentType;
    private TextView paymentAccount;

    private PaymentVerificationLayoutComponent paymentRow;
    private ReceiptRequestLayoutComponent receiptRow;
    private TransactionIdLayoutComponent transactionIdRow;
    private TransactionTotalLayoutComponent transactionTotalRow;

    private StepDetailSwipeCompleteButtonComponent stepComplete;

    private Response response;
    private ServiceOrderAuthorizePaymentStep orderStep;

    public AuthorizePaymentLayoutComponents(AppCompatActivity activity, Response response){
        this.response = response;

        stepTitle = new StepDetailTitleLayoutComponents(activity);
        stepDueBy = new StepDetailDueByLayoutComponents(activity);
        paymentType = (TextView) activity.findViewById(R.id.authorize_payment_type);
        paymentAccount = (TextView) activity.findViewById(R.id.authorize_payment_display_account);

        /// setup rows
        paymentRow = new PaymentVerificationLayoutComponent(activity, this);
        receiptRow = new ReceiptRequestLayoutComponent(activity, this);
        transactionIdRow = new TransactionIdLayoutComponent(activity, this);
        transactionTotalRow = new TransactionTotalLayoutComponent(activity, this);

        stepComplete = new StepDetailSwipeCompleteButtonComponent(activity, activity.getResources().getString(R.string.authorize_payment_completed_step_button_caption), this);

        Timber.tag(TAG).d("created");
    }

    public void showWaitingAnimationAndBanner(AppCompatActivity activity){
        Timber.tag(TAG).d("showWaitingAnimationBanner");
        paymentRow.setInvisible();
        receiptRow.setInvisible();
        transactionIdRow.setInvisible();
        transactionTotalRow.setInvisible();
        stepComplete.showWaitingAnimationAndBanner(activity.getString(R.string.authorize_payment_completed_banner_text));
    }

    public void setValues(AppCompatActivity activity, ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("setValues START...");
        this.orderStep = orderStep;

        stepTitle.setValues(activity, orderStep);
        stepDueBy.setValues(activity, orderStep);

        Timber.tag(TAG).d("   paymentType -> " + orderStep.getPaymentAuthorization().getPaymentType().toString());
        switch (orderStep.getPaymentAuthorization().getPaymentType()){
            case CHARGE_TO_ACCOUNT:
                paymentType.setText(R.string.authorize_payment_payment_type_charge_to_account_text);
                paymentAccount.setText(orderStep.getPaymentAuthorization().getDisplayChargeAccountId());
                break;
            case CHARGE_TO_CARD_ON_FILE:
                paymentType.setText(R.string.authorize_payment_payment_type_charge_to_card_on_file_text);
                paymentAccount.setText(orderStep.getPaymentAuthorization().getDisplayMaskedCardOnFile());
                break;
            default:
                Timber.tag(TAG).w("   ...should never get here");
                break;
        }

        paymentRow.setValues(activity, orderStep.getPaymentAuthorization());

        if (orderStep.getRequireReceipt()) {
            Timber.tag(TAG).d("   ...receipt photo required");
            receiptRow.setValues(orderStep.getReceiptRequest());
        } else {
            Timber.tag(TAG).d("   ...no receipt photo required");
        }

        if (orderStep.getRequireServiceProviderTransactionId()){
            Timber.tag(TAG).d("   ...transaction id required");
            transactionIdRow.setValues(activity, orderStep.getServiceProviderTransactionId(), orderStep.getTransactionIdStatus(), orderStep.getStatusIconText());

        } else {
            Timber.tag(TAG).d("   ...transaction id NOT required");
        }

        if (orderStep.getRequireServiceProviderTransactionTotal()){
            Timber.tag(TAG).d("   ...transaction total required");
            transactionTotalRow.setValues(activity, orderStep.getServiceProviderTransactionTotal(), orderStep.getTransactionTotalStatus(), orderStep.getStatusIconText());

        } else {
            Timber.tag(TAG).d("   ...transaction total NOT required");
        }

        Timber.tag(TAG).d("...setValues COMPLETE");
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible START...");
        if (orderStep != null){
            stepTitle.setVisible();
            stepDueBy.setVisible();
            paymentType.setVisibility(View.VISIBLE);
            paymentAccount.setVisibility(View.VISIBLE);

            /// deprecating payment row, will never be seen
            paymentRow.setInvisible();

            if (orderStep.getRequireReceipt()){
                Timber.tag(TAG).d("...receipt photo required");
                receiptRow.setVisible();
            } else {
                Timber.tag(TAG).d("...receipt photo NOT required");
                receiptRow.setInvisible();
            }

            if (orderStep.getRequireServiceProviderTransactionId()){
                ///only show transaction id step after a receipt photo has been attempted, if a receipt photo is required
                Timber.tag(TAG).d("...transaction ID required");
                if (orderStep.getRequireReceipt() && orderStep.getReceiptRequest().getReceiptStatus() != ReceiptRequest.ReceiptStatus.NOT_ATTEMPTED){
                    transactionIdRow.setVisible();
                } else {
                    transactionIdRow.setInvisible();
                }
            } else {
                transactionIdRow.setInvisible();
            }

            if (orderStep.getRequireServiceProviderTransactionTotal()){
                Timber.tag(TAG).d("...transaction total required");
                ///only show transaction id step after a receipt photo has been attempted, if a receipt photo is required
                if (orderStep.getRequireReceipt() && orderStep.getReceiptRequest().getReceiptStatus() != ReceiptRequest.ReceiptStatus.NOT_ATTEMPTED){
                    transactionTotalRow.setVisible();
                } else {
                    transactionTotalRow.setInvisible();
                }
            } else {
                Timber.tag(TAG).d("...transaction total NOT required");
                transactionTotalRow.setInvisible();
            }

            setStepCompleteStatus();

        } else {
            setInvisible();
        }
        Timber.tag(TAG).d("...setVisible COMPLETE");
    }

    private void setStepCompleteStatus(){
        if (receiptRequestReadyToFinish() && transactionIdReadyToFinish() && transactionTotalReadyToFinish()){
            Timber.tag(TAG).d("ready to finish");
            stepComplete.setVisible();
        } else {
            Timber.tag(TAG).d("not ready to finish");
            stepComplete.setInvisible();
        }
    }

    private void checkPaymentAuthStatus(){
        Timber.tag(TAG).d("checkPaymentAuthStatus -> " + orderStep.getPaymentAuthorization().getPaymentVerificationStatus().toString());

        if (orderStep.getPaymentAuthorization().getVerifyPaymentAmount()) {
            Timber.tag(TAG).d("   ...we need to verify payment amount");
            switch (orderStep.getPaymentAuthorization().getPaymentVerificationStatus()) {
                case NOT_VERIFIED:
                    Timber.tag(TAG).d("   ...not verified, setInvisible");
                    stepComplete.setInvisible();
                    break;
                case PAYMENT_AT_OR_BELOW_LIMIT:
                    Timber.tag(TAG).d("   ...payment at or below, setVisible");
                    stepComplete.setVisible();
                    break;
                case PAYMENT_EXCEEDS_LIMIT:
                    Timber.tag(TAG).d("   ...payment exceeds, setVisible");
                    stepComplete.setVisible();
                    break;
            }
        } else {
            //we don't need to verify payment amount
            Timber.tag(TAG).d("   ...dont' need to set payment amount, setVisible");
            stepComplete.setVisible();
        }
    }

    private Boolean receiptRequestReadyToFinish(){
        Timber.tag(TAG).d("receiptRequestReadyToFinish");
        if (orderStep.getRequireReceipt()){
            Timber.tag(TAG).d("   ...we require a receipt photo, check status");
            switch (orderStep.getReceiptRequest().getReceiptStatus()){
                case COMPLETED_SUCCESS:
                    Timber.tag(TAG).d("   ...we have one, ok to finish");
                    return true;
                case COMPLETED_FAILED:
                    Timber.tag(TAG).d("   ...at least we tried, ok to finish");
                    return true;
                case NOT_ATTEMPTED:
                    Timber.tag(TAG).d("   ...not attempted, can't finish yet");
                    return false;
                default:
                    Timber.tag(TAG).d("   ...should never get here, but we'll finish");
                    return true;
            }
        } else {
            Timber.tag(TAG).d("   ...don't need a receipt photo, we'll finish");
            return true;
        }
    }

    private Boolean transactionIdReadyToFinish(){
        Timber.tag(TAG).d("transactionIdReadyToFinish");
        if (orderStep.getRequireServiceProviderTransactionId()){
            Timber.tag(TAG).d("   ...we require a transaction id, check status");
            switch (orderStep.getTransactionIdStatus()){
                case COMPLETE:
                    Timber.tag(TAG).d("   ... ok to finish");
                    return true;
                case NOT_ATTEMPTED:
                    Timber.tag(TAG).d("   ... not ok to finish");
                    return false;
                default:
                    Timber.tag(TAG).d("   ... should never get here");
                    return true;
            }
        } else {
            Timber.tag(TAG).d("   ...don't require a transaction id, ready to finish");
            return true;
        }
    }

    private Boolean transactionTotalReadyToFinish(){
        Timber.tag(TAG).d("transactionTotalReadyToFinish");
        if (orderStep.getRequireServiceProviderTransactionTotal()){
            Timber.tag(TAG).d("   ...we require a transaction total, check status");
            switch (orderStep.getTransactionTotalStatus()){
                case COMPLETE:
                    Timber.tag(TAG).d("   ... ok to finish");
                    return true;
                case NOT_ATTEMPTED:
                    Timber.tag(TAG).d("   ... not ok to finish");
                    return false;
                default:
                    Timber.tag(TAG).d("   ... should never get here");
                    return true;
            }
        } else {
            Timber.tag(TAG).d("   ...don't require a transaction id, ready to finish");
            return true;
        }
    }

    public void setInvisible(){
        stepTitle.setInvisible();
        stepDueBy.setInvisible();
        paymentType.setVisibility(View.INVISIBLE);
        paymentAccount.setVisibility(View.INVISIBLE);

        paymentRow.setInvisible();
        receiptRow.setInvisible();
        transactionIdRow.setInvisible();
        transactionTotalRow.setInvisible();

        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone(){
        stepTitle.setGone();
        stepDueBy.setGone();
        paymentType.setVisibility(View.GONE);
        paymentAccount.setVisibility(View.GONE);

        paymentRow.setGone();
        receiptRow.setGone();
        transactionIdRow.setGone();
        transactionTotalRow.setGone();

        Timber.tag(TAG).d("setGone");
    }

    public void close(){
        stepTitle=null;
        stepDueBy=null;
        paymentType=null;
        paymentAccount=null;

        paymentRow=null;
        receiptRow=null;
        transactionIdRow = null;
        transactionTotalRow = null;

        stepComplete=null;

        response = null;
        orderStep = null;
        Timber.tag(TAG).d("close");
    }

    ///
    /// PaymentVerificationLayoutComponent.Response interface
    ///
    public void paymentRowClicked(PaymentAuthorization paymentAuthorization){
        Timber.tag(TAG).d("paymentRowClicked, verificationStatus -> " + paymentAuthorization.getPaymentVerificationStatus().toString());
        setStepCompleteStatus();
        response.paymentRowClicked(paymentAuthorization);
    }

    ///
    /// ReceiptRequestLayoutComponent.Response interface
    ///
    public void receiptPhotoRowClicked(ReceiptRequest receiptRequest){
        Timber.tag(TAG).d("receiptPhotoRowClicked");
        setStepCompleteStatus();
        response.receiptRowClicked(receiptRequest);
    }

    ///
    /// TransactionIdLayoutComponent Response interface
    ///
    public void transactionIdRowClicked(){
        Timber.tag(TAG).d("transactionIdRowClicked");
        setStepCompleteStatus();
        response.transactionIdClicked();
    }

    ////
    /// TransactionTotalLayoutComponent Response interface
    ///
    public void transactionTotalRowClicked(){
        Timber.tag(TAG).d("transactionTotalRowClicked");
        setStepCompleteStatus();
        response.transactionTotalClicked();
    }

    ///
    /// StepDetailSwipeComplete response interface
    ///
    public void stepDetailSwipeCompleteButtonClicked(){
        Timber.tag(TAG).d("stepDetailSwipeCompleteButtonClicked");
        response.stepCompleteClicked(orderStep);
    }

    public interface Response {
        void receiptRowClicked(ReceiptRequest receiptRequest);

        void paymentRowClicked(PaymentAuthorization paymentAuthorization);

        void transactionIdClicked();

        void transactionTotalClicked();

        void stepCompleteClicked(ServiceOrderAuthorizePaymentStep orderStep);
    }
}
