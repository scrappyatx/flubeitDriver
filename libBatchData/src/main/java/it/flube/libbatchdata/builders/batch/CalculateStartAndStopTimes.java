/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.TimestampBuilder;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/25/2018
 * Project : Driver
 */
public class CalculateStartAndStopTimes {
    public static final Integer DEFAULT_ROUNDING_MINUTES = 30;

    ////
    ////    Calculates the start & finish times for the batch, all service orders,
    ////    and all steps of each service order
    ////
    ////    Uses the batch.getExpectedStartTime() as the starting time, and
    ////    use the duration in minutes of each step to calculate start & finish times.
    ////
    ////    roundingMinutes specifies the nearest minutes to round the batch.setExpectedFinishTime()
    ////
    ////    e.g., roundingMinutes = 15 will always set a expected finishTime on nearest 15 minute interval
    ////

    public static void calculateStartAndStopTimes(BatchHolder batchHolder){
        doCalc(batchHolder, DEFAULT_ROUNDING_MINUTES);
    }

    public static void calculateStartAndStopTimes(BatchHolder batchHolder, Integer roundingMinutes){
        doCalc(batchHolder, roundingMinutes);
    }

    private static void doCalc(BatchHolder batchHolder, Integer roundingMinutes){
        //1. get the expectedStartTime from the batch
        Date startTime = batchHolder.getBatch().getExpectedStartTime();
        batchHolder.getBatchDetail().setExpectedStartTime(startTime);

        //2. loop through all service orders by sequence
        ArrayList<ServiceOrder> serviceOrderList = getServiceOrdersSortedBySequence(batchHolder);
        for ( ServiceOrder thisOrder : serviceOrderList ){

            //calculate the start & finish times for this order, given the start time
            CalcServiceOrderTimes(batchHolder, thisOrder.getGuid(), startTime);

            //set the start time for the next order to the finish time of the last one
            startTime = batchHolder.getBatch().getExpectedFinishTime();
        }

        //3. round up the expected finish time to the closest rounding minutes
        batchHolder.getBatch().setExpectedFinishTime(roundBatchFinishTime(batchHolder.getBatch().getExpectedFinishTime(), roundingMinutes));
        batchHolder.getBatchDetail().setExpectedFinishTime(batchHolder.getBatch().getExpectedFinishTime());
    }

    private static Date roundBatchFinishTime(Date batchFinishTime, Integer roundingMinutes){
        return batchFinishTime;
    }

    private static void CalcServiceOrderTimes(BatchHolder batchHolder, String serviceOrderGuid, Date serviceOrderStartTime){
        ///loop through all steps in this order, calcualte the startTime and endTime of each step
        ///at the end, set the endTime for the batch

        /// initialize stepStartTime and stepStopTime
        Date stepStartTime = serviceOrderStartTime;
        Date stepFinishTime = serviceOrderStartTime;

        //set the start time of the service order to the stepStartTime
        batchHolder.getServiceOrders().get(serviceOrderGuid).setStartTime(new TimestampBuilder.Builder().scheduledTime(serviceOrderStartTime).build());

        //iterate through all orders in sequence
        ArrayList<OrderStepInterface> stepList = getOrderListSortedBySequence(batchHolder, serviceOrderGuid);
        for (OrderStepInterface thisStep : stepList ){

            //step finish time is the stepStartTime + the how long it takes to complete the step
            stepFinishTime = BuilderUtilities.addMinutesToDate(stepStartTime, thisStep.getDurationMinutes());

            //set the start time of the step to the value in stepStartTime
            batchHolder.getSteps().get(thisStep.getGuid()).setStartTime(new TimestampBuilder.Builder().scheduledTime(serviceOrderStartTime).build());

            //set the finish time of the step to the value in stepFinishTime
            batchHolder.getSteps().get(thisStep.getGuid()).setFinishTime(new TimestampBuilder.Builder().scheduledTime(stepFinishTime).build());

            //set the start time for the next step to the finish time of this one
            stepStartTime = stepFinishTime;

        }

        //set the finish time of the service order to the stepFinishTime
        batchHolder.getServiceOrders().get(serviceOrderGuid).setFinishTime(new TimestampBuilder.Builder().scheduledTime(stepFinishTime).build());

        //set the finish time of the batch to the stepFinishTime
        batchHolder.getBatch().setExpectedFinishTime(stepFinishTime);
    }



    private static ArrayList<ServiceOrder> getServiceOrdersSortedBySequence(BatchHolder batchHolder){
        /// build a list of service orders sorted by sequence
        /// uses a TreeMap with an integey key, the key will be the serviceOrder sequence.
        /// since TreeMap sorts by key, this will give us a list of service orders sorted by sequence

        Map<Integer, ServiceOrder> orderMap = new TreeMap<Integer, ServiceOrder>();
        ArrayList<ServiceOrder> serviceOrderList = new ArrayList<ServiceOrder>();

        //loop through all service orders, add the service order sequence as the index
        for (Map.Entry<String, ServiceOrder> entry : batchHolder.getServiceOrders().entrySet()){
            orderMap.put(entry.getValue().getSequence(), entry.getValue());
        }
        //put results into arraylist, will be sorted by sequence ascending in the treemap
        for (Map.Entry<Integer, ServiceOrder> entry : orderMap.entrySet()){

            serviceOrderList.add(entry.getValue());
        }

        return serviceOrderList;
    }



    private static ArrayList<OrderStepInterface> getOrderListSortedBySequence(BatchHolder batchHolder, String serviceOrderGuid){
        /// build a list of steps in a service order ordered by sequence
        ///
        ///
        Map<Integer, OrderStepInterface> stepMap = new TreeMap<Integer, OrderStepInterface>();
        ArrayList<OrderStepInterface> stepList = new ArrayList<OrderStepInterface>();

        //loop through all steps, add only the steps that belong to the desired serviceOrderGuid
        for (Map.Entry<String, OrderStepInterface> entry : batchHolder.getSteps().entrySet()){
            if (entry.getValue().getServiceOrderGuid().equals(serviceOrderGuid)){
                stepMap.put(entry.getValue().getSequence(), entry.getValue());
            }
        }

        //put results into arraylist, will be sorted by sequence asending in the treemap
        for (Map.Entry<Integer, OrderStepInterface> entry : stepMap.entrySet()){
            stepList.add(entry.getValue());
        }

        return stepList;
    }
}
