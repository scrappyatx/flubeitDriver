/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 10/27/2017
 * Project : Driver
 */

public class PhotoRequestListAdapter extends RecyclerView.Adapter<PhotoRequestListAdapter.PhotoRequestViewHolder> {
    private static final String TAG = "PhotoRequestListAdapter";
    private static final int THUMBNAIL_WIDTH = 80;
    private static final int THUMBNAIL_HEIGHT = 60;

    private Context activityContext;
    private ArrayList<PhotoRequest> photoRequestList;
    private Response response;


    public PhotoRequestListAdapter(Context activityContext, Response response ) {
        this.response = response;
        this.activityContext = activityContext;
        photoRequestList = new ArrayList<PhotoRequest>();

    }

    public void updateList(ArrayList<PhotoRequest> photoRequestList) {
        Timber.tag(TAG).d("starting update list...");
        Timber.tag(TAG).d("    parameter ordersList --> " + photoRequestList.size() + " items");
        this.photoRequestList.clear();

        Timber.tag(TAG).d("    class ordersList before adding --> " + this.photoRequestList.size() + " items");
        this.photoRequestList.addAll(photoRequestList);

        notifyDataSetChanged();
        Timber.tag(TAG).d("after addAll  --> " + this.photoRequestList.size() + " items");
    }

    public ArrayList<PhotoRequest> getPhotoRequestList(){
        Timber.tag(TAG).d("getPhotoRequestList");
        return photoRequestList;
    }

    @Override
    public PhotoRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_detail_photo_request_row_item, parent, false);
        Timber.tag(TAG).d("PhotoRequestViewHolder");
        return new PhotoRequestViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(PhotoRequestViewHolder holder, int position) {
        PhotoRequest photoRequest = photoRequestList.get(position);
        holder.bindPhotoRequest(position, photoRequest);
        Timber.tag(TAG).d("Binding photoRequest " + photoRequest.getGuid() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount --> " + photoRequestList.size() + " items");
        return photoRequestList.size();
    }

    public void close(){
        activityContext = null;
    }


    public class PhotoRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView sequence;
        private TextView title;
        private TextView description;
        private TextView statusIconText;
        private ImageView thumbnailImage;

        private PhotoRequest photoRequest;

        private Response response;

        public PhotoRequestViewHolder(View v, Response response) {
            super(v);
            this.response = response;

            thumbnailImage = (ImageView) v.findViewById(R.id.image_thumbnail);
            sequence = (TextView) v.findViewById(R.id.photo_list_sequence);
            title = (TextView) v.findViewById(R.id.photo_list_title);
            description = (TextView) v.findViewById(R.id.photo_list_description);
            statusIconText = (TextView) v.findViewById(R.id.photo_list_photoStatus);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.photoRequestSelected(photoRequest);
            Timber.tag(TAG).d("photoRequest selected --> " + photoRequest.getGuid());
        }

        public void bindPhotoRequest(Integer position, PhotoRequest photoRequest) {
            this.photoRequest = photoRequest;

            sequence.setText(photoRequest.getSequence().toString());
            title.setText(photoRequest.getTitle());

            description.setText(photoRequest.getDescription());
            description.setVisibility(View.GONE);

            statusIconText.setText(photoRequest.getStatusIconText().get(photoRequest.getStatus().toString()));

            Timber.tag(TAG).d("bindPhotoRequest --->");
            Timber.tag(TAG).d("---> guid               : " + photoRequest.getGuid());
            Timber.tag(TAG).d("---> title              : " + photoRequest.getTitle());
            Timber.tag(TAG).d("---> Description        : " + photoRequest.getDescription());
            Timber.tag(TAG).d("---> Status             : " + photoRequest.getStatus().toString());
            Timber.tag(TAG).d("---> StatusIconText     : " + photoRequest.getStatusIconText().get(photoRequest.getStatus().toString()));

            Timber.tag(TAG).d("---> thumbnail Width    : " + thumbnailImage.getWidth());
            Timber.tag(TAG).d("---> thumbnail Height   : " + thumbnailImage.getHeight());

            //set thumbnail image
            if (photoRequest.getHasDeviceFile()) {
                //use device image
                //thumbnailImage.setImageBitmap(BitmapFactory.decodeFile(photoRequest.getDeviceAbsoluteFileName()));
                //Picasso.with(activityContext)

                //// GLIDE TEST - comment this out
                String fileName = "file://" + photoRequest.getDeviceAbsoluteFileName();
                Timber.tag(TAG).d("filename -> " + fileName);
                Picasso.get()
                        //.load(new File(photoRequest.getDeviceAbsoluteFileName()))
                        .load(fileName)
                       //.load(R.drawable.placeholder_image)
                        //.placeholder(R.drawable.placeholder_image)
                       //.resize(THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT)
                        .fit()
                        .centerInside()
                        .into(thumbnailImage);
                ////
                ///Glide.with(activityContext)
                ///        .load(new File(photoRequest.getDeviceAbsoluteFileName()))
                ///        .into(thumbnailImage);

                thumbnailImage.setVisibility(View.VISIBLE);
                Timber.tag(TAG).d("---> Thumbnail Image (deviceFile)    : " + photoRequest.getDeviceAbsoluteFileName());

            } else if (photoRequest.getHasNoAttemptImage()) {
                //use no attempt image
                //Picasso.with(activityContext)
                //Picasso.get()
                //        .load(photoRequest.getNoAttemptImageUrl())
                        //.load(R.drawable.no_attempts_placeholder)
                        //.placeholder(R.drawable.no_attempts_placeholder)
                //        .resize(THUMBNAIL_WIDTH,THUMBNAIL_WIDTH)
                //        .into(thumbnailImage);
                thumbnailImage.setVisibility(View.INVISIBLE);
                Timber.tag(TAG).d("---> Thumbnail Image (no attempt image)    : " + photoRequest.getNoAttemptImageUrl());

            } else {
                //use default image no attempt placeholder
                //Picasso.with(activityContext)
                //Picasso.get()
                //        .load(R.drawable.no_attempts_placeholder)
                //        .resize(THUMBNAIL_WIDTH, THUMBNAIL_WIDTH)
                //        .into(thumbnailImage);
                thumbnailImage.setVisibility(View.INVISIBLE);
                Timber.tag(TAG).d("---> Thumbnail Image (default)    : " + photoRequest.getNoAttemptImageUrl());
            }

        }
    }

    public interface Response {
        void photoRequestSelected(PhotoRequest photoRequest);
    }


}
