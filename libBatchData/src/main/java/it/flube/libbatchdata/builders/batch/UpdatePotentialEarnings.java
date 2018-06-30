/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.batch.BatchHolder;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class UpdatePotentialEarnings {

    public static void update(BatchHolder batchHolder, Integer payRateInCents){
        updatePayRateInCents(batchHolder, payRateInCents);
    }

    public static void update(BatchHolder batchHolder,PotentialEarnings.EarningsType earningsType){
        updateEarningsType(batchHolder, earningsType);
    }

    public static void update(BatchHolder batchHolder, Boolean plusTips){
        updatePlusTips(batchHolder, plusTips);
    }

    public static void update(BatchHolder batchHolder, Integer payRateInCents, PotentialEarnings.EarningsType earningsType){
        updatePayRateInCents(batchHolder, payRateInCents);
        updateEarningsType(batchHolder, earningsType);
    }

    public static void update(BatchHolder batchHolder, Integer payRateInCents, Boolean plusTips){
        updatePayRateInCents(batchHolder, payRateInCents);
        updatePlusTips(batchHolder, plusTips);
    }

    public static void update(BatchHolder batchHolder,PotentialEarnings.EarningsType earningsType, Boolean plusTips){
        updateEarningsType(batchHolder, earningsType);
        updatePlusTips(batchHolder, plusTips);
    }

    public static void update(BatchHolder batchHolder, Integer payRateInCents, PotentialEarnings.EarningsType earningsType, Boolean plusTips){
        updatePayRateInCents(batchHolder, payRateInCents);
        updateEarningsType(batchHolder, earningsType);
        updatePlusTips(batchHolder, plusTips);
    }

    ////
    ///     methods to actually do the updates
    ///
    private static void updatePayRateInCents(BatchHolder batchHolder, Integer payRateInCents){
        batchHolder.getBatch().getPotentialEarnings().setPayRateInCents(payRateInCents);
        batchHolder.getBatchDetail().getPotentialEarnings().setPayRateInCents(payRateInCents);
    }

    private static void updateEarningsType(BatchHolder batchHolder,PotentialEarnings.EarningsType earningsType){
        batchHolder.getBatch().getPotentialEarnings().setEarningsType(earningsType);
        batchHolder.getBatchDetail().getPotentialEarnings().setEarningsType(earningsType);
    }

    private static void updatePlusTips(BatchHolder batchHolder, Boolean plusTips){
        batchHolder.getBatchDetail().getPotentialEarnings().setPlusTips(plusTips);
        batchHolder.getBatchDetail().getPotentialEarnings().setPlusTips(plusTips);

    }



}
