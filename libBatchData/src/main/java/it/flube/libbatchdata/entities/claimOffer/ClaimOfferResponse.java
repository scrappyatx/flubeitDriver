/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.claimOffer;

import it.flube.libbatchdata.entities.batch.BatchDetail;

/**
 * Created on 3/20/2018
 * Project : Driver
 */

public class ClaimOfferResponse {

    private String batchGuid;
    private BatchDetail.BatchType batchType;
    private String clientId;
    private Long timestamp;

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public BatchDetail.BatchType getBatchType() {
        return batchType;
    }

    public void setBatchType(BatchDetail.BatchType batchType) {
        this.batchType = batchType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
