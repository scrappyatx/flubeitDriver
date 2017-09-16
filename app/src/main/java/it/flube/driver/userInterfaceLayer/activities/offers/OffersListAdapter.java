/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers;

/**
 * Created by Bryan on 3/25/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.Offer;
import timber.log.Timber;

import java.util.ArrayList;



public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.OfferViewHolder> {
    private static final String TAG = "OffersListAdapter";

    private ArrayList<Offer> offersList;
    private OffersListAdapter.Response response;


    public OffersListAdapter(OffersListAdapter.Response response ) {
        offersList = new ArrayList<Offer>();
        this.response = response;
    }

    public void updateList(ArrayList<Offer> offersList) {
        this.offersList = offersList;
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList");
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offersview_item_row, parent, false);
        Timber.tag(TAG).d("created new OfferViewHolder");
        return new OfferViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, int position) {
        Offer itemOffer = offersList.get(position);
        holder.bindOffer(itemOffer);
        Timber.tag(TAG).d("Binding offer " + itemOffer.getGUID() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount");
        return offersList.size();
    }


    public static class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView serviceProviderImage;
        private TextView offerTime;
        private TextView offerDuration;
        private TextView offerDescription;
        private TextView baseEarnings;
        private TextView extraEarnings;
        private ImageView distanceImage;
        private TextView distance;

        private Offer offer;

        private OffersListAdapter.Response response;


        public OfferViewHolder(View v, OffersListAdapter.Response response) {
            super(v);
            this.response = response;

            serviceProviderImage = (ImageView) v.findViewById(R.id.serviceProvider_image);
            offerTime = (TextView) v.findViewById(R.id.item_time);
            offerDuration = (TextView) v.findViewById(R.id.item_duration);
            offerDescription = (TextView) v.findViewById(R.id.item_description);
            baseEarnings = (TextView) v.findViewById(R.id.item_earnings);
            extraEarnings = (TextView) v.findViewById(R.id.item_earnings_extra);
            distance = (TextView) v.findViewById(R.id.item_distance);
            distanceImage = (ImageView) v.findViewById(R.id.distance_image);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.offerSelected(offer);
            Timber.tag(TAG).d("offer selected --> " + offer.getGUID());
        }

        public void bindOffer(Offer offer) {
            this.offer = offer;

            offerDescription.setText(offer.getServiceDescription());
            offerTime.setText(offer.getOfferTime());
            offerDuration.setText(offer.getOfferDuration());
            baseEarnings.setText(offer.getEstimatedEarnings());
            extraEarnings.setText(offer.getEstimatedEarningsExtra());

            Timber.tag(TAG).d("bindOffer :");
            Timber.tag(TAG).d("---> OID:        : " + offer.getGUID());
            Timber.tag(TAG).d("---> Description : " + offer.getServiceDescription());
            Timber.tag(TAG).d("---> Time        : " + offer.getOfferTime());
            Timber.tag(TAG).d("---> Duration    : " + offer.getOfferDuration());
            Timber.tag(TAG).d("---> Base Earnings : " + offer.getEstimatedEarnings());
            Timber.tag(TAG).d("---> Extra Earnings : " + offer.getEstimatedEarningsExtra());

        }

    }

    public interface Response {
        void offerSelected(Offer offer);
    }

}
