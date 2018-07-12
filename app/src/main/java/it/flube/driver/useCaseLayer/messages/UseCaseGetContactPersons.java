/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.messages;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseBatchFinishedRequest;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 7/9/2018
 * Project : Driver
 */
public class UseCaseGetContactPersons implements
        Runnable,
        CloudActiveBatchInterface.GetActiveBatchCurrentStepResponse,
        CloudActiveBatchInterface.GetContactPersonsByServiceOrderResponse,
        CloudActiveBatchInterface.GetServiceOrderListResponse {

    private static final String TAG = "UseCaseGetContactPersons";


    private final MobileDeviceInterface device;
    private final Driver driver;
    private final UseCaseGetContactPersons.Response response;


    private String activeBatchGuid;
    private ContactPerson supportContact;
    private ContactPersonsByServiceOrder contactList;
    private ArrayList<String> serviceOrderList;

    public UseCaseGetContactPersons(MobileDeviceInterface device, UseCaseGetContactPersons.Response response){
        this.device = device;
        this.driver = device.getUser().getDriver();
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        //get the contact info for flube.it support
        supportContact = device.getCloudConfig().getFlubeItSupportContactPerson();

        ////see if we have an active batch - if we don't, we just return the flube.it support contact person
        //// if we do, we get the contact person list for the active batch
        device.getCloudActiveBatch().getActiveBatchCurrentStepRequest(driver, this);
    }

    //// GetActiveBatchCurrentStepRequest response interface
    public void cloudGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepSuccess");

        activeBatchGuid = batchDetail.getBatchGuid();

        /// now get the contact person list by service order for the active batch
        device.getCloudActiveBatch().getActiveBatchContactPersonsByServiceOrder(driver, activeBatchGuid, this);
    }

    public void cloudGetActiveBatchCurrentStepFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepFailure");
        response.getContactPersonsSuccess(supportContact);
    }

    //// GetActiveBatchContactPersonsByServiceOrder interface
    public void cloudGetActiveBatchContactPersonsByServiceOrderSuccess(ContactPersonsByServiceOrder contactList){
        Timber.tag(TAG).d("cloudGetActiveBatchContactPersonsByServiceOrderSuccess");
        this.contactList = contactList;
        // now get the service order list
        device.getCloudActiveBatch().getActiveBatchServiceOrderListRequest(driver, activeBatchGuid, this);
    }

    public void cloudGetActiveBatchContactPersonsByServiceOrderFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchContactPersonsByServiceOrderFailure");
        response.getContactPersonsSuccess(supportContact);
    }

    //// GetActiveBatchServiceOrderList interface
    public void cloudGetActiveBatchServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        Timber.tag(TAG).d("cloudGetActiveBatchServiceOrderListSuccess");
        ///build an arrayList where the position is the service order sequence (starting at zero = service order sequence 1)

        // first build a treemap, index by sequence.  this will put them in sequence order
        TreeMap<Integer, String> orderBySequenceList = new TreeMap<Integer, String>();
        for (ServiceOrder thisOrder : orderList){
            orderBySequenceList.put(thisOrder.getSequence(), thisOrder.getGuid());
        }

        // now build the arraylist of service order guids by treemap order, this will put them in ascending sequence
        serviceOrderList = new ArrayList<String>();
        for (Map.Entry<Integer, String> orderGuid : orderBySequenceList.entrySet()){
            serviceOrderList.add(orderGuid.getValue());
        }

        response.getContactPersonsSuccess(supportContact, serviceOrderList, contactList);

    }

    public void cloudGetActiveBatchServiceOrderListFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchServiceOrderListFailure");
        response.getContactPersonsSuccess(supportContact);
    }




    public interface Response {
        void getContactPersonsSuccess(ContactPerson supportContact);

        void getContactPersonsSuccess(ContactPerson supportContact, ArrayList<String> serviceOrderList, ContactPersonsByServiceOrder contactMap);

    }
}
