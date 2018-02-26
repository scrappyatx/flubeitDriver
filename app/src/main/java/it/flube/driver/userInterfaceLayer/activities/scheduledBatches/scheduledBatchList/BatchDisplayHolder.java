/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import it.flube.libbatchdata.entities.batch.Batch;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class BatchDisplayHolder {


    private Batch batch;
    private Boolean sectionHeader;

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Boolean getSectionHeader() {
        return sectionHeader;
    }

    public void setSectionHeader(Boolean sectionHeader) {
        this.sectionHeader = sectionHeader;
    }
}
