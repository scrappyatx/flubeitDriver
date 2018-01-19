/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.BatchViewHolder> {

    private static final String TAG = "BatchListAdapter";

    private Context activityContext;
    private ArrayList<Batch> batchList;
    private BatchListAdapter.Response response;

    public BatchListAdapter(Context activityContext, BatchListAdapter.Response response ) {
        this.activityContext = activityContext;
        this.response = response;
        batchList = new ArrayList<Batch>();
    }

    public void updateList(ArrayList<Batch> batchList) {
        this.batchList.clear();
        this.batchList.addAll(batchList);
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList");
    }

    @Override
    public BatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batchesview_item_row, parent, false);
        Timber.tag(TAG).d("created new BatchViewHolder");
        return new BatchViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(BatchListAdapter.BatchViewHolder holder, int position) {
        Batch batch = batchList.get(position);
        holder.bindBatch(batch);
        Timber.tag(TAG).d("Binding batch " + batch.getGuid() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount");
        return batchList.size();
    }

    public void close(){
        activityContext = null;
    }

    public class BatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView serviceProviderImage;
        private TextView batchTime;
        private TextView batchDuration;
        private TextView batchDescription;
        private TextView baseEarnings;
        private TextView extraEarnings;
        private ImageView distanceImage;
        private TextView distance;

        private Batch batch;

        private Response response;


        public BatchViewHolder(View v, Response response) {
            super(v);
            this.response = response;

            serviceProviderImage = (ImageView) v.findViewById(R.id.serviceProvider_image);
            batchTime = (TextView) v.findViewById(R.id.item_time);
            batchDuration = (TextView) v.findViewById(R.id.item_duration);
            batchDescription = (TextView) v.findViewById(R.id.item_description);
            baseEarnings = (TextView) v.findViewById(R.id.item_earnings);
            extraEarnings = (TextView) v.findViewById(R.id.item_earnings_extra);
            distance = (TextView) v.findViewById(R.id.item_distance);
            distanceImage = (ImageView) v.findViewById(R.id.distance_image);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.batchSelected(batch);
            Timber.tag(TAG).d("batch selected --> " + batch.getGuid());
        }

        public void bindBatch(Batch batch) {
            this.batch = batch;

            String displayDescription = batch.getTitle();
            String displayTime = batch.getDisplayTiming().getHours();
            String displayDuration = batch.getDisplayTiming().getDuration();

            //TODO do the number formatting into currency in the batch builder, then just get property here
            String displayBaseEarnings = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                    .format(batch.getPotentialEarnings().getPayRateInCents()/100);
            String displayExtraEarnings = "";
            if (batch.getPotentialEarnings().getPlusTips()) {
                displayExtraEarnings = "+ Tips";
            }
            String serviceProviderUrl = batch.getIconUrl();
            String distanceUrl = batch.getDisplayDistance().getDistanceIndicatorUrl();
            String displayDistance = batch.getDisplayDistance().getDistanceToTravel();


            batchDescription.setText(displayDescription);
            batchTime.setText(displayTime);
            batchDuration.setText(displayDuration);
            baseEarnings.setText(displayBaseEarnings);
            extraEarnings.setText(displayExtraEarnings);
            distance.setText(displayDistance);

            Picasso.with(activityContext)
                    .load(serviceProviderUrl)
                    .into(serviceProviderImage);

            Picasso.with(activityContext)
                    .load(distanceUrl)
                    .into(distanceImage);


            Timber.tag(TAG).d("bindBatch --->");
            Timber.tag(TAG).d("---> OID:               : " + batch.getGuid());
            Timber.tag(TAG).d("---> Description        : " + displayDescription);
            Timber.tag(TAG).d("---> Time               : " + displayTime);
            Timber.tag(TAG).d("---> Duration           : " + displayDuration);
            Timber.tag(TAG).d("---> Base Earnings      : " + displayBaseEarnings);
            Timber.tag(TAG).d("---> Extra Earnings     : " + displayExtraEarnings);
            Timber.tag(TAG).d("---> serviceProviderUrl : " + serviceProviderUrl);
            Timber.tag(TAG).d("---> distanceUrl        : " + distanceUrl);
            Timber.tag(TAG).d("---> distance           : " + displayDistance);

        }
    }

    public interface Response {
        void batchSelected(Batch batch);
    }

}
