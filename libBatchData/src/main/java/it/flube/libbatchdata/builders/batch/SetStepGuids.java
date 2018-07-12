/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Map;

import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrderScaffold;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/29/2018
 * Project : Driver
 */
public class SetStepGuids {

    public static void setStepGuids(BatchHolder batchHolder){
        //loop through all the steps, and set the guids on any objects in the step that need it
        for (Map.Entry<String, OrderStepInterface> thisStep : batchHolder.getSteps().entrySet()) {
            switch (thisStep.getValue().getTaskType()){
                case NAVIGATION:
                    //no guids in this object need to be set, we are done
                    break;
                case GIVE_ASSET:
                    doGiveAssetStepGuids((ServiceOrderGiveAssetStep) thisStep.getValue());
                    break;
                case RECEIVE_ASSET:
                    doReceiveAssetStepGuids((ServiceOrderReceiveAssetStep) thisStep.getValue());
                    break;
                case TAKE_PHOTOS:
                    doPhotoStepGuids((ServiceOrderPhotoStep) thisStep.getValue());
                    break;
                case AUTHORIZE_PAYMENT:
                    //no guids in this object need to be set, we are done
                    break;
                case WAIT_FOR_USER_TRIGGER:
                    break;
                case WAIT_FOR_EXTERNAL_TRIGGER:
                    //no guids in this object need to be set, we are done
                    break;
                default:
                    break;
            }
        }
    }

    private static void doPhotoStepGuids(ServiceOrderPhotoStep photoStep){
        //loop through all the photoRequests and set the guids
        for (Map.Entry<String, PhotoRequest> thisPhotoRequest : photoStep.getPhotoRequestList().entrySet()){
            thisPhotoRequest.getValue().setBatchGuid(photoStep.getBatchGuid());
            thisPhotoRequest.getValue().setBatchDetailGuid(photoStep.getBatchDetailGuid());
            thisPhotoRequest.getValue().setServiceOrderGuid(photoStep.getServiceOrderGuid());
            thisPhotoRequest.getValue().setStepGuid(photoStep.getGuid());
        }
    }

    private static void doReceiveAssetStepGuids(ServiceOrderReceiveAssetStep receiveStep){
        //if we have a signatureRequest, set the guids
        if (receiveStep.getRequireSignature()){
            receiveStep.getSignatureRequest().setBatchGuid(receiveStep.getBatchGuid());
            receiveStep.getSignatureRequest().setBatchDetailGuid(receiveStep.getBatchDetailGuid());
            receiveStep.getSignatureRequest().setServiceOrderGuid(receiveStep.getServiceOrderGuid());
            receiveStep.getSignatureRequest().setStepGuid(receiveStep.getGuid());
        }
    }

    private static void doGiveAssetStepGuids(ServiceOrderGiveAssetStep giveStep){
        //if we have a signatureRequest, set the guids
        if (giveStep.getRequireSignature()){
            giveStep.getSignatureRequest().setBatchGuid(giveStep.getBatchGuid());
            giveStep.getSignatureRequest().setBatchDetailGuid(giveStep.getBatchDetailGuid());
            giveStep.getSignatureRequest().setServiceOrderGuid(giveStep.getServiceOrderGuid());
            giveStep.getSignatureRequest().setStepGuid(giveStep.getGuid());
        }
    }

}
