/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.transactionIdDetail;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail.ReceiptDetailButtonLayoutComponents;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import timber.log.Timber;

/**
 * Created on 3/18/2019
 * Project : Driver
 */
public class TransactionIdDetailLayoutComponents implements
        Button.OnClickListener,
        TextWatcher {
    private static final String TAG = "TransactionIdDetailLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_transaction_id_detail.xml
    ///
    private static final String DEVICE_OCR_BUTTON_TAG = "DEVICE_OCR_BUTTON";
    private static final String CLOUD_OCR_BUTTON_TAG = "CLOUD_OCR_BUTTON";
    private static final String MANUAL_ENTRY_DONE_BUTTON_TAG = "MANUAL_ENTRY_DONE_BUTTON";

    private Button deviceOcrButton;
    private Button cloudOcrButton;
    private TextView detectedTitle;

    private TextView manualTitle;
    private Button manualEntryDoneButton;
    private EditText manualEntryText;
    private LottieAnimationView animation;

    private ServiceOrderAuthorizePaymentStep orderStep;
    private Response response;

    public TransactionIdDetailLayoutComponents(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("TransactionIdDetailLayoutComponents");
        this.response = response;

        animation = (LottieAnimationView) activity.findViewById(R.id.transaction_id_detail_waiting_animation);
        animation.useHardwareAcceleration(true);
        animation.enableMergePathsForKitKatAndAbove(true);

        detectedTitle = (TextView) activity.findViewById(R.id.transaction_id_title);

        deviceOcrButton = (Button) activity.findViewById(R.id.use_device_ocr_detected_button);
        deviceOcrButton.setTag(DEVICE_OCR_BUTTON_TAG);
        deviceOcrButton.setOnClickListener(this);

        cloudOcrButton = (Button) activity.findViewById(R.id.use_cloud_ocr_detected_button);
        cloudOcrButton.setTag(CLOUD_OCR_BUTTON_TAG);
        cloudOcrButton.setOnClickListener(this);

        manualTitle = (TextView) activity.findViewById(R.id.manual_title);

        manualEntryDoneButton = (Button) activity.findViewById(R.id.manual_entry_complete_button);
        manualEntryDoneButton.setTag(MANUAL_ENTRY_DONE_BUTTON_TAG);
        manualEntryDoneButton.setOnClickListener(this);

        manualEntryText = (EditText) activity.findViewById(R.id.manual_entry_text);
        manualEntryText.addTextChangedListener(this);

        setInvisible();
    }

    public void setValues(AppCompatActivity activity, ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("setValues");
        this.orderStep = orderStep;

        //setup device ocr button
        if (haveDeviceOcrTransactionId(orderStep)){
            Timber.tag(TAG).d("...we have a device ocr transaction id");
            deviceOcrButton.setText(orderStep.getReceiptRequest().getReceiptAnalysis().getDeviceOcrResults().getTransactionId());
        } else {
            Timber.tag(TAG).d("...don't have a device ocr transaction id");
            deviceOcrButton.setText(null);
        }

        //setup cloud ocr button
        if (haveCloudOcrTransactionId(orderStep)){
            Timber.tag(TAG).d("...we have a cloud ocr transaction id");
            cloudOcrButton.setText(orderStep.getReceiptRequest().getReceiptAnalysis().getCloudOcrResults().getTransactionId());
        } else {
            Timber.tag(TAG).d("...don't have a cloud ocr transaction id");
            cloudOcrButton.setText(null);
        }

        //setup manual entry text
        if ((orderStep.getServiceProviderTransactionId() != null) && (orderStep.getServiceProviderTransactionId().length() != 0)){
            Timber.tag(TAG).d("...have a service provider transaction id");
            manualEntryText.setText(orderStep.getServiceProviderTransactionId());
            //put cursor at end
            manualEntryText.setSelection(manualEntryText.getText().length());
        } else {
            Timber.tag(TAG).d("...service provider transaction id length is zero");
            manualEntryText.setText(null);
        }

    }

    private Boolean haveDeviceOcrTransactionId(ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("haveDeviceOcrTransactionId");
        if (orderStep.getReceiptRequest() != null){
            // we have a receipt request
            Timber.tag(TAG).d("...we have a receipt request");
            if (orderStep.getReceiptRequest().getReceiptAnalysis() != null){
                Timber.tag(TAG).d("...we have a receipt analysis");
                // check for device ocr results
                if (orderStep.getReceiptRequest().getReceiptAnalysis().getDeviceOcrResults() != null){
                    //we have device ocr results
                    Timber.tag(TAG).d("...we have device ocr results");
                    if (orderStep.getReceiptRequest().getReceiptAnalysis().getDeviceOcrResults().getFoundTransactionId()){
                        // we have a transaction id
                        Timber.tag(TAG).d("...we have a transaction id");
                        return true;
                    } else {
                        //we don't have a transaction id
                        Timber.tag(TAG).d("...don't have a transaction id");
                        return false;
                    }
                } else {
                    //we don't have device ocr results
                    Timber.tag(TAG).d("...don't have device ocr results");
                    return false;
                }
            } else {
                // there is no receipt analysis
                Timber.tag(TAG).d("...don't have receipt analysis");
                return false;
            }
        } else {
            // we don't have a receipt request
            Timber.tag(TAG).d("...don't have receipt request");
            return false;
        }
    }

    private Boolean haveCloudOcrTransactionId(ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("haveCloudOcrTransactionId");
        if (orderStep.getReceiptRequest() != null){
            // we have a receipt request
            Timber.tag(TAG).d("...we have a receipt request");
            if (orderStep.getReceiptRequest().getReceiptAnalysis() != null){
                Timber.tag(TAG).d("...we have a receipt analysis");
                // check for device ocr results
                if (orderStep.getReceiptRequest().getReceiptAnalysis().getCloudOcrResults() != null){
                    //we have device ocr results
                    Timber.tag(TAG).d("...we have cloud ocr results");
                    if (orderStep.getReceiptRequest().getReceiptAnalysis().getCloudOcrResults().getFoundTransactionId()){
                        // we have a transaction id
                        Timber.tag(TAG).d("...we have a transaction id");
                        return true;
                    } else {
                        //we don't have a transaction id
                        Timber.tag(TAG).d("...don't have a transaction id");
                        return false;
                    }
                } else {
                    //we don't have device ocr results
                    Timber.tag(TAG).d("...don't have cloud ocr results");
                    return false;
                }
            } else {
                // there is no receipt analysis
                Timber.tag(TAG).d("...don't have receipt analysis");
                return false;
            }
        } else {
            // we don't have a receipt request
            Timber.tag(TAG).d("...don't have receipt request");
            return false;
        }
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible");

        //assume we have no detected results
        detectedTitle.setVisibility(View.INVISIBLE);

        if (deviceOcrButton.getText().length() != 0){
            Timber.tag(TAG).d("...device ocr -> %s", deviceOcrButton.getText());
            deviceOcrButton.setVisibility(View.VISIBLE);
            detectedTitle.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("...device ocr is zero length");
            deviceOcrButton.setVisibility(View.INVISIBLE);
        }

        if (cloudOcrButton.getText().length() != 0){
            Timber.tag(TAG).d("...cloud ocr -> %s", cloudOcrButton.getText());
            cloudOcrButton.setVisibility(View.VISIBLE);
            detectedTitle.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("...cloud ocr is zero length");
            cloudOcrButton.setVisibility(View.INVISIBLE);
        }

        manualTitle.setVisibility(View.VISIBLE);
        manualEntryText.setVisibility(View.VISIBLE);
        if (manualEntryText.getText().length() != 0){
            Timber.tag(TAG).d("...manual text -> %s", manualEntryText.getText());
            manualEntryDoneButton.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("...manual text is zero length");
            manualEntryDoneButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setGone(){
        Timber.tag(TAG).d("setGone");
        deviceOcrButton.setVisibility(View.GONE);
        cloudOcrButton.setVisibility(View.GONE);
        detectedTitle.setVisibility(View.GONE);

        manualTitle.setVisibility(View.GONE);
        manualEntryDoneButton.setVisibility(View.GONE);
        manualEntryText.setVisibility(View.GONE);
        animation.setVisibility(View.GONE);
    }

    public void setInvisible(){
        Timber.tag(TAG).d("setInvisible");
        deviceOcrButton.setVisibility(View.INVISIBLE);
        cloudOcrButton.setVisibility(View.INVISIBLE);
        detectedTitle.setVisibility(View.INVISIBLE);

        manualTitle.setVisibility(View.INVISIBLE);
        manualEntryDoneButton.setVisibility(View.INVISIBLE);
        manualEntryText.setVisibility(View.INVISIBLE);
        animation.setVisibility(View.INVISIBLE);
    }

    public void close(){
        Timber.tag(TAG).d("close");

        deviceOcrButton = null;
        cloudOcrButton = null;
        detectedTitle = null;

        manualEntryDoneButton = null;
        manualEntryText = null;
        animation.setImageBitmap(null);
        animation = null;

        orderStep = null;
        response = null;
    }

    private void showAnimation(){
        Timber.tag(TAG).d("showAnimation");
        setInvisible();

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();

    }

    /// Button.OnClickListener
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
       switch ((String) v.getTag()){
           case DEVICE_OCR_BUTTON_TAG:
               Timber.tag(TAG).d("  device ocr transaction id -> %s",deviceOcrButton.getText().toString());

               orderStep.setServiceProviderTransactionId(deviceOcrButton.getText().toString());
               orderStep.setTransactionIdSourceType(ServiceOrderAuthorizePaymentStep.DataSourceType.DEVICE_OCR);
               orderStep.setTransactionIdStatus(ServiceOrderAuthorizePaymentStep.DataEntryStatus.COMPLETE);

               showAnimation();
               response.detectedDeviceOcrButtonClicked(orderStep);
               break;
           case CLOUD_OCR_BUTTON_TAG:

               Timber.tag(TAG).d("  cloud ocr transaction id -> %s",cloudOcrButton.getText().toString());
               orderStep.setServiceProviderTransactionId(cloudOcrButton.getText().toString());
               orderStep.setTransactionIdSourceType(ServiceOrderAuthorizePaymentStep.DataSourceType.CLOUD_OCR);
               orderStep.setTransactionIdStatus(ServiceOrderAuthorizePaymentStep.DataEntryStatus.COMPLETE);

               showAnimation();
               response.detectedCloudOcrButtonClicked(orderStep);
               break;
           case MANUAL_ENTRY_DONE_BUTTON_TAG:
               Timber.tag(TAG).d("  manual transaction id -> %s", manualEntryText.getText().toString());

               orderStep.setServiceProviderTransactionId(manualEntryText.getText().toString());
               orderStep.setTransactionIdSourceType(ServiceOrderAuthorizePaymentStep.DataSourceType.MANUAL_ENTRY);
               orderStep.setTransactionIdStatus(ServiceOrderAuthorizePaymentStep.DataEntryStatus.COMPLETE);

               showAnimation();
               response.manualEntryButtonClicked(orderStep);
               break;
           default:
               Timber.tag(TAG).d("should never get here");
               break;
       }
    }

    /// TextWatcher listener
    public void afterTextChanged(Editable s){
        //This method is called to notify you that, somewhere within s, the text has been changed.
        Timber.tag(TAG).d("afterTextChanged");
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after){
        //This method is called to notify you that, within s, the count characters beginning
        // at start are about to be replaced by new text with length after.
        Timber.tag(TAG).d("beforeTextChanged");

    }

    public void onTextChanged(CharSequence s, int start, int before, int count){
        //This method is called to notify you that, within s, the count characters beginning
        // at start have just replaced old text that had length before.
        Timber.tag(TAG).d("onTextChanged");
        if (s.length() > 0){
            Timber.tag(TAG).d("...setting done button VISIBLE");
            manualEntryDoneButton.setVisibility(View.VISIBLE);
        } else {
            Timber.tag(TAG).d("...setting done button INVISIBLE");
            manualEntryDoneButton.setVisibility(View.INVISIBLE);
        }
    }


    public interface Response {
        void detectedCloudOcrButtonClicked(ServiceOrderAuthorizePaymentStep orderStep);

        void detectedDeviceOcrButtonClicked(ServiceOrderAuthorizePaymentStep orderStep);

        void manualEntryButtonClicked(ServiceOrderAuthorizePaymentStep orderStep);
    }

}
