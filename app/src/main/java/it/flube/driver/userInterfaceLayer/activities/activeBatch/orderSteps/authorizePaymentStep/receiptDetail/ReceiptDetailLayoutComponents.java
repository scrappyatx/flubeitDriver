/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 9/8/2018
 * Project : Driver
 */
public class ReceiptDetailLayoutComponents implements
        ReceiptDetailButtonLayoutComponents.Response {
    public static final String TAG="ReceiptDetailLayoutComponents";
    ///
    /// wrapper class for activity_receipt_detail.xml
    ///

    private TextView title;
    private ImageView receiptImage;
    private ReceiptDetailButtonLayoutComponents stepComplete;

    private String analyzeImageBannerText;
    private ReceiptRequest receiptRequest;
    private Response response;

    public ReceiptDetailLayoutComponents(AppCompatActivity activity, Response response){
        title = (TextView) activity.findViewById(R.id.receipt_request_title);
        title.setText(activity.getResources().getString(R.string.receipt_detail_receipt_request_title));

        receiptImage = (ImageView) activity.findViewById(R.id.image_receipt);

        stepComplete = new ReceiptDetailButtonLayoutComponents(activity, this);
        analyzeImageBannerText = activity.getResources().getString(R.string.receipt_detail_keep_photo_button_banner);

        this.response = response;
        Timber.tag(TAG).d("created");
    }

    public void setValues(AppCompatActivity activity, ReceiptRequest receiptRequest){
        this.receiptRequest = receiptRequest;

        if (receiptRequest.getHasDeviceFile()){
            String fileName = "file://" + receiptRequest.getDeviceAbsoluteFileName();
            Timber.tag(TAG).d("filename -> " + fileName);

            Picasso.get()
                    .load(fileName)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .fit()
                    .centerInside()
                    .into(receiptImage);
        } else {
            Picasso.get()
                    .load(R.drawable.no_attempt_placeholder_really_small)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .fit()
                    .centerInside()
                    .into(receiptImage);
        }
        Timber.tag(TAG).d("setValues");
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible");
        title.setVisibility(View.VISIBLE);
        receiptImage.setVisibility(View.VISIBLE);
        stepComplete.setVisible();
    }

    public void setInvisible(){
        Timber.tag(TAG).d("setInvisible");
        title.setVisibility(View.INVISIBLE);
        receiptImage.setVisibility(View.INVISIBLE);
        stepComplete.setInvisible();
    }

    public void setGone(){
        Timber.tag(TAG).d("setGone");
        title.setVisibility(View.GONE);
        receiptImage.setVisibility(View.GONE);
        stepComplete.setGone();
    }

    public void close(){
        Timber.tag(TAG).d("close");
        title=null;
        receiptImage=null;
        stepComplete.close();
        response = null;
        receiptRequest = null;
        analyzeImageBannerText = null;
    }

    public void showWaitingAnimation(){
        Timber.tag(TAG).d("showWaitingAnimation");
        stepComplete.showWaitingAnimationWithNoBanner();
    }


    /// response interface StepDetailCompleteButtonComponents
    public void receiptDetailRetakeButtonClicked(){
        Timber.tag(TAG).d("receiptDetailRetakeButtonClicked");
        stepComplete.showWaitingAnimationWithNoBanner();
        response.takePhotoButtonClicked();
    }

    public void receiptDetailKeepButtonClicked(){
        Timber.tag(TAG).d("receiptDetailKeepButtonClicked");
        stepComplete.showWaitingAnimationAndBanner(analyzeImageBannerText);
        response.keepPhotoButtonClicked(receiptRequest);
    }

    public interface Response {
        void takePhotoButtonClicked();

        void keepPhotoButtonClicked(ReceiptRequest receiptRequest);
    }

}
