/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.entities.asset.AbstractAsset;
import it.flube.driver.modelLayer.entities.asset.AssetTransfer;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderReceiveAssetStep extends ServiceOrderAbstractStep {
    private ArrayList<AssetTransfer> assetList;
    private ContactPerson contactPerson;

    public ServiceOrderReceiveAssetStep(){
        super.setTaskType(TaskType.RECEIVE_ASSET);
    }

    public ArrayList<AssetTransfer> getAssetList(){
        return assetList;
    }

    public void setAssetList(ArrayList<AssetTransfer> assetList){
        this.assetList = assetList;
    }

    public ContactPerson getContactPerson(){
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson){
        this.contactPerson = contactPerson;
    }

    /// setters / getters for instance variables from abstract Service Order class
    @Override
    public void setTitle(String stepTitle) {
        super.setTitle(stepTitle);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setDescription(String stepDescription) {
        super.setDescription(stepDescription);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setNote(String stepNote){
        super.setNote(stepNote);
    }

    @Override
    public String getNote() {
        return super.getNote();
    }

    @Override
    public TaskType getTaskType() {
        return super.getTaskType();
    }

    @Override
    public void setWorkTiming(WorkTiming workTiming) {
        super.setWorkTiming(workTiming);
    }

    @Override
    public WorkTiming getWorkTiming(){
        return super.getWorkTiming();
    }

    @Override
    public void setWorkStatus(WorkStatus workStatus){
        super.setWorkStatus(workStatus);
    }

    @Override
    public WorkStatus getWorkStatus(){
        return super.getWorkStatus();
    }

    @Override
    public void setWorkStage(WorkStage workStage){
        super.setWorkStage(workStage);
    }

    @Override
    public WorkStage getWorkStage(){
        return super.getWorkStage();
    }

    @Override
    public void setStartTime(Timestamp startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public Timestamp getStartTime(){
        return super.getStartTime();
    }

    @Override
    public void setFinishTime(Timestamp finishTime) {
        super.setFinishTime(finishTime);
    }

    @Override
    public Timestamp getFinishTime(){
        return super.getFinishTime();
    }

    @Override
    public void setMilestoneWhenFinished(String milestoneWhenFinished){
        super.setMilestoneWhenFinished(milestoneWhenFinished);
    }

    public String getMilestoneWhenFinished(){
        return super.getMilestoneWhenFinished();
    }

}
