/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.driver;

/**
 * Created on 12/4/2017
 * Project : Driver
 */

public class CloudDatabaseSettings {
    private String publicOffersNode;
    private String personalOffersNode;
    private String demoOffersNode;
    private String scheduledBatchesNode;
    private String activeBatchNode;
    private String batchDataNode;

    public String getPublicOffersNode() {
        return publicOffersNode;
    }

    public void setPublicOffersNode(String publicOffersNode) {
        this.publicOffersNode = publicOffersNode;
    }

    public String getPersonalOffersNode() {
        return personalOffersNode;
    }

    public void setPersonalOffersNode(String personalOffersNode) {
        this.personalOffersNode = personalOffersNode;
    }

    public String getDemoOffersNode() {
        return demoOffersNode;
    }

    public void setDemoOffersNode(String demoOffersNode) {
        this.demoOffersNode = demoOffersNode;
    }

    public String getScheduledBatchesNode() {
        return scheduledBatchesNode;
    }

    public void setScheduledBatchesNode(String scheduledBatchesNode) {
        this.scheduledBatchesNode = scheduledBatchesNode;
    }

    public String getActiveBatchNode() {
        return activeBatchNode;
    }

    public void setActiveBatchNode(String activeBatchNode) {
        this.activeBatchNode = activeBatchNode;
    }

    public String getBatchDataNode() {
        return batchDataNode;
    }

    public void setBatchDataNode(String batchDataNode) {
        this.batchDataNode = batchDataNode;
    }
}
