/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.testOffers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class TestOfferOptionsListAdapter extends RecyclerView.Adapter<TestOfferOptionsListAdapter.OptionViewHolder> {
    private static final String TAG = "TestOfferOptionsListAdapter";

    private Context activityContext;
    private ArrayList<TestOfferOption> optionsList;
    private TestOfferOptionsListAdapter.Response response;


    public TestOfferOptionsListAdapter(Context activityContext, TestOfferOptionsListAdapter.Response response ) {
        this.response = response;
        this.activityContext = activityContext;
        optionsList = new ArrayList<TestOfferOption>();

    }

    public void updateList(ArrayList<TestOfferOption> optionsList) {
        Timber.tag(TAG).d("starting update list...");
        Timber.tag(TAG).d("    parameter optionsList --> " + optionsList.size() + " items");
        this.optionsList.clear();
        this.optionsList.addAll(optionsList);
        Timber.tag(TAG).d("    class offersList     --> " + optionsList.size() + " items");
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList  --> " + this.optionsList.size() + " items");
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_offer_option_row, parent, false);
        Timber.tag(TAG).d("created new OptionViewHolder");
        return new OptionViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {
        TestOfferOption option = optionsList.get(position);
        holder.bindOption(option);
        Timber.tag(TAG).d("Binding option " + option.getOptionName() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount --> " + optionsList.size() + " items");
        return optionsList.size();
    }

    public void close(){
        activityContext = null;
    }


    public class OptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView optionName;
        private TestOfferOption option;
        private TestOfferOptionsListAdapter.Response response;


        public OptionViewHolder(View v, Response response) {
            super(v);
            this.response = response;

            optionName = (TextView) v.findViewById(R.id.test_option_name);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.optionSelected(option);
            Timber.tag(TAG).d("option selected --> " + option.getOptionName());
        }

        public void bindOption(TestOfferOption option) {
            this.option = option;
            Timber.tag(TAG).d("option -> " + option.getOptionName());

            optionName.setText(option.getOptionName());

            Timber.tag(TAG).d("bind Option --->");
            Timber.tag(TAG).d("---> option name  : " + option.getOptionName());
            Timber.tag(TAG).d("---> class name   : " + option.getClassName());
        }
    }

    public interface Response {
        void optionSelected(TestOfferOption option);
    }

}
