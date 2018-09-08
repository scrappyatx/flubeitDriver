/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.io.File;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.PhotoRequest;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 5/5/2018
 * Project : Driver
 */
public class PhotoRequestDetailLayoutComponents {
    public final static String TAG = "PhotoRequestDetailLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     photo_request_detail.xml
    ///

    private static final int THUMBNAIL_WIDTH = 200;
    private static final int THUMBNAIL_HEIGHT = 150;

    private TextView sequence;
    private TextView title;
    private TextView description;
    private ImageView hint;
    private ImageView actual;

    private Boolean hasPhotoHint;
    private PhotoRequest photoRequest;

    public PhotoRequestDetailLayoutComponents(AppCompatActivity activity){
        Timber.tag(TAG).d("creating component");

        sequence = (TextView) activity.findViewById(R.id.photo_request_sequence);
        title = (TextView) activity.findViewById(R.id.photo_request_title);
        description = (TextView) activity.findViewById(R.id.photo_request_description);
        hint = (ImageView) activity.findViewById(R.id.image_photo_hint);
        actual = (ImageView) activity.findViewById(R.id.image_photo_actual);

        hasPhotoHint = false;
        setGone();
        Timber.tag(TAG).d("...components created");
    }

    public void setValues(AppCompatActivity activity, PhotoRequest photoRequest ){
        this.photoRequest = photoRequest;

        sequence.setText(photoRequest.getSequence().toString());
        title.setText(photoRequest.getTitle());
        description.setText(photoRequest.getDescription());

        if (photoRequest.getHasPhotoHint()){
            hasPhotoHint = true;
            //Picasso.with(activity)

            //// GLIDE TEST
            Picasso.get()
                    .load(photoRequest.getPhotoHintUrl())
                    //.resize(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .fit()
                    .centerInside()
                    .into(hint);

            //Glide.with(activity)
            //        .load(photoRequest.getPhotoHintUrl())
            //        .into(hint);

        }

        if (photoRequest.getHasDeviceFile()) {
            //actual.setImageBitmap(BitmapFactory.decodeFile(photoRequest.getDeviceAbsoluteFileName()));
            //Picasso.with(activity)

            String fileName = "file://" + photoRequest.getDeviceAbsoluteFileName();
            Timber.tag(TAG).d("filename -> " + fileName);

            //// GLIDE TEST
            Picasso.get()
                    //.load(new File(photoRequest.getDeviceAbsoluteFileName()))
                    .load(fileName)
                    //.placeholder(R.drawable.placeholder_image)
                    //.resize(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .fit()
                    .centerInside()
                    .into(actual);

            //Glide.with(activity)
            //        .load(new File(photoRequest.getDeviceAbsoluteFileName()))
            //        .into(actual);


        } else if (photoRequest.getHasNoAttemptImage()) {

            //Picasso.with(activity)

            //// GLIDE TEST
            Picasso.get()
                    .load(photoRequest.getNoAttemptImageUrl())
                    //.placeholder(R.drawable.no_attempts_placeholder)
                    //.resize(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .fit()
                    .centerInside()
                    .into(actual);

            //Glide.with(activity)
            //        .load(photoRequest.getNoAttemptImageUrl())
            //        .into(actual);


        } else {

            //Picasso.with(activity)

            //// GLIDE TEST
            Picasso.get()
                    .load(R.drawable.no_attempts_placeholder)
                   //.resize(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .fit()
                    .centerInside()
                   .into(actual);

            //Glide.with(activity)
            //        .load(R.drawable.no_attempts_placeholder)
            //        .into(actual);
        }

        Timber.tag(TAG).d("setValues");
    }

    public PhotoRequest getPhotoRequest(){
        return this.photoRequest;
    }

    public void setVisible(){
        sequence.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);

        if (hasPhotoHint) {
            hint.setVisibility(View.VISIBLE);
        } else {
            hint.setVisibility(View.GONE);
        }
        actual.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        sequence.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        if (hasPhotoHint) {
            hint.setVisibility(View.INVISIBLE);
        } else {
            hint.setVisibility(View.GONE);
        }
        actual.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        sequence.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
        hint.setVisibility(View.GONE);
        actual.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        sequence = null;
        title = null;
        description = null;
        hint = null;
        actual = null;
        Timber.tag(TAG).d("components closed");
    }

}
