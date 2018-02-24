/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraView;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class PhotoTakeActivity extends AppCompatActivity
    implements PhotoTakeController.Response {

    private static final String TAG = "PhotoTakeActivity";

    private PhotoTakeController controller;
    private Button photoButton;
    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_take);
        cameraView = (CameraView) findViewById(R.id.camera);
        photoButton = (Button) findViewById(R.id.photo_button);
    }

    @Override
    public void onResume() {
        super.onResume();

        controller = new PhotoTakeController(this);
        cameraView.start();

        photoButton.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("onResume");
    }


    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        cameraView.stop();
        controller.close();
        super.onPause();
    }

    public void clickPhotoButton(View view){
        Timber.tag(TAG).d("clickPhotoButton START...");
        photoButton.setVisibility(View.INVISIBLE);

        cameraView.captureImage(controller);
    }

    public void photoComplete(String imageGuid){
        Timber.tag(TAG).d("photoComplete!");
        photoButton.setVisibility(View.VISIBLE);
    }
}
