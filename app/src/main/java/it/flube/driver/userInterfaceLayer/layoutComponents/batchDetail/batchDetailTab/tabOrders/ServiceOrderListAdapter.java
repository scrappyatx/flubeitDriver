/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabOrders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.layoutComponents.LayoutComponentUtilities;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class ServiceOrderListAdapter extends RecyclerView.Adapter<ServiceOrderListAdapter.OrderViewHolder> {
    private static final String TAG = "ServiceOrderListAdapter";


    private ArrayList<ServiceOrder> ordersList;
    private Response response;


    public ServiceOrderListAdapter(Response response ) {
        this.response = response;
        ordersList = new ArrayList<ServiceOrder>();

    }

    public void updateList(ArrayList<ServiceOrder> ordersList) {
        Timber.tag(TAG).d("starting update list...");
        Timber.tag(TAG).d("    parameter ordersList --> " + ordersList.size() + " items");
        this.ordersList.clear();
        this.ordersList.addAll(ordersList);
        Timber.tag(TAG).d("    class ordersList     --> " + ordersList.size() + " items");
        notifyDataSetChanged();
        Timber.tag(TAG).d("updateList  --> " + this.ordersList.size() + " items");
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_order_list_row_item, parent, false);
        Timber.tag(TAG).d("created new OrderViewHolder");
        return new OrderViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        ServiceOrder order = ordersList.get(position);
        holder.bindOrder(order);
        Timber.tag(TAG).d("Binding order " + order.getGuid() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount --> " + ordersList.size() + " items");
        return ordersList.size();
    }

    public void close(){

    }


    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView sequence;
        private TextView title;
        //private TextView description;
        private TextView startBy;
        private TextView completeBy;

        private ServiceOrder order;

        private Response response;

        public OrderViewHolder(View v, Response response) {
            super(v);
            this.response = response;

            sequence = (TextView) v.findViewById(R.id.service_order_list_sequence);
            title = (TextView) v.findViewById(R.id.service_order_list_title);
            //description = (TextView) v.findViewById(R.id.service_order_list_description);
            startBy = (TextView) v.findViewById(R.id.service_order_list_start_by_value);
            completeBy = (TextView) v.findViewById(R.id.service_order_list_complete_by_value);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.orderSelected(order);
            Timber.tag(TAG).d("order selected --> " + order.getGuid());
        }

        public void bindOrder(ServiceOrder order) {
            this.order = order;

            sequence.setText(order.getSequence().toString());
            title.setText(order.getTitle());
            //description.setText(order.getDescription());
            startBy.setText(LayoutComponentUtilities.getStartTime(BuilderUtilities.convertMillisToDate(order.getStartTime().getScheduledTime())));
            completeBy.setText(LayoutComponentUtilities.getStartTime(BuilderUtilities.convertMillisToDate(order.getFinishTime().getScheduledTime())));

            Timber.tag(TAG).d("bindOrder --->");
            Timber.tag(TAG).d("---> guid               : " + order.getGuid());
            Timber.tag(TAG).d("---> title              : " + order.getTitle());
            //Timber.tag(TAG).d("---> Description        : " + order.getDescription());
            Timber.tag(TAG).d("---> StartBy            : " + order.getStartTime().getScheduledTime().toString());
            Timber.tag(TAG).d("---> CompleteBy         : " + order.getFinishTime().getScheduledTime().toString());


        }
    }

    public interface Response {
        void orderSelected(ServiceOrder serviceOrder);
    }


}
