/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptPhotoTake;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.capability.Capabilities;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.selector.ResolutionSelectorsKt;
import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.useCaseLayer.authorizePaymentStep.UseCaseReceiptOcr;
import it.flube.libbatchdata.entities.ReceiptRequest;
import kotlin.Unit;
import timber.log.Timber;

import static io.fotoapparat.selector.LensPositionSelectorsKt.back;

/**
 * Created on 9/10/2018
 * Project : Driver
 */
public class ReceiptPhotoTakeLayoutComponents implements
        WhenDoneListener<Unit>,
        Button.OnClickListener {

    private static final String TAG="ReceiptPhotoTakeLayoutComponents";
    ///
    /// wrapper class for activity_receipt_photo_take.xml
    ///

    private Fotoapparat fotoapparat;

    private io.fotoapparat.view.CameraView cameraView;
    private LottieAnimationView animation;
    private Button button;

    private Response response;
    private ReceiptRequest receiptRequest;
    private String imageDeviceAbsoluteFileName;

    public ReceiptPhotoTakeLayoutComponents(AppCompatActivity activity, Response response){
        this.response = response;

        button = (Button) activity.findViewById(R.id.photo_button);
        button.setOnClickListener(this);

        animation = (LottieAnimationView) activity.findViewById(R.id.photo_processing_animation);
        animation.useHardwareAcceleration(true);
        animation.enableMergePathsForKitKatAndAbove(true);

        cameraView = (io.fotoapparat.view.CameraView) activity.findViewById(R.id.camera);


        fotoapparat = Fotoapparat
                .with(activity)
                .into(cameraView)
                .previewScaleType(ScaleType.CenterCrop)
                .photoResolution(ResolutionSelectorsKt.highestResolution())
                .lensPosition(back())       // we want back camera
                .build();

        setInvisible();

    }

    public void setValues(ReceiptRequest receiptRequest){
        this.receiptRequest = receiptRequest;
        Timber.tag(TAG).d("setValues");
    }

    public Boolean hasDeviceFile(){
        Timber.tag(TAG).d("hasDeviceFile");
        return ((receiptRequest != null) && (receiptRequest.getHasDeviceFile()));
    }

    public void onResume(){
        fotoapparat.start();
        Timber.tag(TAG).d("...onResume");
    }

    public void onPause(){
        fotoapparat.stop();
        Timber.tag(TAG).d("...onPause");
    }

    public void setVisible(){
        cameraView.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        animation.setVisibility(View.INVISIBLE);

        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        cameraView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        animation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        cameraView.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        animation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        cameraView = null;
        button = null;
        animation.setImageBitmap(null);
        animation=null;
        fotoapparat = null;
        receiptRequest = null;
        Timber.tag(TAG).d("components closed");
    }


    public void onClick(View v){
        Timber.tag(TAG).d("onClick -> take a photo");
        //hide the image preview & show the animation
        cameraView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();

        File saveFile = AndroidDevice.getInstance().getDeviceImageStorage().createUniqueDeviceImageFile();
        imageDeviceAbsoluteFileName = saveFile.getAbsoluteFile().toString();
        Timber.tag(TAG).d("   ...saving to file -> %s", imageDeviceAbsoluteFileName);
        PhotoResult photoResult = fotoapparat.autoFocus().takePicture();
        photoResult.saveToFile(saveFile).whenDone(this);
    }

    /// fotoapparat call back
    public void whenDone(Unit unit){
        Timber.tag(TAG).d("whenDone -> saved to file");
        //AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseReceiptOcr(AndroidDevice.getInstance(), imageDeviceAbsoluteFileName, receiptRequest, this));
        //don't want to analyze photo at this point
        response.receiptTakePhotoComplete(imageDeviceAbsoluteFileName, receiptRequest);
    }

    public interface Response {
        void receiptTakePhotoComplete(String imageDeviceAbsoluteFileName, ReceiptRequest receiptRequest);
    }


}
