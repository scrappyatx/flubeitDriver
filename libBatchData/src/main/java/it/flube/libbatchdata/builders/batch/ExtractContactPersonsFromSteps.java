/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Map;

import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 7/2/2018
 * Project : Driver
 */
public class ExtractContactPersonsFromSteps {

    public static void extractContactPersonsFromSteps(BatchHolder batchHolder){
        //loop through all steps, if the step is ReceiveAsset or GiveAsset, then get the ContactPerson and add it to the list

        for (Map.Entry<String, OrderStepInterface> thisStep : batchHolder.getSteps().entrySet()) {
            switch (thisStep.getValue().getTaskType()){
                case NAVIGATION:
                    //no contact persons in this step type
                    break;
                case GIVE_ASSET:
                    ServiceOrderGiveAssetStep giveStep = (ServiceOrderGiveAssetStep) thisStep.getValue();

                    //put into contactPersons hashmap
                    batchHolder.getContactPersons().put(giveStep.getContactPerson().getGuid(), giveStep.getContactPerson());

                    //put into contactPersonsByServiceOrder hashmap
                    batchHolder.getContactPersonsByServiceOrder().get(giveStep.getServiceOrderGuid()).put(giveStep.getContactPerson().getGuid(), giveStep.getContactPerson());

                    //put into serviceOrder
                    switch(giveStep.getContactPerson().getContactRole()){
                        case CUSTOMER:
                            // add this contact person to service order
                            batchHolder.getServiceOrders().get(giveStep.getServiceOrderGuid()).setCustomerContactPerson(giveStep.getContactPerson());
                            break;
                        case SERVICE_PROVIDER:
                            //add this contact person to service order
                            batchHolder.getServiceOrders().get(giveStep.getServiceOrderGuid()).setCustomerContactPerson(giveStep.getContactPerson());
                            break;
                        case FLUBEIT_SUPPORT:
                            break;
                        case DRIVER:
                            break;
                    }

                    break;
                case RECEIVE_ASSET:
                    ServiceOrderReceiveAssetStep receiveStep = (ServiceOrderReceiveAssetStep) thisStep.getValue();

                    //put into contactPersons hashmap
                    batchHolder.getContactPersons().put(receiveStep.getContactPerson().getGuid(), receiveStep.getContactPerson());

                    //put into contactPersonsByServiceOrder hashmap
                    batchHolder.getContactPersonsByServiceOrder().get(receiveStep.getServiceOrderGuid()).put(receiveStep.getContactPerson().getGuid(), receiveStep.getContactPerson());

                    //put into service order
                    switch(receiveStep.getContactPerson().getContactRole()){
                        case CUSTOMER:
                            // add this contact person to service order
                            batchHolder.getServiceOrders().get(receiveStep.getServiceOrderGuid()).setCustomerContactPerson(receiveStep.getContactPerson());
                            break;
                        case SERVICE_PROVIDER:
                            //add this contact person to service order
                            batchHolder.getServiceOrders().get(receiveStep.getServiceOrderGuid()).setCustomerContactPerson(receiveStep.getContactPerson());
                            break;
                        case FLUBEIT_SUPPORT:
                            break;
                        case DRIVER:
                            break;
                    }



                    break;
                case TAKE_PHOTOS:
                    //no contact persons in this step type
                    break;
                case AUTHORIZE_PAYMENT:
                    //no contact persons in this step type
                    break;
                case WAIT_FOR_USER_TRIGGER:
                    //no contact persons in this step type
                    break;
                case WAIT_FOR_EXTERNAL_TRIGGER:
                    //no contact persons in this step type
                    break;
                default:
                    break;
            }
        }
    }
}
