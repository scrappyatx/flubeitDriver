/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Photo;
import it.flube.driver.modelLayer.entities.Timestamp;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderPhotoStep extends ServiceOrderAbstractStep {
    private ArrayList<Photo> photoList;

    public ServiceOrderPhotoStep(){
        super.setTaskType(TaskType.TAKE_PHOTOS);
    }

    public ArrayList<Photo> getPhotoList(){
        return photoList;
    }

    public void setPhotoList(ArrayList<Photo> photoList) {
        this.photoList = photoList;
    }

    /// setters / getters for instance variables from abstract Service Order class
    @Override
    public void setGUID(String guid){
        super.setGUID(guid);
    }

    @Override
    public String getGUID(){
        return super.getGUID();
    }

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
    public void setStartTimestamp(Timestamp startTimestamp) {
        super.setStartTimestamp(startTimestamp);
    }

    @Override
    public Timestamp getStartTimestamp(){
        return super.getStartTimestamp();
    }

    @Override
    public void setFinishTimestamp(Timestamp finishTimestamp) {
        super.setFinishTimestamp(finishTimestamp);
    }

    @Override
    public Timestamp getFinishTimestamp(){
        return super.getFinishTimestamp();
    }

    @Override
    public void setMilestoneWhenFinished(String milestoneWhenFinished){
        super.setMilestoneWhenFinished(milestoneWhenFinished);
    }

    public String getMilestoneWhenFinished(){
        return super.getMilestoneWhenFinished();
    }

}
