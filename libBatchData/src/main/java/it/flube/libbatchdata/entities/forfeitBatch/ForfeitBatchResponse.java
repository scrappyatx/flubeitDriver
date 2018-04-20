/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.forfeitBatch;

import it.flube.libbatchdata.entities.batch.BatchDetail;

/**
 * Created on 3/31/2018
 * Project : Driver
 */
public class ForfeitBatchResponse {

    private Boolean approved;
    private String reason;
    private Long timestamp;

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
