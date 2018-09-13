/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.FotoapparatBuilder;
import io.fotoapparat.parameter.ScaleType;

import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.selector.ResolutionSelectorsKt;
import io.fotoapparat.view.CameraRenderer;
import it.flube.driver.R;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.photoStep.UseCasePhotoDetectImageLabel;
import it.flube.driver.useCaseLayer.photoStep.UseCaseSavePhotoToDeviceImageStorage;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import kotlin.Unit;
import timber.log.Timber;

import static io.fotoapparat.selector.LensPositionSelectorsKt.back;
import static io.fotoapparat.selector.ResolutionSelectorsKt.highestResolution;
import static io.fotoapparat.selector.SelectorsKt.firstAvailable;

/**
 * Created on 5/5/2018
 * Project : Driver
 */
public class PhotoTakeLayoutComponents implements
        //WhenDoneListener<BitmapPhoto>,
        WhenDoneListener<Unit>,
        CameraKitEventCallback<CameraKitImage>,
        UseCaseSavePhotoToDeviceImageStorage.Response,
        UseCasePhotoDetectImageLabel.Response {

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
    private MobileDeviceInterface device;
    private PhotoRequest photoRequest;
    private CaptureResponse response;

    private String imageDeviceAbsoluteFileName;

    public PhotoTakeLayoutComponents(AppCompatActivity activity){
        //cameraView = (CameraView) activity.findViewById(R.id.camera);
        button = (Button) activity.findViewById(R.id.photo_button);
        animation = (LottieAnimationView) activity.findViewById(R.id.photo_processing_animation);

        cameraView = (io.fotoapparat.view.CameraView) activity.findViewById(R.id.camera);

        fotoapparat = Fotoapparat
                        .with(activity)
                        .into(cameraView)
                        .previewScaleType(ScaleType.CenterCrop)
                        .photoResolution(ResolutionSelectorsKt.lowestResolution())
                        .lensPosition(back())       // we want back camera
                        .build();

        setInvisible();
    }

    public void setValues(PhotoRequest photoRequest){
        this.photoRequest = photoRequest;
        Timber.tag(TAG).d("setValues");
    }

    public void captureRequest(MobileDeviceInterface device, CaptureResponse response){
        Timber.tag(TAG).d("...onResume");
        this.device = device;
        this.response = response;

        //hide the image preview & show the animation
        cameraView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();

        //cameraView.captureImage(this);
        PhotoResult photoResult = fotoapparat.autoFocus().takePicture();
        //photoResult.toBitmap().whenDone(this);
        File saveFile = device.getDeviceImageStorage().createUniqueDeviceImageFile();
        imageDeviceAbsoluteFileName = saveFile.getAbsoluteFile().toString();

        photoResult.saveToFile(saveFile).whenDone(this);
    }

    /// fotoapparat call back
    public void whenDone(Unit unit){
        Timber.tag(TAG).d("whenDone -> saved to file");



        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCasePhotoDetectImageLabel(device, imageDeviceAbsoluteFileName, photoRequest, this));
    }

    /// fotoapparat call back
    public void whenDone(BitmapPhoto bitmapPhoto){
        Timber.tag(TAG).d("whenDone -> got a bitmap");


        Timber.tag(TAG).d("...got image ");
        Timber.tag(TAG).d("      height -> " + bitmapPhoto.bitmap.getHeight());
        Timber.tag(TAG).d("      width  -> " + bitmapPhoto.bitmap.getWidth());
        Timber.tag(TAG).d("      size (bytes) -> " + bitmapPhoto.bitmap.getByteCount());

        //generate a guid for this image
        String imageGuid = BuilderUtilities.generateGuid();

        //save this image in local device storage
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseSavePhotoToDeviceImageStorage(device, photoRequest, imageGuid, bitmapPhoto.bitmap, this));
    }

    /// cameraKitEventCallback interface
    public void callback(CameraKitImage event){
        Timber.tag(TAG).d("...callback started");
        //byte[] jpeg = event.getJpeg();

        //get the bitmap for the image
        Bitmap bitmap = event.getBitmap();


        Timber.tag(TAG).d("...got image ");
        Timber.tag(TAG).d("      height -> " + event.getBitmap().getHeight());
        Timber.tag(TAG).d("      width  -> " + event.getBitmap().getWidth());
        Timber.tag(TAG).d("      size (bytes) -> " + event.getBitmap().getByteCount());

        //generate a guid for this image
        String imageGuid = BuilderUtilities.generateGuid();

        //save this image in local device storage
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseSavePhotoToDeviceImageStorage(device, photoRequest, imageGuid, bitmap, this));
    }

    /// use case response interface
    public void imageSavedSuccess(String imageGuid){
        Timber.tag(TAG).d("saved image SUCCESS, image guid -> " + imageGuid);
        response.captureSuccess(photoRequest);
    }

    public void imageSavedFailure(String imageGuid){
        Timber.tag(TAG).d("save image FAILURE, image guid -> " + imageGuid);
        response.captureFailure(photoRequest);
    }

    ///use case response interface
    public void photoDetectImageLabelComplete(){
        Timber.tag(TAG).d("photoDetectImageLabelComplete");
        //stop the animation
        cameraView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        animation.setVisibility(View.INVISIBLE);

        response.captureSuccess(photoRequest);
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
        cameraView = null;
        button = null;
        animation=null;
        Timber.tag(TAG).d("components closed");
    }

    public interface CaptureResponse {
        void captureSuccess(PhotoRequest photoRequest);

        void captureFailure(PhotoRequest photoRequest);
    }


}
