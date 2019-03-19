/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import timber.log.Timber;

/**
 * Created on 3/17/2019
 * Project : Driver
 */
public class ReceiptDetailButtonLayoutComponents implements
        Button.OnClickListener {
    public final static String TAG = "ReceiptDetailButtonLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     step_receipt_detail_complete.xml
    ///
    public static final String TAG_KEEP_BUTTON = "KEEP_BUTTON";
    public static final String TAG_RETAKE_BUTTON = "RETAKE_BUTTON";

    private Button retakeButton;
    private Button keepButton;
    private TextView banner;
    private LottieAnimationView animation;

    private Response response;

    public ReceiptDetailButtonLayoutComponents(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("ReceiptDetailButtonLayoutComponents");

        animation = (LottieAnimationView) activity.findViewById(R.id.receipt_detail_complete_animation);
        animation.useHardwareAcceleration(true);
        animation.enableMergePathsForKitKatAndAbove(true);

        banner = (TextView) activity.findViewById(R.id.receipt_detail_complete_banner);

        keepButton = (Button) activity.findViewById(R.id.save_photo_button);
        keepButton.setTag(TAG_KEEP_BUTTON);
        keepButton.setOnClickListener(this);
        keepButton.setText(activity.getResources().getString(R.string.receipt_detail_keep_button_caption));

        retakeButton = (Button) activity.findViewById(R.id.discard_photo_button);
        retakeButton.setTag(TAG_RETAKE_BUTTON);
        retakeButton.setOnClickListener(this);
        retakeButton.setText(activity.getResources().getString(R.string.receipt_detail_discard_button_caption));

        this.response = response;
        setGone();
        Timber.tag(TAG).d("...components created");
    }



    public void showWaitingAnimationAndBanner(String bannerText){
        Timber.tag(TAG).d("showWaitingAnimationAndBanner");
        Timber.tag(TAG).d("...bannerText -> %s", bannerText);
        keepButton.setVisibility(View.GONE);
        retakeButton.setVisibility(View.GONE);

        banner.setText(bannerText);
        banner.setVisibility(View.VISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
    }

    public void showWaitingAnimationWithNoBanner(){
        Timber.tag(TAG).d("showWaitingAnimationWithNoBanner");
        keepButton.setVisibility(View.GONE);
        retakeButton.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible");
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        keepButton.setVisibility(View.INVISIBLE);
        retakeButton.setVisibility(View.INVISIBLE);
        keepButton.setVisibility(View.VISIBLE);
        retakeButton.setVisibility(View.VISIBLE);
    }

    public void setInvisible(){
        Timber.tag(TAG).d("setInvisible");
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        keepButton.setVisibility(View.INVISIBLE);
        retakeButton.setVisibility(View.INVISIBLE);
    }

    public void setGone(){
        Timber.tag(TAG).d("setGone");
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        keepButton.setVisibility(View.GONE);
        retakeButton.setVisibility(View.GONE);
    }

    public void close(){
        Timber.tag(TAG).d("onClick");
        animation.setImageBitmap(null);
        animation = null;
        retakeButton = null;
        keepButton = null;
        banner = null;
        response = null;
    }

    /// Button.OnClickListener
    public void onClick(View v){
        Timber.tag(TAG).d("onClick -> %s", v.getTag());
        switch ((String) v.getTag()){
            case TAG_KEEP_BUTTON:
                response.receiptDetailKeepButtonClicked();
                break;
            case TAG_RETAKE_BUTTON:
                response.receiptDetailRetakeButtonClicked();
                break;
            default:
                break;
        }
    }

    public interface Response {
        void receiptDetailRetakeButtonClicked();

        void receiptDetailKeepButtonClicked();
    }

}
