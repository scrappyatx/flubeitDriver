/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderItinerary;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.UserInterfaceUtilities;
import timber.log.Timber;

/**
 * Created on 10/25/2017
 * Project : Driver
 */

public class OrderStepListAdapter extends RecyclerView.Adapter<OrderStepListAdapter.StepViewHolder> {
    private static final String TAG = "OrderStepListAdapter";

    private Context activityContext;
    private Integer activeSequence;
    private ArrayList<OrderStepInterface> stepList;
    private Response response;


    public OrderStepListAdapter(Context activityContext, Response response ) {
        this.response = response;
        this.activityContext = activityContext;
        stepList = new ArrayList<OrderStepInterface>();

    }

    public void updateList(ArrayList<OrderStepInterface> stepList) {
        Timber.tag(TAG).d("starting update list...");
        Timber.tag(TAG).d("    parameter stepList --> " + stepList.size() + " items");
        this.stepList.clear();
        this.stepList.addAll(stepList);
        Timber.tag(TAG).d("    class stepList     --> " + this.stepList.size() + " items");
        notifyDataSetChanged();
        Timber.tag(TAG).d("update class stepList  --> " + this.stepList.size() + " items");
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_step_list_row_item, parent, false);
        Timber.tag(TAG).d("created new StepViewHolder");
        return new StepViewHolder(inflatedView, response);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        OrderStepInterface step = stepList.get(position);
        holder.bindOrder(step);
        Timber.tag(TAG).d("Binding step " + step.getGuid() + " to position " + Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        Timber.tag(TAG).d("getItemCount --> " + stepList.size() + " items");
        return stepList.size();
    }

    public void close(){
        activityContext = null;
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView sequence;
        private TextView title;
        private TextView description;
        private TextView completeByCaption;
        private TextView completeByValue;
        private TextView stepType;

        private OrderStepInterface orderStep;

        private Response response;

        public StepViewHolder(View v, Response response) {
            super(v);
            this.response = response;

            sequence = (TextView) v.findViewById(R.id.step_list_sequence);
            title = (TextView) v.findViewById(R.id.step_list_title);
            description = (TextView) v.findViewById(R.id.step_list_description);
            completeByCaption = (TextView) v.findViewById(R.id.step_list_complete_by_caption);
            completeByValue = (TextView) v.findViewById(R.id.step_list_complete_by_value);
            stepType = (TextView) v.findViewById(R.id.step_list_step_type);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            response.stepSelected(orderStep);
            Timber.tag(TAG).d("step selected --> " + orderStep.getGuid());
        }

        public void bindOrder(OrderStepInterface orderStep) {
            this.orderStep = orderStep;

            Timber.tag(TAG).d("bindStep --->");
            Timber.tag(TAG).d("---> sequence           : " + orderStep.getSequence());
            Timber.tag(TAG).d("---> guid               : " + orderStep.getGuid());
            Timber.tag(TAG).d("---> title              : " + orderStep.getTitle());
            Timber.tag(TAG).d("---> Description        : " + orderStep.getDescription());
            Timber.tag(TAG).d("---> CompleteBy         : " + orderStep.getFinishTime().getScheduledTime().toString());
            Timber.tag(TAG).d("---> StepType           : " + orderStep.getTaskType().toString());

            sequence.setText(orderStep.getSequence().toString());
            title.setText(orderStep.getTitle());
            description.setText(orderStep.getDescription());
            String caption = "Due by :";
            completeByCaption.setText(caption);
            completeByValue.setText(orderStep.getFinishTime().getScheduledTime().toString());
            stepType.setText(UserInterfaceUtilities.getStepTaskTypeIcon(orderStep.getTaskType()));

            //if (orderStep.getSequence() == activeSequence) {
            //    stepType.setBackground(ResourcesCompat.getDrawable(activityContext.getResources(), R.drawable.step_circle_active, null));
            //} else {
            //    stepType.setBackground(ResourcesCompat.getDrawable(activityContext.getResources(), R.drawable.step_circle_active, null));
            //}
        }
    }

    public interface Response {
        void stepSelected(OrderStepInterface orderStep);
    }


}
