/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake;


import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.parameter.ScaleType;

import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.selector.FlashSelectorsKt;
import io.fotoapparat.selector.ResolutionSelectorsKt;
import io.fotoapparat.selector.SelectorsKt;
import it.flube.driver.R;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import kotlin.Unit;
import timber.log.Timber;

import static io.fotoapparat.selector.LensPositionSelectorsKt.back;

/**
 * Created on 5/5/2018
 * Project : Driver
 */
public class PhotoTakeLayoutComponents implements
        WhenDoneListener<Unit> {

    public final static String TAG = "PhotoTakeLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_photo_take.xml
    ///

    //private CameraView cameraView;
    private Fotoapparat fotoapparat;
    private io.fotoapparat.view.CameraView cameraView;
    private LottieAnimationView animation;
    private Button button;
    private PhotoRequest photoRequest;
    private CaptureResponse response;

    private String imageDeviceAbsoluteFileName;

    public PhotoTakeLayoutComponents(AppCompatActivity activity){
        //cameraView = (CameraView) activity.findViewById(R.id.camera);
        button = (Button) activity.findViewById(R.id.photo_button);
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
                        //.flash(SelectorsKt.firstAvailable()) //first available flash mode
                        //.focusMode(SelectorsKt.firstAvailable()) //first available focus mode
                        .build();

        setInvisible();
    }

    public void setValues(PhotoRequest photoRequest){
        this.photoRequest = photoRequest;
        Timber.tag(TAG).d("setValues");
    }

    public void captureRequest(MobileDeviceInterface device, CaptureResponse response){
        Timber.tag(TAG).d("...captureRequest");
        this.response = response;

        //hide the image preview & show the animation
        cameraView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();

        File saveFile = device.getDeviceImageStorage().createUniqueDeviceImageFile();
        imageDeviceAbsoluteFileName = saveFile.getAbsoluteFile().toString();
        Timber.tag(TAG).d("   ...saving to file -> %s", imageDeviceAbsoluteFileName);
        fotoapparat.autoFocus().takePicture().saveToFile(saveFile).whenDone(this);
    }

    /// fotoapparat call back
    public void whenDone(Unit unit){
        Timber.tag(TAG).d("whenDone -> saved to file");
        ///update the photoRequest object

        response.takePhotoComplete(imageDeviceAbsoluteFileName, photoRequest);
    }


    public void onResume(){
        //cameraView.start();
        fotoapparat.start();
        Timber.tag(TAG).d("...onResume");
    }

    public void onPause(){
        //cameraView.stop();
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
        animation.setImageBitmap(null);

        cameraView = null;
        button = null;
        animation=null;
        fotoapparat = null;
        photoRequest = null;
        response = null;

        Timber.tag(TAG).d("components closed");
    }

    public interface CaptureResponse {
        void takePhotoComplete(String imageDeviceAbsoluteFileName, PhotoRequest photoRequest);
    }


}
