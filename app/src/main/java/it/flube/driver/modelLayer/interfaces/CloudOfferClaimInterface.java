/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 3/19/2018
 * Project : Driver
 */

public interface CloudOfferClaimInterface {


    ///
    ///  PUBLIC OFFER CLAIM REQUEST
    ///  PERSONAL OFFER CLAIM REQUEST
    ///
    void claimOfferRequest(Driver driver, String batchGuid, BatchDetail.BatchType batchType, ClaimOfferResponse response);

    interface ClaimOfferResponse {
        void cloudClaimOfferRequestSuccess(String batchGuid);

        void cloudClaimOfferRequestFailure(String batchGuid);

        void cloudClaimOfferRequestTimeout(String batchGuid);
    }


}
