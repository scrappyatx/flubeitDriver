/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.BatchViewHolder> {

    private static final String TAG = "BatchListAdapter";

    private ArrayList<BatchCloudDB> batchList;
    private BatchListAdapter.Response response;

    public BatchListAdapter(BatchListAdapter.Response response ) {
        batchList = new ArrayList<BatchCloudDB>();
        this.response = response;
    }

    public void updateList(ArrayList<BatchCloudDB> batchList) {
        this.batchList = batchList;
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList");
    }

    @Override
    public BatchListAdapter.BatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batchesview_item_row, parent, false);
        Timber.tag(TAG).d("created new BatchViewHolder");
        return new BatchListAdapter.BatchViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(BatchListAdapter.BatchViewHolder holder, int position) {
        BatchCloudDB batch = batchList.get(position);
        holder.bindBatch(batch);
        Timber.tag(TAG).d("Binding batch " + batch.getOrderOID() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount");
        return batchList.size();
    }

    public static class BatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView description;
        private TextView pickupStreet;
        private TextView pickupCityStateZip;
        private TextView dropoffStreet;
        private TextView dropOffCityStateZip;

        private BatchCloudDB batch;
        private BatchListAdapter.Response response;


        public BatchViewHolder(View v, BatchListAdapter.Response response) {
            super(v);
            this.response = response;

            description = (TextView) v.findViewById(R.id.batch_row_description);
            pickupStreet = (TextView) v.findViewById(R.id.batch_row_pickup_street1);
            pickupCityStateZip = (TextView) v.findViewById(R.id.batch_row_pickup_city_state_zip);
            dropoffStreet = (TextView) v.findViewById(R.id.batch_row_return_street1);
            dropOffCityStateZip = (TextView) v.findViewById(R.id.batch_row_return_city_state_zip);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.batchSelected(batch);
            Timber.tag(TAG).d("batch selected --> " + batch.getOrderOID());
        }

        public void bindBatch(BatchCloudDB batch) {
            this.batch = batch;

            description.setText(batch.getOrderOID());
            pickupStreet.setText(batch.getPickupLocation().getStreet1());

            String pickupCSZ = batch.getPickupLocation().getCity() + ", " + batch.getPickupLocation().getState() + " " + batch.getPickupLocation().getZip();
            pickupCityStateZip.setText(pickupCSZ);

            dropoffStreet.setText(batch.getReturnLocation().getStreet1());

            String dropoffCSZ = batch.getPickupLocation().getCity() + ", " + batch.getPickupLocation().getState() + " " + batch.getPickupLocation().getZip();
            dropOffCityStateZip.setText(dropoffCSZ);

            Timber.tag(TAG).d("bindBatch :");
            Timber.tag(TAG).d("---> Description    : " + batch.getOrderOID());
            Timber.tag(TAG).d("---> Pickup Street  : " + batch.getPickupLocation().getStreet1());
            Timber.tag(TAG).d("---> Pickup CSZ     : " + pickupCSZ);
            Timber.tag(TAG).d("---> Dropoff Street : " + batch.getReturnLocation().getStreet1());
            Timber.tag(TAG).d("---> Dropoff CSZ    : " + dropoffCSZ);

        }
    }

    public interface Response {
        void batchSelected(BatchCloudDB batch);
    }

}
