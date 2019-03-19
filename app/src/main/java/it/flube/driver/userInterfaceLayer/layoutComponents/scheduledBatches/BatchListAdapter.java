/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.scheduledBatches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.layoutComponents.LayoutComponentUtilities;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OfferRowHolder;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.BatchViewHolder> {

    private static final String TAG = "BatchListAdapter";

    private Context activityContext;
    private ArrayList<OfferRowHolder> batchList;
    private BatchListAdapter.Response response;

    public BatchListAdapter(Context activityContext, BatchListAdapter.Response response ) {
        this.activityContext = activityContext;
        this.response = response;
        batchList = new ArrayList<OfferRowHolder>();
    }

    public void updateList(ArrayList<OfferRowHolder> batchList) {
        this.batchList.clear();
        this.batchList.addAll(batchList);
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList");
    }

    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batches_item_row_with_header, parent, false);
        Timber.tag(TAG).d("created new BatchViewHolder");
        return new BatchViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(BatchListAdapter.BatchViewHolder holder, int position) {
        OfferRowHolder batch = batchList.get(position);
        holder.bindBatch(batch);
        Timber.tag(TAG).d("Binding batch " + batch.getBatch().getGuid() + " to position " + Integer.toString(position));
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
        private TextView batchDate;
        private TextView batchTime;
        private TextView batchDuration;
        private TextView batchDescription;
        private TextView baseEarnings;
        private TextView extraEarnings;
        //private ImageView distanceImage;
        private TextView distance;

        private Batch batch;
        private OfferRowHolder offerRowHolder;

        private ConstraintLayout offerHeader;
        private TextView displayHeader;

        private Response response;


        public BatchViewHolder(View v, Response response) {
            super(v);
            this.response = response;

            serviceProviderImage = (ImageView) v.findViewById(R.id.serviceProvider_image);
            batchDate = (TextView) v.findViewById(R.id.item_date);
            batchTime = (TextView) v.findViewById(R.id.item_time);
            batchDuration = (TextView) v.findViewById(R.id.item_duration);
            batchDescription = (TextView) v.findViewById(R.id.item_description);
            baseEarnings = (TextView) v.findViewById(R.id.item_earnings);
            extraEarnings = (TextView) v.findViewById(R.id.item_earnings_extra);
            distance = (TextView) v.findViewById(R.id.item_distance);
            //distanceImage = (ImageView) v.findViewById(R.id.distance_image);

            offerHeader = (ConstraintLayout) v.findViewById(R.id.offers_item_row_header);
            displayHeader = (TextView) v.findViewById(R.id.date_header);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.batchSelected(batch);
            Timber.tag(TAG).d("batch selected --> " + batch.getGuid());
        }

        public void bindBatch(OfferRowHolder offerRowHolder) {
            this.batch = offerRowHolder.getBatch();
            this.offerRowHolder = offerRowHolder;

            //set display header
            displayHeader.setText(offerRowHolder.getDisplayHeader());

            if (offerRowHolder.getShowHeader()){
                offerHeader.setVisibility(View.VISIBLE);
                displayHeader.setVisibility(View.VISIBLE);
            } else {
                offerHeader.setVisibility(View.GONE);
                displayHeader.setVisibility(View.GONE);
            }

            String displayDescription = batch.getTitle();

            String displayDate = LayoutComponentUtilities.getDisplayDate(activityContext, BuilderUtilities.convertMillisToDate(batch.getExpectedStartTime()));

            String displayTime = LayoutComponentUtilities.getStartTime(BuilderUtilities.convertMillisToDate(batch.getExpectedStartTime()));

            String displayDuration = LayoutComponentUtilities.getDisplayDuration(BuilderUtilities.convertMillisToDate(batch.getExpectedStartTime()), BuilderUtilities.convertMillisToDate(batch.getExpectedFinishTime()));

            //TODO do the number formatting into currency in the batch builder, then just get property here
            String displayBaseEarnings = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                    .format(batch.getPotentialEarnings().getPayRateInCents()/100);
            String displayExtraEarnings = "";
            if (batch.getPotentialEarnings().getPlusTips()) {
                displayExtraEarnings = "+ Tips";
            }
            String serviceProviderUrl = batch.getIconUrl();
            //String distanceUrl = batch.getDisplayDistance().getDistanceIndicatorUrl();
            String displayDistance = batch.getDisplayDistance().getDistanceToTravel();


            batchDescription.setText(displayDescription);
            batchDate.setText(displayDate);
            batchTime.setText(displayTime);
            batchDuration.setText(displayDuration);
            baseEarnings.setText(displayBaseEarnings);
            extraEarnings.setText(displayExtraEarnings);
            distance.setText(displayDistance);

            //don't display service provider image
            serviceProviderImage.setVisibility(View.INVISIBLE);
            //Picasso.get()
            //        .load(serviceProviderUrl)
            //        .into(serviceProviderImage);

            //Picasso.with(activityContext)
            //Picasso.get()
             //       .load(distanceUrl)
             //       .into(distanceImage);


            Timber.tag(TAG).d("bindBatch --->");
            Timber.tag(TAG).d("---> OID:               : " + batch.getGuid());
            Timber.tag(TAG).d("---> Description        : " + displayDescription);
            Timber.tag(TAG).d("---> Time               : " + displayTime);
            Timber.tag(TAG).d("---> Duration           : " + displayDuration);
            Timber.tag(TAG).d("---> Base Earnings      : " + displayBaseEarnings);
            Timber.tag(TAG).d("---> Extra Earnings     : " + displayExtraEarnings);
            Timber.tag(TAG).d("---> serviceProviderUrl : " + serviceProviderUrl);
            //Timber.tag(TAG).d("---> distanceUrl        : " + distanceUrl);
            Timber.tag(TAG).d("---> distance           : " + displayDistance);

        }
    }

    public interface Response {
        void batchSelected(Batch batch);
    }

}
