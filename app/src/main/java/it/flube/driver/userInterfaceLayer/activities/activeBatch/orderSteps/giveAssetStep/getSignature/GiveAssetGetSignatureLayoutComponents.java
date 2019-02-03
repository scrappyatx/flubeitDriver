/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.getSignature;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.gcacace.signaturepad.views.SignaturePad;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import timber.log.Timber;

/**
 * Created on 8/16/2018
 * Project : Driver
 */
public class GiveAssetGetSignatureLayoutComponents implements
        SignaturePad.OnSignedListener,
        View.OnClickListener {
    public static final String TAG = "ReceiveAssetGetSignatureLayoutComponents";

    ///
    /// wrapper for activity_get_signature.xml
    ///

    public static final String SAVE_BUTTON_TAG="saveButton";
    public static final String CLEAR_BUTTON_TAG="clearButton";

    private SignaturePad signaturePad;
    private TextView instructions;
    private Button saveButton;
    private Button clearButton;
    private TextView savingBanner;
    private LottieAnimationView savingAnimation;

    private ServiceOrderGiveAssetStep orderStep;
    private GiveAssetGetSignatureLayoutComponents.Response response;

    public GiveAssetGetSignatureLayoutComponents(AppCompatActivity activity, GiveAssetGetSignatureLayoutComponents.Response response){
        this.response = response;

        signaturePad = (SignaturePad) activity.findViewById(R.id.signature_pad);
        signaturePad.setOnSignedListener(this);

        instructions = (TextView) activity.findViewById(R.id.signature_instructions);

        saveButton = (Button) activity.findViewById(R.id.signature_button_save);
        saveButton.setTag(SAVE_BUTTON_TAG);
        saveButton.setOnClickListener(this);

        clearButton = (Button) activity.findViewById(R.id.signature_button_clear);
        clearButton.setTag(CLEAR_BUTTON_TAG);
        clearButton.setOnClickListener(this);

        savingBanner = (TextView) activity.findViewById(R.id.signature_saving_banner);
        savingAnimation = (LottieAnimationView) activity.findViewById(R.id.signature_saving_animation);
        savingAnimation.useHardwareAcceleration(true);
        savingAnimation.enableMergePathsForKitKatAndAbove(true);

        setInvisible();
    }

    public ServiceOrderGiveAssetStep getOrderStep(){
        Timber.tag(TAG).d("getOrderStep");
        return this.orderStep;
    }

    public void setValues(ServiceOrderGiveAssetStep orderStep){
        Timber.tag(TAG).d("setValues");
        this.orderStep = orderStep;
    }

    public void showSavingAnimation(){
        signaturePad.setVisibility(View.GONE);
        instructions.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);

        savingBanner.setVisibility(View.VISIBLE);
        savingAnimation.setVisibility(View.VISIBLE);
        savingAnimation.setProgress(0);
        savingAnimation.playAnimation();
    }

    public void setVisible(){
        signaturePad.setVisibility(View.VISIBLE);
        instructions.setVisibility(View.VISIBLE);

        saveButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);

        savingBanner.setVisibility(View.GONE);
        savingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        signaturePad.setVisibility(View.INVISIBLE);
        instructions.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        clearButton.setVisibility(View.INVISIBLE);

        savingBanner.setVisibility(View.GONE);
        savingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone() {
        signaturePad.setVisibility(View.GONE);
        instructions.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);

        savingBanner.setVisibility(View.GONE);
        savingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        signaturePad=null;

        instructions=null;
        saveButton=null;
        clearButton=null;
        savingBanner=null;
        savingAnimation.setImageBitmap(null);
        savingAnimation=null;
        response = null;
    }

    ///
    /// OnSignedListener interface
    ///
    public void onStartSigning() {
        //Event triggered when the pad is touched
        Timber.tag(TAG).d("onStartSigning");
    }

    public void onSigned() {
        //Event triggered when the pad is signed
        Timber.tag(TAG).d("onSigned");

        saveButton.setVisibility(View.VISIBLE);
        clearButton.setVisibility(View.VISIBLE);

        savingBanner.setVisibility(View.GONE);
        savingAnimation.setVisibility(View.GONE);
    }

    public void onClear() {
        //Event triggered when the pad is cleared
        Timber.tag(TAG).d("onClear");

        saveButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);

        savingBanner.setVisibility(View.GONE);
        savingAnimation.setVisibility(View.GONE);
    }

    /// View.OnClickListener interface
    public void onClick(View v){
        //Event triggered a button is clicked
        Timber.tag(TAG).d("onClick");
        switch ((String) v.getTag()){
            case SAVE_BUTTON_TAG:
                Timber.tag(TAG).d("...save button clicked");
                response.gotSignature(orderStep.getSignatureRequest(), signaturePad.getSignatureBitmap());
                break;
            case CLEAR_BUTTON_TAG:
                Timber.tag(TAG).d("...clear button clicked");
                signaturePad.clear();
                break;
            default:
                Timber.tag(TAG).w("...should never get here");
                break;
        }
    }

    public interface Response {
        void gotSignature(SignatureRequest signatureRequest, Bitmap signatureBitmap);
    }

}

