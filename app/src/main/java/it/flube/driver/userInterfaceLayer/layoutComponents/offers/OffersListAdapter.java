/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.offers;

/**
 * Created by Bryan on 3/25/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.layoutComponents.LayoutComponentUtilities;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.OfferViewHolder> {
    private static final String TAG = "OffersListAdapter";

    private Context activityContext;
    private ArrayList<Batch> offersList;
    private OffersListAdapter.Response response;


    public OffersListAdapter(Context activityContext, OffersListAdapter.Response response ) {
        this.response = response;
        this.activityContext = activityContext;
        offersList = new ArrayList<Batch>();

    }

    public void updateList(ArrayList<Batch> offersList) {
        Timber.tag(TAG).d("starting update list...");
        Timber.tag(TAG).d("    parameter offersList --> " + offersList.size() + " items");
        this.offersList.clear();
        this.offersList.addAll(offersList);
        Timber.tag(TAG).d("    class offersList     --> " + offersList.size() + " items");
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList  --> " + this.offersList.size() + " items");
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
        Batch itemOffer = offersList.get(position);
        holder.bindOffer(itemOffer);
        Timber.tag(TAG).d("Binding offer " + itemOffer.getGuid() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount --> " + offersList.size() + " items");
        return offersList.size();
    }

    public void close(){
        activityContext = null;
    }


    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView serviceProviderImage;
        private TextView offerTime;
        private TextView offerDuration;
        private TextView offerDescription;
        private TextView baseEarnings;
        private TextView extraEarnings;
        private ImageView distanceImage;
        private TextView distance;

        private Batch offer;

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
            Timber.tag(TAG).d("offer selected --> " + offer.getGuid());
        }

        public void bindOffer(Batch offer) {
            this.offer = offer;
            Timber.tag(TAG).d("offer guid -> " + offer.getGuid());

            String displayDescription = offer.getTitle();
            String displayTime = LayoutComponentUtilities.getDisplayTiming(offer.getExpectedStartTime(), offer.getExpectedFinishTime());
            String displayDuration = LayoutComponentUtilities.getDisplayDuration(offer.getExpectedStartTime(), offer.getExpectedFinishTime());
            //TODO do the number formatting into currency in the batch builder, then just get property here
            String displayBaseEarnings = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                    .format(offer.getPotentialEarnings().getPayRateInCents()/100);
            String displayExtraEarnings = "";
            if (offer.getPotentialEarnings().getPlusTips()) {
                displayExtraEarnings = "+ Tips";
            }
            String serviceProviderUrl = offer.getIconUrl();
            String distanceUrl = offer.getDisplayDistance().getDistanceIndicatorUrl();
            String displayDistance = offer.getDisplayDistance().getDistanceToTravel();


            offerDescription.setText(displayDescription);
            offerTime.setText(displayTime);
            offerDuration.setText(displayDuration);
            baseEarnings.setText(displayBaseEarnings);
            extraEarnings.setText(displayExtraEarnings);
            distance.setText(displayDistance);

            //Picasso.with(activityContext)
            Picasso.get()
                    .load(serviceProviderUrl)
                    .into(serviceProviderImage);

            //Picasso.with(activityContext)
            Picasso.get()
                    .load(distanceUrl)
                    .into(distanceImage);


            Timber.tag(TAG).d("bindOffer --->");
            Timber.tag(TAG).d("---> OID:               : " + offer.getGuid());
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
        void offerSelected(Batch batch);
    }

}
