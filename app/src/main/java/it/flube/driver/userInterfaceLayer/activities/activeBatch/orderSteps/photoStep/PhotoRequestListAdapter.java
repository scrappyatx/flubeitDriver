/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.PhotoRequest;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.batchItinerary.ServiceOrderListAdapter;
import timber.log.Timber;

/**
 * Created on 10/27/2017
 * Project : Driver
 */

public class PhotoRequestListAdapter extends RecyclerView.Adapter<PhotoRequestListAdapter.PhotoRequestViewHolder> {
    private static final String TAG = "ServiceOrderListAdapter";

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

        private PhotoRequest photoRequest;

        private Response response;

        public PhotoRequestViewHolder(View v, Response response) {
            super(v);
            this.response = response;

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

            sequence.setText(position.toString());
            title.setText(photoRequest.getTitle());
            description.setText(photoRequest.getDescription());
            statusIconText.setText(photoRequest.getStatusIconText().get(photoRequest.getStatus()));

            Timber.tag(TAG).d("bindPhotoRequest --->");
            Timber.tag(TAG).d("---> guid               : " + photoRequest.getGuid());
            Timber.tag(TAG).d("---> title              : " + photoRequest.getTitle());
            Timber.tag(TAG).d("---> Description        : " + photoRequest.getDescription());
            Timber.tag(TAG).d("---> Status             : " + photoRequest.getStatus().toString());
            Timber.tag(TAG).d("---> StatusIconText     : " + photoRequest.getStatusIconText().get(photoRequest.getStatus()));
        }
    }

    public interface Response {
        void photoRequestSelected(PhotoRequest photoRequest);
    }


}
