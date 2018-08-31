/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Map;

import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 7/3/2018
 * Project : Driver
 */
public class UpdateContactPerson {

    public static void update(BatchHolder batchHolder, ContactPerson updatedContactPerson){
        //// finds the original contactPerson in the batchHolder (identifies by contactPerson guid)
        //// and replaces the original contactPerson with the updatedContact person
        ////
        //// if the updated contactPerson isn't already in the batchHolder, does nothing


        //loop through all order steps in this batch, and find contactPersons in the steps
        for (Map.Entry<String, OrderStepInterface> thisStep : batchHolder.getSteps().entrySet()){

            switch (thisStep.getValue().getTaskType()){
                case NAVIGATION:
                    //no contact persons in this step type
                    break;
                case GIVE_ASSET:
                    ServiceOrderGiveAssetStep giveStep = (ServiceOrderGiveAssetStep) thisStep.getValue();

                    if (giveStep.getContactPerson().getGuid().equals(updatedContactPerson.getGuid())){
                        /// we have a guid match, this contact person needs to be updated

                        //update step
                        giveStep.setContactPerson(updatedContactPerson);

                        //update batchHolder & batchDetail contactPersons hashmap
                        batchHolder.getContactPersons().put(updatedContactPerson.getGuid(), updatedContactPerson);
                        batchHolder.getBatchDetail().getContactPersons().put(updatedContactPerson.getGuid(), updatedContactPerson);

                        //update batchHolder & batchDetail contactPersonsByServiceOrder hashMap
                        batchHolder.getContactPersonsByServiceOrder().get(giveStep.getServiceOrderGuid()).put(updatedContactPerson.getGuid(), updatedContactPerson);
                        batchHolder.getBatchDetail().getContactPersonsByServiceOrder().get(giveStep.getServiceOrderGuid()).put(updatedContactPerson.getGuid(), updatedContactPerson);

                        //update serviceOrder
                        switch (updatedContactPerson.getContactRole()){
                            case CUSTOMER:
                                batchHolder.getServiceOrders().get(giveStep.getServiceOrderGuid()).setCustomerContactPerson(updatedContactPerson);
                                break;
                            case SERVICE_PROVIDER:
                                batchHolder.getServiceOrders().get(giveStep.getServiceOrderGuid()).setServiceProviderContactPerson(updatedContactPerson);
                                break;
                            case DRIVER:
                                break;
                            case FLUBEIT_SUPPORT:
                                break;
                        }


                    }

                    break;
                case RECEIVE_ASSET:
                    ServiceOrderReceiveAssetStep receiveStep = (ServiceOrderReceiveAssetStep) thisStep.getValue();

                    if (receiveStep.getContactPerson().getGuid().equals(updatedContactPerson.getGuid())){
                        /// we have a guid match, this contact person needs to be updated

                        //update step
                        receiveStep.setContactPerson(updatedContactPerson);

                        //update batchHolder & batchDetail contactPerson hashmap
                        batchHolder.getBatchDetail().getContactPersons().put(updatedContactPerson.getGuid(), updatedContactPerson);
                        batchHolder.getBatchDetail().getContactPersons().put(updatedContactPerson.getGuid(), updatedContactPerson);

                        //update batchHolder & batchDetail contactPersonsByServiceOrder hashmap
                        batchHolder.getContactPersonsByServiceOrder().get(receiveStep.getServiceOrderGuid()).put(updatedContactPerson.getGuid(), updatedContactPerson);
                        batchHolder.getContactPersonsByServiceOrder().get(receiveStep.getServiceOrderGuid()).put(updatedContactPerson.getGuid(), updatedContactPerson);

                        //update serviceOrder
                        switch(updatedContactPerson.getContactRole()){
                            case CUSTOMER:
                                batchHolder.getServiceOrders().get(receiveStep.getServiceOrderGuid()).setCustomerContactPerson(updatedContactPerson);
                                break;
                            case SERVICE_PROVIDER:
                                batchHolder.getServiceOrders().get(receiveStep.getServiceOrderGuid()).setServiceProviderContactPerson(updatedContactPerson);
                                break;
                            case DRIVER:
                                break;
                            case FLUBEIT_SUPPORT:
                                break;
                        }
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
